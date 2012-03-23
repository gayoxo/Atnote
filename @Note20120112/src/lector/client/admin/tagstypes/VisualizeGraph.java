package lector.client.admin.tagstypes;

import lector.client.catalogo.grafo.PanelGrafo;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class VisualizeGraph extends DialogBox {

	public VisualizeGraph(Long long1) {
		super(true);
		setHTML("Graph");
		
		ScrollPanel simplePanel = new ScrollPanel();
		setWidget(simplePanel);
		simplePanel.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-100+"px");
		PanelGrafo PG=new PanelGrafo(long1);
		simplePanel.add(PG);
		
		
	}

}
