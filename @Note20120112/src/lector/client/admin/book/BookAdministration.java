package lector.client.admin.book;

import java.util.ArrayList;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.StackPanelMio;
import lector.client.controler.Controlador;
import lector.client.login.ActualUser;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class BookAdministration implements EntryPoint {

	private StackPanelMio stackPanel_1;
	private VerticalPanel Selected;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	public void onModuleLoad() {
		RootPanel RootTXOriginal = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root2");
		RootTXOriginal.setStyleName("Root");

		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		menuBar.setSize("100%", "");

		MenuItem mntmNewItem = new MenuItem("New item", false, (Command) null);
		mntmNewItem.setHTML("Get a Book");
		mntmNewItem.setEnabled(false);
		menuBar.addItem(mntmNewItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		MenuItem mntmAddbook = new MenuItem("Add Book", false, new Command() {
			public void execute() {
				Controlador.change2Searcher();
			}
		});
		menuBar.addItem(mntmAddbook);

		MenuItem mntmNewItem_1 = new MenuItem("New item", false, new Command() {
			public void execute() {
				int SelectedWidgetCount = Selected.getWidgetCount();
				for (int i = 0; i < SelectedWidgetCount; i++) {
					BotonesStackPanelAdministracionMio BDPM = (BotonesStackPanelAdministracionMio) Selected
							.getWidget(i);
					ActualUser.getUser().getBookIds().remove(BDPM.getText());
				}

				Selected.clear();
				bookReaderServiceHolder.saveUser(ActualUser.getUser(),
						new AsyncCallback<Boolean>() {

							public void onSuccess(Boolean result) {
								Selected.clear();

							}

							public void onFailure(Throwable caught) {
								Window.alert("There has been an erron when trying to remove the books of the user");
							}
						});
			}
		});
		mntmNewItem_1.setHTML("Remove");
		menuBar.addItem(mntmNewItem_1);

		MenuItemSeparator separator_1 = new MenuItemSeparator();
		menuBar.addSeparator(separator_1);

		MenuItem mntmNewItem_2 = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		menuBar.addItem(mntmNewItem_2);

		SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel();
		RootTXOriginal.add(splitLayoutPanel, 0, 25);
		splitLayoutPanel.setSize("100%", "100%");

		VerticalPanel simplePanel = new VerticalPanel();
		splitLayoutPanel.addWest(simplePanel, 220.0);
		simplePanel.setWidth("100%");

		ScrollPanel scrollPanel = new ScrollPanel();
		simplePanel.add(scrollPanel);
		scrollPanel.setSize("100%", "100%");

		SimplePanel simplePanel_1 = new SimplePanel();
		splitLayoutPanel.add(simplePanel_1);
		simplePanel_1.setSize("100%", "100%");

		Selected = new VerticalPanel();
		simplePanel_1.setWidget(Selected);
		Selected.setSize("100%", "100%");

		stackPanel_1 = new StackPanelMio();
		scrollPanel.setWidget(stackPanel_1);
		// simplePanel.add(stackPanel_1);

		stackPanel_1.setBotonTipo(new BotonesStackPanelAdministracionMio(
				"prototipo", new VerticalPanel(), Selected));
		stackPanel_1.setBotonClick(new ClickHandler() {

			public void onClick(ClickEvent event) {
				((BotonesStackPanelAdministracionMio) event.getSource()).Swap();
			}
		});

		ArrayList<String> ListaBooks = ActualUser.getUser().getBookIds();
		if (ListaBooks.size() < 10) {
			Long IDi = 0l;
			for (String string : ListaBooks) {
				EntidadLibro E = new EntidadLibro(string, IDi);
				stackPanel_1.addBotonLessTen(E);
				IDi++;
			}

		} else {
			Long IDi = 0l;
			for (String string : ListaBooks) {
				EntidadLibro E = new EntidadLibro(string, IDi);
				stackPanel_1.addBoton(E);
				IDi++;
			}
		}
		stackPanel_1.setSize("100%", "100%");
		stackPanel_1.ClearEmpty();

	}
}
