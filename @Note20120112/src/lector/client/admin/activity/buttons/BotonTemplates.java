package lector.client.admin.activity.buttons;

import lector.share.model.Language;
import lector.share.model.Template;

import com.google.gwt.user.client.ui.Button;

public class BotonTemplates extends Button {
 
	
	private Template template;
	public BotonTemplates(Template templatein) {
		super(templatein.getName());
		template=templatein;
		
	}
	
public Template getTemplate() {
	return template;
}

public void setTemplate(Template template) {
	this.template = template;
}


}
