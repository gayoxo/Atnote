package lector.client.admin.tagstypes;


import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.Finder;
import lector.client.catalogo.FinderGrafo;
import lector.client.catalogo.FinderKeys;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;
import lector.client.controler.Constants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;

public class PopUpFinderSelectorExist extends PopupPanel {

	
	//TODO Cambiar finder
	private FinderKeys finder;
	private Entity father;

	public PopUpFinderSelectorExist(Catalog CatalogoIn, Entity entity) {
		super(true);
		setModal(true);
		setGlassEnabled(true);
		
		
		
		VerticalPanel DLP=new VerticalPanel();
		setWidget(DLP);
//		DLP.setSize("628px", "570px");
	//	DLP.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-100+"px");
		
		
		
		father=entity;
		finder = new FinderKeys();
		finder.setCatalogo(CatalogoIn);
		SimplePanel S= new SimplePanel();
		S.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-100+"px");
		S.setWidget(finder);
		FinderKeys.setButtonTipo(new BotonesStackPanelAdministracionMio(
				"prototipo", new VerticalPanel(), new VerticalPanel(),finder));
		
		FinderKeys.setBotonClick(new ClickHandlerMioSeleccion(this)
				
//				new ClickHandler() {
//
//			public void onClick(ClickEvent event) {
//				Entity E=((BotonesStackPanelAdministracionMio)event.getSource()).getEntidad();
//				AsyncCallback<Void> LLamada=new AsyncCallback<Void>() {
//					
//					public void onSuccess(Void result) {
//						EditorTagsAndTypes.LoadBasicTypes();
//						hide();
//						
//					}
//					
//					public void onFailure(Throwable caught) {
//						Window.alert("Error in copy in folder");
//						
//					}
//				};
//				if (father==null)
//				bookReaderServiceHolder.addFather(E.getID(), Constants.CATALOGID, LLamada);
//				else 
//					bookReaderServiceHolder.addFather(E.getID(), father.getID(), LLamada);
//				
//			}
//		}
//				
				);

		
		
		
		MenuBar menuBar = new MenuBar(false);
		DLP.add(menuBar);
		menuBar.setHeight("24px");
		
		MenuItem mntmClose = new MenuItem("Close", false, new Command() {
			public void execute() {
				hide();
				EditorTagsAndTypes.LoadBasicTypes();
			}
		});
		menuBar.addItem(mntmClose);
		DLP.add(S);
		finder.setSize("100", "100%");
		finder.RefrescaLosDatos();

	}
	
	
	public Entity getFather() {
		return father;
	}
	public Finder getFinder() {
		return finder;
	}
	

}
