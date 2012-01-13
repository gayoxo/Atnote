package lector.client.admin.tagstypes;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.catalogo.Finder2;
import lector.client.catalogo.client.Catalog;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

public class PopUpFinderSelectorExist extends PopupPanel {

	private Finder2 finder;

	public PopUpFinderSelectorExist(Catalog CatalogoIn) {
		super(false);
		setModal(true);
		setGlassEnabled(true);
		finder = new Finder2();
		SimplePanel S= new SimplePanel();
		S.setSize("100%", "100%");
		S.add(finder);
		finder.setButtonTipo(new BotonesStackPanelAdministracionMio(
				"prototipo", new VerticalPanel(), new VerticalPanel()));
		finder.setBotonClick(new ClickHandler() {

			public void onClick(ClickEvent event) {
				//TODO Resolver que hago al pulsar.
				
			}
		});

		finder.setSize("100%", "100%");
		
		DockLayoutPanel DLP=new DockLayoutPanel(Unit.EM);
		setWidget(DLP);
		DLP.setSize("628px", "570px");
		
		MenuBar menuBar = new MenuBar(false);
		DLP.addNorth(menuBar, 1.9);
		
		MenuItem mntmClose = new MenuItem("Close", false, new Command() {
			public void execute() {
				hide();
			}
		});
		menuBar.addItem(mntmClose);
		DLP.add(S);
		finder.setCatalogo(CatalogoIn);
	}

}
