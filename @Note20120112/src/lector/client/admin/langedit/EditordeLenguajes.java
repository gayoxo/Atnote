package lector.client.admin.langedit;

import lector.client.admin.langedit.tabs.AnnotationEditor;
import lector.client.admin.langedit.tabs.BrowserEditor;
import lector.client.admin.langedit.tabs.FilterEditor;
import lector.client.admin.langedit.tabs.MainWindowEditor;
import lector.client.admin.langedit.tabs.SpecificationsEditor;
import lector.client.admin.langedit.tabs.VariosEditor;
import lector.client.controler.Controlador;
import lector.share.model.Language;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;

public class EditordeLenguajes implements EntryPoint {

	//welcome

	
	
	private static Language LenguajeActual;
	private DecoratedTabPanel decoratedTabPanel;
	private MenuItem mntmNewItem;
	
	
	public void onModuleLoad() {
		RootPanel RootTXOriginal = RootPanel.get("Original");
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root");
		RootTXOriginal.setStyleName("Root");
		
		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		menuBar.setWidth("100%");
		
		mntmNewItem = new MenuItem("New item", false, (Command) null);
		mntmNewItem.setEnabled(false);
		mntmNewItem.setHTML("Language Editor : " + LenguajeActual.getName());
		menuBar.addItem(mntmNewItem);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuItem mntmNewItem_2 = new MenuItem("New item", false, new Command() {
			public void execute() {
				int actual_widget=decoratedTabPanel.getDeckPanel().getVisibleWidget();
				Widget WidgetActual=decoratedTabPanel.getDeckPanel().getWidget(actual_widget);
				{
					((PanelDecorador)WidgetActual).saveAll();
					
				}
				
			}


		});
		mntmNewItem_2.setHTML("Save");
		menuBar.addItem(mntmNewItem_2);
		
		MenuItemSeparator separator_1 = new MenuItemSeparator();
		menuBar.addSeparator(separator_1);
		
		MenuItem mntmNewItem_1 = new MenuItem("New item", false, new Command() {
			public void execute() {
				int actual_widget=decoratedTabPanel.getDeckPanel().getVisibleWidget();
				//	Window.alert(Integer.toString(actual_widget2));
				//	int actual_widget=event.getItem();
					//Window.alert(Integer.toString(actual_widget));
					Widget WidgetActual=decoratedTabPanel.getDeckPanel().getWidget(actual_widget);
					if (!((PanelDecorador)WidgetActual).isSaved())
						if (Window.confirm("The tab is modificated and not saved, Do you want to save the modification"))
							{
							((PanelDecorador)WidgetActual).saveAll();
							}
					Controlador.change2AdminLenguaje();
			}
		});
		mntmNewItem_1.setHTML("Back");
		menuBar.addItem(mntmNewItem_1);
		
		decoratedTabPanel = new DecoratedTabPanel();
		
		RootTXOriginal.add(decoratedTabPanel);
		decoratedTabPanel.setSize("100%", "100%");
		
		
		String tamano="875px";
		
		//Esta encima porque sino salta error al setear la tab del decorador.
		MainWindowEditor MWE = new MainWindowEditor(LenguajeActual);
		decoratedTabPanel.add(MWE, "Main Window", true);
		MWE.setWidth(tamano);
		
		//Debajo de la declaracion sino da IndexOutOfBounds
		decoratedTabPanel.selectTab(0);
		
		//Ollente para pasar de pestaña
		decoratedTabPanel.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
			public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
				int actual_widget=decoratedTabPanel.getDeckPanel().getVisibleWidget();
			//	Window.alert(Integer.toString(actual_widget2));
			//	int actual_widget=event.getItem();
			//	Window.alert(Integer.toString(actual_widget));
				Widget WidgetActual=decoratedTabPanel.getDeckPanel().getWidget(actual_widget);
				if (!((PanelDecorador)WidgetActual).isSaved())
					if (Window.confirm("The tab is modificated and not saved, Do you want to save the modification"))
						((PanelDecorador)WidgetActual).saveAll();
					
			}
		});
		

		
		SpecificationsEditor Specifications= new SpecificationsEditor(LenguajeActual);
		decoratedTabPanel.add(Specifications, "Specifications", true);
		Specifications.setWidth(tamano);
		
		FilterEditor Filter= new FilterEditor(LenguajeActual);
		decoratedTabPanel.add(Filter, "Filter", true);
		Filter.setWidth(tamano);
		
		AnnotationEditor Anotation= new AnnotationEditor(LenguajeActual);
		decoratedTabPanel.add(Anotation, "Annotation", true);
		Anotation.setWidth(tamano);

		BrowserEditor Browser= new BrowserEditor(LenguajeActual);
		decoratedTabPanel.add(Browser, "Browser", true);
		Browser.setWidth(tamano);
		
		VariosEditor varios= new VariosEditor(LenguajeActual);
		decoratedTabPanel.add(varios, "Others", true);
		varios.setWidth(tamano);
}
	public static Language getLenguajeActual() {
		return LenguajeActual;
	}
	
	public static void setLenguajeActual(Language lenguajeActual) {
		LenguajeActual = lenguajeActual;
	}
	
	
	
	
	
}
