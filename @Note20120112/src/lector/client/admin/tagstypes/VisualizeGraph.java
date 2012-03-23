package lector.client.admin.tagstypes;

import lector.client.catalogo.grafo.PanelGrafo;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class VisualizeGraph extends DialogBox {

	public VisualizeGraph(Long long1) {
		super(true);
		setHTML("Graph");
		
		ScrollPanel simplePanel = new ScrollPanel();
		simplePanel.setStyleName("Dialog");
		setWidget(simplePanel);
		simplePanel.setSize("100%", "100%");
		PanelGrafo PG=new PanelGrafo(long1);
		simplePanel.add(PG);
		
	}

}
