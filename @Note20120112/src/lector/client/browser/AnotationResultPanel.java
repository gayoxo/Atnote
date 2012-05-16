package lector.client.browser;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AnotationResultPanel extends DialogBox {
	
	public AnotationResultPanel(VerticalPanel V) {
		
		super(true);
		setHTML("Resultado");
		ScrollPanel simplePanel = new ScrollPanel();
		simplePanel.setAlwaysShowScrollBars(true);
		setWidget(simplePanel);
		simplePanel.setSize("469px", "508px");
		simplePanel.add(V);
		V.setSize("100%", "100%");
	}

	public String getHeight() {
		return "447px";
	}
}
