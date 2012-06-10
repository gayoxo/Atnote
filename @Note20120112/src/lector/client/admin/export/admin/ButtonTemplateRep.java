package lector.client.admin.export.admin;

import lector.client.admin.export.template.TemplateCategory;

import com.google.gwt.user.client.ui.Button;

public class ButtonTemplateRep extends Button {

	private TemplateCategory T;
	
	public ButtonTemplateRep(String name, TemplateCategory t) {
		super(name);
		T=t;
	}
	
	public TemplateCategory getT() {
		return T;
	}

}
