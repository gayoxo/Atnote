package lector.client.admin.group;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.login.GroupApp;
import lector.client.login.UserApp;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;

public class GroupAndUserPanel extends Composite {

	private GroupAndUserPanel Yo;
	private GroupApp Mygroup;
	private VerticalPanel Panel_Usuarios;
	private SimplePanel PanelMioDentro;
	private Groupadministration Father;
	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	public GroupAndUserPanel(GroupApp Grupo,SimplePanel panel_grupo_seleccionado,Groupadministration Fatherin) {

		Yo = this;
		Mygroup = Grupo;

		PanelMioDentro=panel_grupo_seleccionado;
		
		Father = Fatherin;	
		FlowPanel flowPanel = new FlowPanel();
		initWidget(flowPanel);
		flowPanel.setSize("100%", "100%");
		
		MenuBar menuBar_2 = new MenuBar(false);
		flowPanel.add(menuBar_2);
		
		MenuItem mntmNewItem_1 = new MenuItem("Group: " + Mygroup.getName(), false, (Command) null);
		mntmNewItem_1.setEnabled(false);
		menuBar_2.addItem(mntmNewItem_1);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar_2.addSeparator(separator);
		
		MenuItem mntmNewItem_2 = new MenuItem("Delete Group", false, new Command() {
			public void execute() {
				if (Window.confirm("Are you sure you want to delete this group?"))
				{
					LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto("Deleting...");
				bookReaderServiceHolder.deleteGroup(Mygroup.getId(), new AsyncCallback<Integer>() {

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						
					}

					public void onSuccess(Integer result) {
						LoadingPanel.getInstance().hide();
						PanelMioDentro.clear();
						Father.refreshGroups();
						
					}
				});
				}
			}
		});
		mntmNewItem_2.setHTML("Delete Group");
		menuBar_2.addItem(mntmNewItem_2);
		
				MenuItem mntmNewItem = new MenuItem("Add a User", false, new Command() {
					public void execute() {
						NewUser2group NU = new NewUser2group(Yo);
						NU.center();
						NU.setModal(true);
					}
				});
				menuBar_2.addItem(mntmNewItem);
				mntmNewItem.setHTML("Add a User");
										
												Panel_Usuarios = new VerticalPanel();
												flowPanel.add(Panel_Usuarios);
												Panel_Usuarios.setWidth("100%");

		

