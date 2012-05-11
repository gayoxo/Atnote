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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;

public class SelectorTypePopUpAnnotacionAdministracion extends PopupPanel {
	private Finder finder;
	private Finder finderrefresh;
	protected MenuItem mntmNewItem;
	protected MenuBar menuBar;
	private CatalogTipo CT;

	public SelectorTypePopUpAnnotacionAdministracion(HorizontalPanel penelBotonesTipo,Catalog Cata, CatalogTipo catalog2,Finder refresh) {
		super(true);
		CT=catalog2;
		SimplePanel verticalPanel = new SimplePanel();
		finderrefresh=refresh;
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
        DockPanel dockPanel = new DockPanel();
        verticalPanel.add(dockPanel);
        dockPanel.setSize("100%", "100%");
        
        menuBar = new MenuBar(false);
        dockPanel.add(menuBar, DockPanel.NORTH);
        dockPanel.setCellHeight(menuBar, "15px");
        menuBar.setSize("100%", "20px");
        
        mntmNewItem = new MenuItem(ActualUser.getLanguage().getNew(), false, new Command() {
        	public void execute() {
        		SelectorNewBetweenTypeAndFolder SBFF=new SelectorNewBetweenTypeAndFolder(finder,finderrefresh);
				SBFF.center();
				SBFF.setModal(true);
				hide();
        	}
        });
      menuBar.addItem(mntmNewItem);
        mntmNewItem.setSize("50%", "");
        
        MenuItem mntmNewItem_2 = new MenuItem(ActualUser.getLanguage().getFromExist(), false, new Command() {
        	public void execute() {
        		PopUpFinderSelectorExistAnnotation PAFSE=new PopUpFinderSelectorExistAnnotation(finder.getCatalogo(),finder.getTopPath(),finderrefresh);
				PAFSE.center();
				PAFSE.setModal(true);
				hide();
        	}
        });
        menuBar.addItem(mntmNewItem_2);
        mntmNewItem_2.setSize("50%", "");
        
        MenuItem mntmNewItem_1 = new MenuItem("New item", false, new Command() {
        	public void execute() {
        		hide();
        	}
        });
        mntmNewItem_1.setHTML(ActualUser.getLanguage().getCancel());
        menuBar.addItem(mntmNewItem_1);
        
        SimplePanel scrollPanel = new SimplePanel();
        dockPanel.add(scrollPanel, DockPanel.CENTER);
       // scrollPanel.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-100+"px");
        dockPanel.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-100+"px");
        FinderGrafo.setButtonTipoGrafo(new BotonesStackPanelReaderSelectMio("prototipo", new VerticalPanel(),penelBotonesTipo));
        
        
        FinderGrafo.setBotonClickGrafo(new ClickHandler() {

	        public void onClick(ClickEvent event) {
	        	
			}
	        });
        finder= new Finder();
        finder.setCatalogo(Cata);
        scrollPanel.setWidget(finder);
        finder.RefrescaLosDatos();
        
	}

	protected void setAllowCreate(boolean state)
	{
		if (state) menuBar.addItem(mntmNewItem);
	}
}
