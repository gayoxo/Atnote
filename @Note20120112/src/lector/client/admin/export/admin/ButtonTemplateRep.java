package lector.client.admin.export.admin;

import lector.client.admin.export.template.TemplateCategory;

import com.google.gwt.user.client.ui.Button;

public class ButtonTemplateRep extends Button {

	private RepresentacionTemplateCategory T;
	
	public ButtonTemplateRep(String name, RepresentacionTemplateCategory t) {
		super(name);
		T=t;
	}
	
	public TemplateCategory getT() {
		return T.getT();
	}
	
	public RepresentacionTemplateCategory getTRep() {
		return T;
	}

}
