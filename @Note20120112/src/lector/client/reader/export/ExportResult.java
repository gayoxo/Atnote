package lector.client.reader.export;

import java.util.ArrayList;

import lector.client.controler.Controlador;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

public class ExportResult implements EntryPoint {

	private static VerticalPanel verticalPanel;


	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		rootPanel.add(dockLayoutPanel,0,0);
		dockLayoutPanel.setSize("100%", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		dockLayoutPanel.addNorth(menuBar, 1.7);
		
		MenuItem mntmNewItem = new MenuItem("New item", false, new Command() {
			public void execute() {
				Controlador.change2Reader();
			}
		});
		mntmNewItem.setHTML("Back");
		menuBar.addItem(mntmNewItem);
		
		ScrollPanel scrollPanel = new ScrollPanel();
		dockLayoutPanel.add(scrollPanel);
		scrollPanel.setSize("100%", "100%");
		
		verticalPanel = new VerticalPanel();
		scrollPanel.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
	}
	
	
	public static void addResult(EnvioExportacion EE)
	{
		if (verticalPanel!=null)
			verticalPanel.add(EE);
	}

}
