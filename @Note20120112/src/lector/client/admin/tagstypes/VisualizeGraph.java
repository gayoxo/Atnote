package lector.client.admin.tagstypes;

import lector.client.catalogo.grafo.PanelGrafo;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

public class VisualizeGraph extends DialogBox {

	public VisualizeGraph(Long long1) {
		super(true);
		setSize("100%", "100%");
		setHTML("Graph");
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		setWidget(dockLayoutPanel);
		dockLayoutPanel.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-100+"px");
		
		MenuBar menuBar = new MenuBar(false);
		dockLayoutPanel.addNorth(menuBar, 1.9);
		
		MenuItem mntmNewItem = new MenuItem("Close", false, new Command() {
			public void execute() {
				hide();
			}
		});
		mntmNewItem.setHTML("Close");
		menuBar.addItem(mntmNewItem);
		ScrollPanel simplePanel = new ScrollPanel();
		dockLayoutPanel.add(simplePanel);
		simplePanel.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-124+"px");
		PanelGrafo PG=new PanelGrafo(long1);
		simplePanel.add(PG);
//		PanelFinderKey PFK=new PanelFinderKey(long1);
//		simplePanel.add(PFK);
		
		
	}

}
