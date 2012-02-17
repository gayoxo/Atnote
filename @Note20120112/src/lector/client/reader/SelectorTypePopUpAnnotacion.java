package lector.client.reader;

import lector.client.catalogo.Finder;
import lector.client.catalogo.Finder2;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.controler.Constants;
import lector.client.login.ActualUser;
import lector.client.reader.PanelTextComent.CatalogTipo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;

public class SelectorTypePopUpAnnotacion extends PopupPanel {
	private static Finder2 finder;
	protected MenuItem mntmNewItem;
	protected MenuBar menuBar;
	private CatalogTipo CT;

	public SelectorTypePopUpAnnotacion(HorizontalPanel penelBotonesTipo,Catalog Cata, CatalogTipo catalog2) {
		super(true);
		CT=catalog2;
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("604px", "558px");
        DockPanel dockPanel = new DockPanel();
        verticalPanel.add(dockPanel);
        dockPanel.setSize("100%", "100%");
        
        menuBar = new MenuBar(false);
        dockPanel.add(menuBar, DockPanel.NORTH);
        menuBar.setSize("100%", "100%");
        
        mntmNewItem = new MenuItem(ActualUser.getLanguage().getNew(), false, new Command() {
        	public void execute() {
        		PopUpNewOSelect SBTF=new PopUpNewOSelect(finder.getCatalogo(),finder.getTopPath(),finder);
        		SBTF.center();
        	}
        });
        if (ActualUser.getUser().getProfile().equals(Constants.PROFESSOR)) menuBar.addItem(mntmNewItem);
        mntmNewItem.setSize("100%", "100%");
        
        MenuItem mntmNewItem_1 = new MenuItem("New item", false, new Command() {
        	public void execute() {
        		hide();
        	}
        });
        mntmNewItem_1.setHTML(ActualUser.getLanguage().getCancel());
        menuBar.addItem(mntmNewItem_1);
        
        ScrollPanel scrollPanel = new ScrollPanel();
        dockPanel.add(scrollPanel, DockPanel.CENTER);
        scrollPanel.setSize("100%", "529px");
        
        finder= new Finder2();
        finder.setCatalogo(Cata);
        scrollPanel.setWidget(finder);
        finder.setSize("100%", "528px");
        finder.RefrescaLosDatos();
        finder.setButtonTipo(new BotonesStackPanelReaderSelectMio("prototipo", new VerticalPanel(),penelBotonesTipo));
        
        
        finder.setBotonClick(new ClickHandler() {

	        public void onClick(ClickEvent event) {
	        	BotonesStackPanelReaderSelectMio BS=((BotonesStackPanelReaderSelectMio) event.getSource());
	        Entity Act=BS.getEntidad();
	        
	        if (Act instanceof File)
	        {
	        	ButtonTipo nuevo=new ButtonTipo(Act,CT.getTexto(),BS.getLabeltypo());
	        	nuevo.addClickHandler(new ClickHandler() {
					
					public void onClick(ClickEvent event) {
						ButtonTipo Yo=(ButtonTipo)event.getSource();
						Yo.getPertenezco().remove(Yo);
						
					}
				});
	        	if (!ExistPreview(BS.getLabeltypo(),Act))
	        			BS.getLabeltypo().add(nuevo);
	        	else Window.alert(ActualUser.getLanguage().getE_ExistBefore());
	        	hide();
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

	protected void setAllowCreate(boolean state)
	{
		if (state) menuBar.addItem(mntmNewItem);
	}
}
