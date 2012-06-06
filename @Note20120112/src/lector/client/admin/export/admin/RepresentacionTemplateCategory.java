package lector.client.admin.export.admin;

import lector.client.admin.export.template.Template;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RepresentacionTemplateCategory extends Composite {

	private Template T;
	
	public RepresentacionTemplateCategory(Template t) {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
	}

}
