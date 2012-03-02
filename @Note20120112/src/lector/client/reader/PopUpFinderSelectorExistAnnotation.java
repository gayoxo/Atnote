package lector.client.reader;


import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.Finder;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;
import lector.client.controler.Constants;
import lector.client.login.ActualUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

public class PopUpFinderSelectorExistAnnotation extends PopupPanel {

	private Finder finder;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private Entity father;
	private Finder finderrefresh;

	public PopUpFinderSelectorExistAnnotation(Catalog CatalogoIn, Entity entity, Finder finderin) {
		super(false);
		setModal(true);
		setGlassEnabled(true);
		father=entity;
		finder = new Finder();
		finderrefresh=finderin;
		SimplePanel S= new SimplePanel();
		S.setSize("100%", "100%");
		S.add(finder);
		finder.setButtonTipo(new BotonesStackPanelAdministracionMio(
				"prototipo", new VerticalPanel(), new VerticalPanel(),finder));
		finder.setBotonClick(new ClickHandler() {

			public void onClick(ClickEvent event) {
				Entity E=((BotonesStackPanelAdministracionMio)event.getSource()).getEntidad();
				AsyncCallback<Void> LLamada=new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
//						finderrefresh.RefrescaLosDatos();
//						//	scrollPanel.setWidget(finder);
//						finderrefresh.setSize("100%","100%");
						finderrefresh.RefrescaLosDatos();
						//	scrollPanel.setWidget(finder);
						//finderrefresh.setSize("100%","100%");
						hide();
						
					}
					
					public void onFailure(Throwable caught) {
						Window.alert(ActualUser.getLanguage().getE_Saving());
						
					}
				};
				if (father==null)
				bookReaderServiceHolder.addFather(E.getID(), Constants.CATALOGID, LLamada);
				else 
					bookReaderServiceHolder.addFather(E.getID(), father.getID(), LLamada);
				
			}
		});

		finder.setSize("100%", "100%");
		
		DockLayoutPanel DLP=new DockLayoutPanel(Unit.EM);
		setWidget(DLP);
		DLP.setSize("628px", "570px");
		
		MenuBar menuBar = new MenuBar(false);
		DLP.addNorth(menuBar, 1.9);
		
		MenuItem mntmClose = new MenuItem(ActualUser.getLanguage().getClose(), false, new Command() {
			public void execute() {
				hide();
			}
		});
		menuBar.addItem(mntmClose);
		DLP.add(S);
		finder.setCatalogo(CatalogoIn);
	}

}
