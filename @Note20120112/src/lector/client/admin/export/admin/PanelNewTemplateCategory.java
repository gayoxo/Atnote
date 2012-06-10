package lector.client.admin.export.admin;

import lector.client.admin.export.template.TemplateCategory;

import com.google.gwt.user.client.ui.PopupPanel;

public class PanelNewTemplateCategory extends PopupPanel {

	private TemplateCategory TC;
	
	public PanelNewTemplateCategory(TemplateCategory tC) {
		TC=tC;
	}

}
