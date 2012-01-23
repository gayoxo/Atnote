package lector.client.admin.users;

import java.util.ArrayList;
import java.util.Stack;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.StackPanelMio;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.login.UserApp;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;


public class NewUserAdministrator implements EntryPoint {

	private TextBox textBox;
	private VerticalPanel verticalPanel_1;
	private StackPanelMio stackPanel_1;
	private Stack<UserApp> Pilallamada;
	private Button SaveNewUsers;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	public void onModuleLoad() {
		RootPanel RootTXOriginal = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root");
		RootTXOriginal.setStyleName("Root");

		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		menuBar.setWidth("100%");

		MenuItem menuItem = new MenuItem("GroupAdministration", false,
				(Command) null);
		menuItem.setHTML("Users Administrator");
		menuItem.setEnabled(false);
		menuBar.addItem(menuItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		MenuItem mntmBack = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		menuBar.addItem(mntmBack);

		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		RootTXOriginal.add(horizontalSplitPanel, 0, 25);
		horizontalSplitPanel.setSize("100%", "100%");

		VerticalPanel verticalPanel = new VerticalPanel();
		horizontalSplitPanel.setLeftWidget(verticalPanel);
		verticalPanel.setSize("100%", "");

		
		

		SimplePanel simplePanel = new SimplePanel();
		verticalPanel.add(simplePanel);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		simplePanel.setWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");

		textBox = new TextBox();
		textBox.setVisibleLength(50);
		horizontalPanel.add(textBox);
		textBox.setWidth("100%");

		Button btnNewButton = new Button("New button");
		btnNewButton.setText("Plus.gif");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				textBox.setText(ClearText(textBox.getText()));
				if (!textBox.getText().isEmpty()
						&& textBox.getText().length() > 2) {
					Button btnNewButton_1 = new Button(
							"<img src=\"Users.gif\">" + textBox.getText());
					btnNewButton_1.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							Button sourceB = ((Button) event.getSource());
							verticalPanel_1.remove(sourceB);
							if (verticalPanel_1.getWidgetCount()==0) SaveNewUsers.setVisible(false);
						}
					});
					btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonPush");
						}
					});
					btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
						}
					});
					btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
						}
					});
					btnNewButton_1.setStyleName("gwt-ButtonTOP");
					btnNewButton_1.setText(textBox.getText());
					btnNewButton_1.setHTML("<img src=\"Users.gif\">" + textBox.getText());
					verticalPanel_1.add(btnNewButton_1);
					textBox.setText("");
					btnNewButton_1.setWidth("100%");
					SaveNewUsers.setVisible(true);
				} else
					Window.alert("The User need to be a valid reference");
			}

			private String ClearText(String text) {
				StringBuffer Sout=new StringBuffer();
				for (int i = 0; i < text.length(); i++) {
					if (text.charAt(i)!=' ')
						Sout.append(text.charAt(i));
						
				}
				return Sout.toString();
			}
		});
		horizontalPanel.add(btnNewButton);
		btnNewButton.setHTML("<img src=\"Plus.gif\">");

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
							Window.alert("The user could not be saved at this moment");
							LoadingPanel.getInstance().hide();

						}

						public void onSuccess(Boolean result) {
							LoadingPanel.getInstance().hide();
							if (!result) Window.alert("The user " + Pilallamada.peek().getEmail()  + " already exists (if you do not see him/her it's because he may be administrator)");						
							Pilallamada.pop();
							if (!Pilallamada.isEmpty()){
								LoadingPanel.getInstance().center();
								LoadingPanel.getInstance().setLabelTexto("Saving...");
								bookReaderServiceHolder.saveUser(
										Pilallamada.peek(), callback);
							
							}
								else {
									refreshPanel();
									
								}

						} 
					};
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
				((Button)event.getSource()).setStyleName("gwt-ButtonBottonSavePush");
			}
		});
		SaveNewUsers.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBottonSave");
			}
		});
		SaveNewUsers.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBottonSaveOver");
			}
		});
		SaveNewUsers.setStyleName("gwt-ButtonBottonSave");
	//	SaveNewUsers.setStyleName("gwt-MenuItemMio");
		verticalPanel.add(SaveNewUsers);
		SaveNewUsers.setWidth("100%");

		ScrollPanel scrollPanel = new ScrollPanel();
		horizontalSplitPanel.setRightWidget(scrollPanel);
		scrollPanel.setSize("100%", "100%");

		stackPanel_1 = new StackPanelMio();
		scrollPanel.setWidget(stackPanel_1);
		// simplePanel.add(stackPanel_1);

		stackPanel_1.setBotonTipo(new BotonesStackPanelUsersMio("prototipo",
				new VerticalPanel()));
		stackPanel_1.setBotonClick(new ClickHandler() {

			public void onClick(ClickEvent event) {
				String Nombre = ((BotonesStackPanelUsersMio) event.getSource())
						.getText();
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto("Loading...");
				bookReaderServiceHolder.loadUserByEmail(Nombre,
						new AsyncCallback<UserApp>() {

							public void onFailure(Throwable caught) {
								LoadingPanel.getInstance().hide();

							}

							public void onSuccess(UserApp result) {
								LoadingPanel.getInstance().hide();
								if (result.getGroupIds()==null) result.setGroupIds(new ArrayList<Long>());
								if (!result.getGroupIds().isEmpty())
									if (Window
											.confirm("Are you sure you want to delete this user?, he is a member to a "
													+ result.getGroupIds().size() + " groups"))

										remove(result.getId());

									else {
									}
								else {
									if (Window
											.confirm("Are you sure you want to delete this user?, he is not a member of any group"))
										remove(result.getId());
								}
									

							}
						});

				
			}

			private void remove(Long userId) {
				
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto("Deleting...");
				bookReaderServiceHolder.deleteUserApp(userId, new AsyncCallback<Integer>() {

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert("Sorry but the user could not be removed, try again later");
					}

					public void onSuccess(Integer result) {
						LoadingPanel.getInstance().hide();
						refreshPanel();
					}
				});

			}
		});

		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
		bookReaderServiceHolder.getUsersApp(new AsyncCallback<ArrayList<UserApp>>() {

			public void onFailure(Throwable caught) {
				LoadingPanel.getInstance().hide();
				
			}

			public void onSuccess(ArrayList<UserApp> result) {
				LoadingPanel.getInstance().hide();
				if (result.size() < 10) {
					Long IDi = 0l;
					for (UserApp User1 : result) {
						EntidadUser E = new EntidadUser(User1.getEmail(), IDi);
						stackPanel_1.addBotonLessTen(E);
						IDi++;
					}

				} else {
					Long IDi = 0l;
					for (UserApp User1 : result) {
						EntidadUser E = new EntidadUser(User1.getEmail(), IDi);
						stackPanel_1.addBoton(E);
						IDi++;
					}
				}
				stackPanel_1.setSize("100%", "100%");
				stackPanel_1.ClearEmpty();
				
			}
		});
		
		stackPanel_1.setSize("100%", "100%");
	}

	protected void refreshPanel() {
		stackPanel_1.Clear();
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
		bookReaderServiceHolder.getUsersApp(new AsyncCallback<ArrayList<UserApp>>() {

			public void onFailure(Throwable caught) {
				LoadingPanel.getInstance().hide();
				
			}

			public void onSuccess(ArrayList<UserApp> result) {
				LoadingPanel.getInstance().hide();
				if (result.size() < 10) {
					Long IDi = 0l;
					for (UserApp User1 : result) {
						EntidadUser E = new EntidadUser(User1.getEmail(), IDi);
						stackPanel_1.addBotonLessTen(E);
						IDi++;
					}

				} else {
					Long IDi = 0l;
					for (UserApp User1 : result) {
						EntidadUser E = new EntidadUser(User1.getEmail(), IDi);
						stackPanel_1.addBoton(E);
						IDi++;
					}
				}
				stackPanel_1.setSize("100%", "100%");
				stackPanel_1.ClearEmpty();
				
			}
		});
		
		stackPanel_1.setSize("100%", "100%");

	}
}
