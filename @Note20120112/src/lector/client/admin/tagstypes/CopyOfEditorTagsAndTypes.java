package lector.client.admin.tagstypes;

import java.util.ArrayList;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.Finder2;
import lector.client.catalogo.StackPanelMio;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.Folder;
import lector.client.controler.Controlador;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class CopyOfEditorTagsAndTypes implements EntryPoint {

	private static VerticalPanel Selected = new VerticalPanel();
	private static FlowPanel Actual = new FlowPanel();
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private static MenuBar menuBar_3;
	private static MenuBar menuBar_2;
	private static Finder2 finder;
	private static StackPanelMio stackPanel_1;
	private static Catalog catalogo;

	public CopyOfEditorTagsAndTypes() {
		menuBar_2 = new MenuBar(false);
		Actual.add(menuBar_2);
		menuBar_2.setWidth("100%");
		
		
		MenuItem mntmNewItem_2 = new MenuItem("Avariable", false,
				(Command) null);
		mntmNewItem_2.setEnabled(false);
		mntmNewItem_2.setHTML("Avariable");
		menuBar_2.addItem(mntmNewItem_2);

		menuBar_3 = new MenuBar(false);
		Selected.add(menuBar_3);
		menuBar_3.setWidth("100%");

		MenuItem mntmSelected = new MenuItem("Selected", false, (Command) null);
		mntmSelected.setHTML("Selected");
		mntmSelected.setEnabled(false);
		menuBar_3.addItem(mntmSelected);
		
		finder = new Finder2();
		Actual.add(finder);
		finder.setButtonTipo(new BotonesStackPanelAdministracionMio(
				"prototipo", new VerticalPanel(), Selected));
		finder.setBotonClick(new ClickHandler() {

			public void onClick(ClickEvent event) {
				BotonesStackPanelAdministracionMio BS = ((BotonesStackPanelAdministracionMio) event
						.getSource());
				BS.Swap();
				
			}
		});

		finder.setSize("100%", "100%");

		stackPanel_1 = new StackPanelMio();
		stackPanel_1.setBotonTipo(new BotonesStackPanelAdministracionMio(
				"prototipo", new VerticalPanel(), Selected));
		stackPanel_1.setBotonClick(new ClickHandler() {

			public void onClick(ClickEvent event) {
				((BotonesStackPanelAdministracionMio) event.getSource()).Swap();
			}
		});
		stackPanel_1.setSize("100%", "100%");
	}

	public void onModuleLoad() {

		RootPanel RootTXOriginal = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root");
		RootTXOriginal.setStyleName("Root");

		finder.setCatalogo(catalogo);
		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		
		MenuItem mntmTypesTags = new MenuItem("Types & Tags Administration for Catalog: " + catalogo.getCatalogName(), false, (Command) null);
		mntmTypesTags.setEnabled(false);
		mntmTypesTags.setHTML("Types & Tags Administration\r\n");
		menuBar.addItem(mntmTypesTags);
		
		MenuItemSeparator separator_1 = new MenuItemSeparator();
		menuBar.addSeparator(separator_1);

		MenuItem mntmNewItem_3 = new MenuItem("New", false, new Command() {

			public void execute() {
					SelectBetweenFileOrFolderInNew.setCatalog(catalogo);
					SelectBetweenFileOrFolderInNew SBFF=new SelectBetweenFileOrFolderInNew(finder.getTopPath());
					SBFF.center();
					SBFF.setModal(true);
				
			}
		});
		mntmNewItem_3.setHTML("New");
		menuBar.addItem(mntmNewItem_3);

		MenuItem mntmMerge = new MenuItem("Merge", false, new Command() {

			public void execute() {
				int Unir = Selected.getWidgetCount();
				if (Unir >= 3) {

					ArrayList<BotonesStackPanelAdministracionMio> ListaUnir = new ArrayList<BotonesStackPanelAdministracionMio>();
					for (int i = 1; i < Unir; i++) {
						ListaUnir
								.add(((BotonesStackPanelAdministracionMio) Selected
										.getWidget(i)));
					}

					MergeSelector.setCatalog(catalogo);
					MergeSelector MS = new MergeSelector(ListaUnir);
					MS.center();
					MS.setModal(true);

				} else {
					Window.alert("There are less than two elements to merge");
				}

			}

		});
		mntmMerge.setEnabled(true);
		menuBar.addItem(mntmMerge);

		MenuItem mntmRename = new MenuItem("Rename", false, new Command() {

			public void execute() {
				int renombrar = Selected.getWidgetCount();
				for (int i = 1; i < renombrar; i++) {

					BotonesStackPanelAdministracionMio Renombrar = ((BotonesStackPanelAdministracionMio) Selected
							.getWidget(i));
						RenameTypos(Renombrar.getEntidad());

				}

			}

			private void RenameTypos(Entity entity) {

				NewTypeRename TR = new NewTypeRename(entity, entity.getFather());
				TR.setModal(true);
				TR.center();
			}
		});
		mntmRename.setHTML("Rename");
		menuBar.addItem(mntmRename);

		MenuItem mntmNewItem = new MenuItem("Delete", false, new Command() {

			public void execute() {
				int Borrar = Selected.getWidgetCount();
				for (int i = 1; i < Borrar; i++) {

					BotonesStackPanelAdministracionMio Delete = ((BotonesStackPanelAdministracionMio) Selected
							.getWidget(i));

						BorrarTypos(Delete.getEntidad());


				}

			}

			private void BorrarTypos(Entity delete) {
				AsyncCallback<Void> callback = new AsyncCallback<Void>() {

					public void onFailure(Throwable caught) {
						Window.alert("Error in Delete");

					}

					public void onSuccess(Void result) {

						LoadBasicTypes();

					}
				};
				if (delete instanceof Folder)
					bookReaderServiceHolder.deleteFolder(delete.getID(),
							callback);
				else
					
					bookReaderServiceHolder.deleteFile(delete.getID(), callback);

			}

			});
		menuBar.addItem(mntmNewItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
//		MenuBar menuBar_1 = new MenuBar(true);

//		mntmMenuTipos = new MenuItem("Types", false, menuBar_1);
//		mntmMenuTipos.setEnabled(false);
//		mntmMenuTipos.setHTML("Types");
//
//		mntmTypes = new MenuItem("Types", false, new Command() {
//
//			public void execute() {
//				myState = state.Typos;
//				mntmMenuTipos.setHTML("Types");
//				LoadBasicTypes();
//				mntmNewItem_1.setEnabled(true);
//				mntmTypes.setEnabled(false);
//			}
//		});
//
//		menuBar_1.addItem(mntmTypes);
//		mntmTypes.setEnabled(false);
//
//		mntmNewItem_1 = new MenuItem("Tags", false, new Command() {
//
//			public void execute() {
//				myState = state.Tags;
//				mntmMenuTipos.setHTML("Tags");
//				LoadBasicTags();
//				mntmNewItem_1.setEnabled(false);
//				mntmTypes.setEnabled(true);
//			}
//		});
//
//		menuBar_1.addItem(mntmNewItem_1);
//		menuBar.addItem(mntmMenuTipos);

		MenuItem mntmSearcher = new MenuItem("Back", false, new Command() {

			public void execute() {
				Controlador.change2CatalogAdmin();
//				if (BeforeBook == null) {
//					Controlador.change2Searcher();
//				} else {
//					Controlador.change2Reader();
//					MainEntryPoint.SetBook(BeforeBook);
//					MainEntryPoint.getTechnicalSpecs().setBook(BeforeBook);
//				}
			}
		});
		mntmSearcher.setHTML("Back");
		menuBar.addItem(mntmSearcher);

		SimplePanel simplePanel_2 = new SimplePanel();
		RootTXOriginal.add(simplePanel_2,0,20);
		//RootTXOriginal.add(simplePanel_2);
		simplePanel_2.setSize("100%", "97%");

		
		SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel();
		simplePanel_2.setWidget(splitLayoutPanel);
		splitLayoutPanel.setSize("100%", "100%");

		splitLayoutPanel.addWest(Actual, 500.0);

		Actual.setStyleName("popup");
		Actual.setSize("100%", "100%");

		SimplePanel simplePanel_1 = new SimplePanel();
		splitLayoutPanel.add(simplePanel_1);
		simplePanel_1.setSize("100%", "100%");
		simplePanel_1.setWidget(Selected);

		Selected.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Selected.setWidth("100%");
		
	
		LoadBasicTypes();
		

	}

	public static void LoadBasicTypes() {
		//
		// Asincronino
		Selected.clear();
		Selected.add(menuBar_3);
		finder.RefrescaLosDatos();
	//	scrollPanel.setWidget(finder);
		finder.setSize("100%","100%");
	}


	
	public static VerticalPanel getSelected() {
		return Selected;
	}
	
	public static Catalog getCatalogo() {
		return catalogo;
	}
	
	public static void setCatalogo(Catalog catalogo) {
		CopyOfEditorTagsAndTypes.catalogo = catalogo;
	}
	
}