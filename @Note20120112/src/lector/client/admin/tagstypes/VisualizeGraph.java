package lector.client.admin.tagstypes;

import lector.client.catalogo.grafo.PanelGrafo;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class VisualizeGraph extends PopupPanel {

	public VisualizeGraph(Long long1) {
		super(true);
		
		SimplePanel simplePanel = new SimplePanel();
		setWidget(simplePanel);
		simplePanel.setSize("100%", "100%");
		PanelGrafo PG=new PanelGrafo(long1);
		simplePanel.add(PG);
		
	}

}
