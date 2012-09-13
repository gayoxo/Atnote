package lector.client.admin.group;

import java.util.ArrayList;
import java.util.Stack;

import lector.client.admin.users.PanelAddMasibo;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Constants;
import lector.client.controler.ErrorConstants;
import lector.client.reader.LoadingPanel;
import lector.share.model.UserApp;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

public class CreateAndAdd extends PopupPanel {

	private GroupAndUserPanel gAUPadre;
	private CreateAndAdd YO;
	private VerticalPanel verticalPanel;
	private VerticalPanel verticalPanel_1;
	private Button SaveNewUsers;
	private Stack<UserApp> Pilallamada;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private ArrayList<UserApp> UsuariosGroup;

	public CreateAndAdd(GroupAndUserPanel gAUP) {
		super(false);
		setGlassEnabled(true);

		gAUPadre = gAUP;
		YO = this;

		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		setWidget(dockLayoutPanel);
		dockLayoutPanel.setSize("411px", "528px");

		MenuBar menuBar = new MenuBar(false);
		dockLayoutPanel.addNorth(menuBar, 1.9);

		MenuItem Closebotton = new MenuItem("New item", false, new Command() {
			public void execute() {
				hide();
			}
		});
		Closebotton.setHTML("Close");
		menuBar.addItem(Closebotton);

		MenuItem AbrirPanel = new MenuItem("New item", false, new Command() {
			public void execute() {

				PanelAddMasibo PAM = new PanelAddMasibo(YO);
				PAM.setVisible(true);
				PAM.center();

			}
		});
		AbrirPanel.setHTML("OpenAddedPanel");
		menuBar.addItem(AbrirPanel);
		
		ScrollPanel scrollPanel = new ScrollPanel();
		dockLayoutPanel.addWest(scrollPanel, 31.5);
		scrollPanel.setSize("100%", "100%");

		verticalPanel = new VerticalPanel();
		scrollPanel.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");

		verticalPanel_1 = new VerticalPanel();
		verticalPanel.add(verticalPanel_1);
		verticalPanel_1.setWidth("100%");

		SaveNewUsers = new Button("Save New Users");
		SaveNewUsers.setVisible(false);
		SaveNewUsers.addClickHandler(new ClickHandler() {
			private AsyncCallback<Boolean> callback;

			public void onClick(ClickEvent event) {
				Pilallamada = new Stack<UserApp>();
				int Elementos_a_salvar = verticalPanel_1.getWidgetCount();
				for (int i = 0; i < Elementos_a_salvar; i++) {
					Widget Uno = verticalPanel_1.getWidget(i);
					Button Dos = (Button) Uno;
					String Nombre = Dos.getText();
					Pilallamada.add(new UserApp(Nombre, Constants.STUDENT));
				}
				if (!Pilallamada.isEmpty()) {
					callback = new AsyncCallback<Boolean>() {

						public void onFailure(Throwable caught) {
							Window.alert(ErrorConstants.ERROR_SAVING_USERS);
							LoadingPanel.getInstance().hide();

						}

						public void onSuccess(Boolean result) {
							LoadingPanel.getInstance().hide();
							if (!result)
								Window.alert("The user "
										+ Pilallamada.peek().getEmail()
										+ " already exists (if you do not see him/her it's because he may be administrator)");
							
							LoadingPanel.getInstance().center();
							LoadingPanel.getInstance().setLabelTexto(
									"Loading...");
							bookReaderServiceHolder.loadUserByEmail(Pilallamada
									.peek().getEmail(),
									new AsyncCallback<UserApp>() {

										public void onFailure(Throwable caught) {
											Window.alert(ErrorConstants.ERROR_ADDING_USERS2GROUP1
													+ Pilallamada.peek()
															.getEmail()
													+ ErrorConstants.ERROR_ADDING_USERS2GROUP2
													+ gAUPadre.getMygroup()
															.getName()
													+ ErrorConstants.ERROR_ADDING_USERS2GROUP3);
											
											LoadingPanel.getInstance().hide();

											Pilallamada.pop();
											if (!Pilallamada.isEmpty()) {

												LoadingPanel.getInstance()
														.center();
												LoadingPanel.getInstance()
														.setLabelTexto(
																"Saving...");

												bookReaderServiceHolder
														.saveUser(Pilallamada
																.peek(),
																callback);

											} else {
												EnterInGroup();

											}

										}

										private void EnterInGroup() {
											ArrayList<Long> ListaYa = gAUPadre
													.getMygroup().getUsersIds();
											for (UserApp result : UsuariosGroup) {
												if (!noestaenlalista(result,
														ListaYa)) {
													gAUPadre.getMygroup()
															.getUsersIds()
															.add(result.getId());
												} else
													Window.alert("The user was in the list before");
											}

											LoadingPanel.getInstance()
											.setLabelTexto(
													"Saving...");
											bookReaderServiceHolder.saveGroup(
													gAUPadre.getMygroup(),
													new AsyncCallback<Void>() {

														public void onFailure(
																Throwable caught) {
															LoadingPanel.getInstance().hide();
															Window.alert("The group could not saved");

														}

														public void onSuccess(
																Void result) {
															LoadingPanel.getInstance().hide();
															gAUPadre.refresh();
															hide();
														}
													});

										}

										private boolean noestaenlalista(
												UserApp userEnter,
												ArrayList<Long> listaYa) {
											for (Long UsersT : listaYa) {
												if (UsersT.equals(userEnter
														.getId()))
													return true;
											}
											return false;
										}

										public void onSuccess(UserApp result) {
											LoadingPanel.getInstance().hide();
											if (result.getProfile().equals(Constants.STUDENT)) 
												UsuariosGroup.add(result);
											Pilallamada.pop();
											if (!Pilallamada.isEmpty()) {

												LoadingPanel.getInstance()
														.center();
												LoadingPanel.getInstance()
														.setLabelTexto(
																"Saving...");
												bookReaderServiceHolder
														.saveUser(Pilallamada
																.peek(),
																callback);

											} else {
												EnterInGroup();

											}

										}

									});

						}
					};
					UsuariosGroup = new ArrayList<UserApp>();
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto("Saving...");
					bookReaderServiceHolder.saveUser(Pilallamada.peek(),
							callback);

				}
				verticalPanel_1.clear();
				SaveNewUsers.setVisible(false);
			}
		});
		SaveNewUsers.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonBottonSavePush");
			}
		});
		SaveNewUsers.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonBottonSave");
			}
		});
		SaveNewUsers.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonBottonSaveOver");
			}
		});
		SaveNewUsers.setStyleName("gwt-ButtonBottonSave");
		// SaveNewUsers.setStyleName("gwt-MenuItemMio");
		verticalPanel.add(SaveNewUsers);
		SaveNewUsers.setSize("100%", "100%");

	}

	public void addText(String TextoBoton) {
		Button btnNewButton_1 = new Button("<img src=\"Users.gif\">"
				+ TextoBoton);
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Button sourceB = ((Button) event.getSource());
				verticalPanel_1.remove(sourceB);
				if (verticalPanel_1.getWidgetCount() == 0)
					SaveNewUsers.setVisible(false);
			}
		});
		btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnNewButton_1.setStyleName("gwt-ButtonTOP");
		btnNewButton_1.setText(TextoBoton);
		btnNewButton_1.setHTML("<img src=\"Users.gif\">" + TextoBoton);
		verticalPanel_1.add(btnNewButton_1);
		btnNewButton_1.setSize("100%", "100%");
		SaveNewUsers.setVisible(true);

	}

}
