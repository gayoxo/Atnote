package lector.client.admin.export.admin;

import com.google.gwt.user.client.ui.VerticalPanel;

public class VerticalPanelTemplate extends VerticalPanel {

	private RepresentacionTemplateCategory FatherObject;
	
	public VerticalPanelTemplate(RepresentacionTemplateCategory yO) {
		super();
	}

	public RepresentacionTemplateCategory getFatherObject() {
		return FatherObject;
	}
	
	public void setFatherObject(RepresentacionTemplateCategory fatherObject) {
		FatherObject = fatherObject;
	}
}
