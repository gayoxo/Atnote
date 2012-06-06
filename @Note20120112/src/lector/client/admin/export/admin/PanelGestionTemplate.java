package lector.client.admin.export.admin;

import lector.client.admin.export.template.Template;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PanelGestionTemplate extends Composite {

	public Template T;
	private VerticalPanel PanelTemplate;
	
	public PanelGestionTemplate(Template t) {
		
		T=t;
		PanelTemplate = new VerticalPanel();
		initWidget(PanelTemplate);
		
		
		
	}

}
