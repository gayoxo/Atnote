package lector.client.admin.group;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Controlador;
import lector.client.login.GroupApp;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class Groupadministration implements EntryPoint {

	private SimplePanel Panel_grupo_seleccionado;
	private VerticalPanel Panel_de_grupos;
	private Groupadministration Yo;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	private Button ActualSelect;
	private String OldStyle;
	
	public void onModuleLoad() {
		Yo=this;
		RootPanel RootTXOriginal = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root2");
		RootTXOriginal.setStyleName("Root");
		
		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		
		MenuItem mntmNewItem = new MenuItem("Group", false, (Command) null);
		mntmNewItem.setEnabled(false);
		mntmNewItem.setHTML("Group Administration");
		menuBar.addItem(mntmNewItem);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuItem mntmNewItem_1 = new MenuItem("New", false, new Command() {
			public void execute() {
				NewGroupPopUpPanel NG=new NewGroupPopUpPanel(Yo);
				NG.center();
				NG.setModal(true);
			}
		});
		mntmNewItem_1.setHTML("New");
		menuBar.addItem(mntmNewItem_1);
		
		MenuItem mntmNewItem_2 = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		mntmNewItem_2.setHTML("Back");
		menuBar.addItem(mntmNewItem_2);
		
		SimplePanel simplePanel = new SimplePanel();
		RootTXOriginal.add(simplePanel,0,25);
		simplePanel.setSize("100%", "100%");
		
		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		simplePanel.setWidget(horizontalSplitPanel);
		horizontalSplitPanel.setSize("100%", "100%");
		
		Panel_de_grupos = new VerticalPanel();
		horizontalSplitPanel.setLeftWidget(Panel_de_grupos);
		Panel_de_grupos.setSize("100%", "");
				
		Panel_grupo_seleccionado = new SimplePanel();
		horizontalSplitPanel.setRightWidget(Panel_grupo_seleccionado);
		Panel_grupo_seleccionado.setSize("100%", "100%");
		
		bookReaderServiceHolder.getGroups(new AsyncCallback<ArrayList<GroupApp>>() {
			
			public void onSuccess(ArrayList<GroupApp> result) {
			
					for (int i = 0; i < result.size()-1; i++){
					Button btnNewButton = new Button(result.get(i).getName());
					btnNewButton.addClickHandler(new ClickHandler() {


						public void onClick(ClickEvent event) {
							
							
							if (ActualSelect!=null)
								{
								ActualSelect.setStyleName(OldStyle);
								}
							ActualSelect=(Button)event.getSource();
							ActualSelect.setStyleName("gwt-ButtonTOPSelect");
							OldStyle="gwt-ButtonTOP";
							String BotonEvento=ActualSelect.getHTML();
							bookReaderServiceHolder.loadGroupByName(BotonEvento, new AsyncCallback<GroupApp>() {

								public void onFailure(Throwable caught) {
									Window.alert("The group could not be loaded");
									
								}

								public void onSuccess(GroupApp result) {
									Panel_grupo_seleccionado.clear();
									Panel_grupo_seleccionado.add(new GroupAndUserPanel(result,Panel_grupo_seleccionado,Yo));
								}
							});
							
							
							
						}
					});
					Panel_de_grupos.add(btnNewButton);
					btnNewButton.setSize("100%", "100%");
					btnNewButton.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							if (ActualSelect!=(Button)event.getSource()) 
								((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
							else ((Button)event.getSource()).setStyleName("gwt-ButtonTOPSelect");
						}
					});
					btnNewButton.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							
								((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
							
						}
					});
					btnNewButton.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonPush");
						}
					});
					btnNewButton.setStyleName("gwt-ButtonTOP");
				}
				
			if (!result.isEmpty())
			{
				Button btnNewButton = new Button(result.get(result.size()-1).getName());
				btnNewButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						
						
						if (ActualSelect!=null)
							{
							ActualSelect.setStyleName(OldStyle);
							}
						ActualSelect=(Button)event.getSource();
						OldStyle="gwt-ButtonBotton";
						ActualSelect.setStyleName("gwt-ButtonBottonSelect");
						String BotonEvento=ActualSelect.getHTML();
						bookReaderServiceHolder.loadGroupByName(BotonEvento, new AsyncCallback<GroupApp>() {

							public void onFailure(Throwable caught) {
								Window.alert("The group could not be loaded");
								
							}

							public void onSuccess(GroupApp result) {
								Panel_grupo_seleccionado.clear();
								Panel_grupo_seleccionado.add(new GroupAndUserPanel(result,Panel_grupo_seleccionado,Yo));
							}
						});
						
						
						
					}
				});
				Panel_de_grupos.add(btnNewButton);
				btnNewButton.setSize("100%", "100%");
				btnNewButton.addMouseOutHandler(new MouseOutHandler() {
					public void onMouseOut(MouseOutEvent event) {
						if (ActualSelect!=(Button)event.getSource()) 
							((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
						else ((Button)event.getSource()).setStyleName("gwt-ButtonBottonSelect");
					}
				});
				btnNewButton.addMouseOverHandler(new MouseOverHandler() {
					public void onMouseOver(MouseOverEvent event) {
						
							((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
						
					}
				});
				btnNewButton.addMouseDownHandler(new MouseDownHandler() {
					public void onMouseDown(MouseDownEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
					}
				});
				btnNewButton.setStyleName("gwt-ButtonBotton");
			}
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Groups could not be retrieved, please try again later");
				
			}
		});

		
		
	}

	public void refreshGroups() {
		Panel_de_grupos.clear();
		Panel_grupo_seleccionado.clear();
		
bookReaderServiceHolder.getGroups(new AsyncCallback<ArrayList<GroupApp>>() {
			
			public void onSuccess(ArrayList<GroupApp> result) {
			
					for (int i = 0; i < result.size()-1; i++){
					Button btnNewButton = new Button(result.get(i).getName());
					btnNewButton.addClickHandler(new ClickHandler() {


						public void onClick(ClickEvent event) {
							
							
							if (ActualSelect!=null)
								{
								ActualSelect.setStyleName(OldStyle);
								}
							ActualSelect=(Button)event.getSource();
							ActualSelect.setStyleName("gwt-ButtonTOPSelect");
							OldStyle="gwt-ButtonTOP";
							String BotonEvento=ActualSelect.getHTML();
							bookReaderServiceHolder.loadGroupByName(BotonEvento, new AsyncCallback<GroupApp>() {

								public void onFailure(Throwable caught) {
									Window.alert("The group could not be loaded");
									
								}

								public void onSuccess(GroupApp result) {
									Panel_grupo_seleccionado.clear();
									Panel_grupo_seleccionado.add(new GroupAndUserPanel(result,Panel_grupo_seleccionado,Yo));
								}
							});
							
							
							
						}
					});
					Panel_de_grupos.add(btnNewButton);
					btnNewButton.setSize("100%", "100%");
					btnNewButton.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							if (ActualSelect!=(Button)event.getSource()) 
								((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
							else ((Button)event.getSource()).setStyleName("gwt-ButtonTOPSelect");
						}
					});
					btnNewButton.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							
								((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
							
						}
					});
					btnNewButton.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonPush");
						}
					});
					btnNewButton.setStyleName("gwt-ButtonTOP");
				}
				
			if (!result.isEmpty())
			{
				Button btnNewButton = new Button(result.get(result.size()-1).getName());
				btnNewButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						
						
						if (ActualSelect!=null)
							{
							ActualSelect.setStyleName(OldStyle);
							}
						ActualSelect=(Button)event.getSource();
						OldStyle="gwt-ButtonBotton";
						ActualSelect.setStyleName("gwt-ButtonBottonSelect");
						String BotonEvento=ActualSelect.getHTML();
						bookReaderServiceHolder.loadGroupByName(BotonEvento, new AsyncCallback<GroupApp>() {

							public void onFailure(Throwable caught) {
								Window.alert("The group could not be loaded");
								
							}

							public void onSuccess(GroupApp result) {
								Panel_grupo_seleccionado.clear();
								Panel_grupo_seleccionado.add(new GroupAndUserPanel(result,Panel_grupo_seleccionado,Yo));
							}
						});
						
						
						
					}
				});
				Panel_de_grupos.add(btnNewButton);
				btnNewButton.setSize("100%", "100%");
				btnNewButton.addMouseOutHandler(new MouseOutHandler() {
					public void onMouseOut(MouseOutEvent event) {
						if (ActualSelect!=(Button)event.getSource()) 
							((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
						else ((Button)event.getSource()).setStyleName("gwt-ButtonBottonSelect");
					}
				});
				btnNewButton.addMouseOverHandler(new MouseOverHandler() {
					public void onMouseOver(MouseOverEvent event) {
						
							((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
						
					}
				});
				btnNewButton.addMouseDownHandler(new MouseDownHandler() {
					public void onMouseDown(MouseDownEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
					}
				});
				btnNewButton.setStyleName("gwt-ButtonBotton");
			}
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Groups could not be retrieved, please try again later");
				
			}
		});
	
	}
	
}
