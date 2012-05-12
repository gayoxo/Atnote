package lector.client.reader;

import lector.client.catalogo.Finder;
import lector.client.catalogo.FinderGrafo;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.controler.Constants;
import lector.client.login.ActualUser;
import lector.client.reader.PanelTextComent.CatalogTipo;

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
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;

public class SelectorTypePopUpAnnotacion extends PopupPanel {
	private static FinderGrafo finder;
	protected MenuItem mntmNewItem;
	protected MenuBar menuBar;
	private static CatalogTipo CT;
	private HorizontalPanel panelBotonesTipo;
	private Catalog Cata;
	private static HorizontalPanel HP;

	public SelectorTypePopUpAnnotacion(HorizontalPanel penelBotonesTipo,Catalog Catain, CatalogTipo catalog2) {
		super(true);
		CT=catalog2;
		panelBotonesTipo=penelBotonesTipo;
		Cata=Catain;
//		SimplePanel verticalPanel = new SimplePanel();
//		
//		
//		verticalPanel.setSize("100%", "100%");
		DockLayoutPanel dockPanel = new DockLayoutPanel(Unit.EM);
//        verticalPanel.add(dockPanel);
        dockPanel.setSize("100%", "100%");
        setWidget(dockPanel);
        
        menuBar = new MenuBar(false);
        dockPanel.addNorth(menuBar, 1.9);
        menuBar.setSize("100%", "20px");
        
       
        mntmNewItem = new MenuItem(ActualUser.getLanguage().getNewAdmin(), false, new Command() {
        	public void execute() {
        		SelectorTypePopUpAnnotacionAdministracion SBTF=new SelectorTypePopUpAnnotacionAdministracion(panelBotonesTipo,Cata,CT,finder);
        		SBTF.center();
        	}
        });
        if (ActualUser.getUser().getProfile().equals(Constants.PROFESSOR)) menuBar.addItem(mntmNewItem);
        mntmNewItem.setSize("100%", "100%");
        
        MenuItem mntmNewItem_1 = new MenuItem("New item", false, new Command() {
        	public void execute() {
        		while(HP.getWidgetCount()!=0)
        		{
        			ButtonTipo Yo=(ButtonTipo)HP.getWidget(0);
					Yo.setPertenezco(panelBotonesTipo);
					if (!exist(Yo))
						panelBotonesTipo.add(Yo);
					else HP.remove(Yo);
       		}
//        			for (Widget widget : HP) {
//					panelBotonesTipo.add(widget);				
//        		}
        		hide();
        	}

			private boolean exist(ButtonTipo yo) {
				for (Widget BB:panelBotonesTipo)
					{
					ButtonTipo Yo=(ButtonTipo)BB;
					if (yo.getEntidad().getID().equals(Yo.getEntidad().getID())) 
						return true;
					}
				return false;
			}
        });
        mntmNewItem_1.setHTML(ActualUser.getLanguage().getClose());
        menuBar.addItem(mntmNewItem_1);
        
        ScrollPanel SP=new ScrollPanel();
        dockPanel.addSouth(SP, 5.0);
        
        SP.setSize("100%", "100%");
        HP=new HorizontalPanel();
        SP.add(HP);
        
        HP.setHeight("100%");
        
        SimplePanel scrollPanel = new SimplePanel();
        scrollPanel.setSize("100%", "100%");
        dockPanel.add(scrollPanel);
        dockPanel.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-100+"px");
        
        FinderGrafo.setButtonTipoGrafo(new BotonesStackPanelReaderSelectMio("prototipo", new VerticalPanel(),HP));
        
        
        FinderGrafo.setBotonClickGrafo(new ClickHandler() {

	        public void onClick(ClickEvent event) {
	        	BotonesStackPanelReaderSelectMio BS=((BotonesStackPanelReaderSelectMio) event.getSource());
	        Entity Act=BS.getEntidad();
	        
	        if (Act instanceof File)
	        {
	        	ButtonTipo nuevo=new ButtonTipo(Act,CT.getTexto(),BS.getLabeltypo());
	        	nuevo.addClickHandler(new ClickHandler() {
					
					public void onClick(ClickEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
						
					}
				});
			
	        	nuevo.addMouseDownHandler(new MouseDownHandler() {
					public void onMouseDown(MouseDownEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
					}
				});
				

	        	nuevo.addMouseOutHandler(new MouseOutHandler() {
					public void onMouseOut(MouseOutEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				}
			});
				

	        	nuevo.addMouseOverHandler(new MouseOverHandler() {
					public void onMouseOver(MouseOverEvent event) {
						
						((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
					
				}
			});

	        	nuevo.setStyleName("gwt-ButtonCenter");
	        	nuevo.addClickHandler(new ClickHandler() {
					
					public void onClick(ClickEvent event) {
						ButtonTipo Yo=(ButtonTipo)event.getSource();
						Yo.getPertenezco().remove(Yo);
						
						
					}
				});
	        	if (!ExistPreview(BS.getLabeltypo(),Act))
	        			BS.getLabeltypo().add(nuevo);
	        	else Window.alert(ActualUser.getLanguage().getE_ExistBefore());
	        }
	        }

			private boolean ExistPreview(HorizontalPanel labeltypo, Entity act) {
				for (int i = 0; i < labeltypo.getWidgetCount(); i++) {
					Entity temp = ((ButtonTipo)labeltypo.getWidget(i)).getEntidad();
					if (temp.getID()==act.getID()) return true;
					
				}
				return false;
			}
	        });
        finder= new FinderGrafo(Cata);
        scrollPanel.setWidget(finder);
        finder.setSize("100%", "100%");
        finder.RefrescaLosDatos();
        
	}

	protected void setAllowCreate(boolean state)
	{
		if (state) menuBar.addItem(mntmNewItem);
	}
	
	public static void RestoreFinderButtonActio(){
		FinderGrafo.setButtonTipoGrafo(new BotonesStackPanelReaderSelectMio("prototipo", new VerticalPanel(),HP));
		 FinderGrafo.setBotonClickGrafo(new ClickHandler() {

		        public void onClick(ClickEvent event) {
		        	BotonesStackPanelReaderSelectMio BS=((BotonesStackPanelReaderSelectMio) event.getSource());
		        Entity Act=BS.getEntidad();
		        
		        if (Act instanceof File)
		        {
		        	ButtonTipo nuevo=new ButtonTipo(Act,CT.getTexto(),BS.getLabeltypo());
		        	nuevo.addClickHandler(new ClickHandler() {
						
						public void onClick(ClickEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
							
						}
					});
				
		        	nuevo.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
						}
					});
					

		        	nuevo.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
					}
				});
					

		        	nuevo.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							
							((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
						
					}
				});

		        	nuevo.setStyleName("gwt-ButtonCenter");
		        	nuevo.addClickHandler(new ClickHandler() {
						
						public void onClick(ClickEvent event) {
							ButtonTipo Yo=(ButtonTipo)event.getSource();
							Yo.getPertenezco().remove(Yo);
							
							
						}
					});
		        	if (!ExistPreview(BS.getLabeltypo(),Act))
		        			BS.getLabeltypo().add(nuevo);
		        	else Window.alert(ActualUser.getLanguage().getE_ExistBefore());
		        }
		        }

				private boolean ExistPreview(HorizontalPanel labeltypo, Entity act) {
					for (int i = 0; i < labeltypo.getWidgetCount(); i++) {
						Entity temp = ((ButtonTipo)labeltypo.getWidget(i)).getEntidad();
						if (temp.getID()==act.getID()) return true;
						
					}
					return false;
				}
		        });
	}
}
