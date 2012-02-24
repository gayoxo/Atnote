package lector.client.reader.hilocomentarios;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class JeraquiaSimulada extends Composite {

	private VerticalPanel verticalPanel;

	public JeraquiaSimulada() {
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		
		SimplePanel simplePanel = new SimplePanel();
		horizontalPanel.add(simplePanel);
		simplePanel.setWidth("40px");
		
		verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);
		
	}

	public VerticalPanel getVerticalPanel() {
		return verticalPanel;
	}
	

}