		bookReaderServiceHolder.getUsersByGroupId(Mygroup.getId(),
				new AsyncCallback<ArrayList<UserApp>>() {

					public void onSuccess(ArrayList<UserApp> result) {
					

						for (int i = 0; i < result.size()-1; i++) {			
							ButtonGroupMio User = new ButtonGroupMio(result.get(i));
							User.setHTML("<img src=\"Users.gif\">"+ result.get(i).getEmail());
							Panel_Usuarios.add(User);
							User.setSize("100%","100%");
							User.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							User.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							User.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							User.setStyleName("gwt-ButtonTOP");
							User.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									if (Window
											.confirm("Are you sure to remove the user from the list")) {
//										String SourceText = ((Button) event
//												.getSource()).getHTML();
//										bookReaderServiceHolder
//												.loadUserByEmail(
//														SourceText,
//														new AsyncCallback<UserApp>() {
//
//															public void onFailure(
//																	Throwable caught) {
//																Window.alert("The user could not be loaded");
//															}
//
//															public void onSuccess(
//																	UserApp result) {
										
										
										ButtonGroupMio BGM=((ButtonGroupMio) event.getSource());
										
																bookReaderServiceHolder
																		.removeUserAndGroupRelation(
																				BGM.getUsuario().getId(),
																				
																				Mygroup.getId(),
																				new AsyncCallback<Void>() {

																					public void onFailure(
																							Throwable caught) {
																						Window.alert("The user could not be removed from his/her group");

																					}

																					public void onSuccess(
																							Void result) {
																						refresh();
																					}
																				});
//
//															}
//														});

									}

								}
							});
						}
						if (!result.isEmpty()){
							ButtonGroupMio User = new ButtonGroupMio(result.get(result.size()-1));
							User.setHTML("<img src=\"Users.gif\">"+ result.get(result.size()-1).getEmail());
							Panel_Usuarios.add(User);
							User.setSize("100%","100%");
							User.setStyleName("gwt-ButtonBotton");
							User.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
								}
							});
							User.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
								}
							});
							User.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
								}
							});
							User.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									if (Window
											.confirm("Are you sure to remove the user from the list")) {
//										String SourceText = ((Button) event
//												.getSource()).getHTML();
//										bookReaderServiceHolder
//												.loadUserByEmail(
//														SourceText,
//														new AsyncCallback<UserApp>() {
//
//															public void onFailure(
//																	Throwable caught) {
//																Window.alert("The user could not be loaded");
//															}
//
//															public void onSuccess(
//																	UserApp result) {
										ButtonGroupMio BGM=((ButtonGroupMio) event.getSource());
										
										bookReaderServiceHolder
												.removeUserAndGroupRelation(
														BGM.getUsuario().getId(),
//																bookReaderServiceHolder
//																		.removeUserAndGroupRelation(
//																				result.getId(),
																				Mygroup.getId(),
																				new AsyncCallback<Void>() {

																					public void onFailure(
																							Throwable caught) {
																						Window.alert("The user could not be removed from his/her group");

																					}

																					public void onSuccess(
																							Void result) {
																						refresh();
																					}
																				});
//
//															}
//														});

									}

								}
							});
						}

					}

					public void onFailure(Throwable caught) {
						Window.alert("The users of the group could not be retrieved");

					}
				});

	}

	public GroupApp getMygroup() {
		return Mygroup;
	}

	public void refresh() {
		Panel_Usuarios.clear();
		
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
		bookReaderServiceHolder.getUsersByGroupId(Mygroup.getId(),
				new AsyncCallback<ArrayList<UserApp>>() {

					public void onSuccess(ArrayList<UserApp> result) {
					
						LoadingPanel.getInstance().hide();
						for (int i = 0; i < result.size()-1; i++) {			
							ButtonGroupMio User = new ButtonGroupMio(result.get(i));
							User.setHTML("<img src=\"Users.gif\">"+ result.get(i).getEmail());
							Panel_Usuarios.add(User);
							User.setSize("100%","100%");
							User.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							User.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							User.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							User.setStyleName("gwt-ButtonTOP");
							User.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									if (Window
											.confirm("Are you sure to remove the user from the list")) {
//										String SourceText = ((Button) event
//												.getSource()).getHTML();
//										bookReaderServiceHolder
//												.loadUserByEmail(
//														SourceText,
//														new AsyncCallback<UserApp>() {
//
//															public void onFailure(
//																	Throwable caught) {
//																Window.alert("The user could not be loaded");
//															}
//
//															public void onSuccess(
//																	UserApp result) {
										
										
										ButtonGroupMio BGM=((ButtonGroupMio) event.getSource());
										
																bookReaderServiceHolder
																		.removeUserAndGroupRelation(
																				BGM.getUsuario().getId(),
																				
																				Mygroup.getId(),
																				new AsyncCallback<Void>() {

																					public void onFailure(
																							Throwable caught) {
																						Window.alert("The user could not be removed from his/her group");

																					}

																					public void onSuccess(
																							Void result) {
																						refresh();
																					}
																				});
//
//															}
//														});

									}

								}
							});
						}
						if (!result.isEmpty()){
							ButtonGroupMio User = new ButtonGroupMio(result.get(result.size()-1));
							User.setHTML("<img src=\"Users.gif\">"+ result.get(result.size()-1).getEmail());
							Panel_Usuarios.add(User);
							User.setSize("100%","100%");
							User.setStyleName("gwt-ButtonBotton");
							User.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
								}
							});
							User.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
								}
							});
							User.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
								}
							});
							User.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									if (Window
											.confirm("Are you sure to remove the user from the list")) {
//										String SourceText = ((Button) event
//												.getSource()).getHTML();
//										bookReaderServiceHolder
//												.loadUserByEmail(
//														SourceText,
//														new AsyncCallback<UserApp>() {
//
//															public void onFailure(
//																	Throwable caught) {
//																Window.alert("The user could not be loaded");
//															}
//
//															public void onSuccess(
//																	UserApp result) {
										ButtonGroupMio BGM=((ButtonGroupMio) event.getSource());
										
										bookReaderServiceHolder
												.removeUserAndGroupRelation(
														BGM.getUsuario().getId(),
//																bookReaderServiceHolder
//																		.removeUserAndGroupRelation(
//																				result.getId(),
																				Mygroup.getId(),
																				new AsyncCallback<Void>() {

																					public void onFailure(
																							Throwable caught) {
																						Window.alert("The user could not be removed from his/her group");

																					}

																					public void onSuccess(
																							Void result) {
																						refresh();
																					}
																				});
//
//															}
//														});

									}

								}
							});
						}

					}

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert("The users of the group could not be retrieved");

					}
				});
	}
}
