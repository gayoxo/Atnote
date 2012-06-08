package lector.client.admin.export.admin;

import lector.client.admin.export.template.Template;
import lector.client.admin.export.template.TemplateCategory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;

public class RepresentacionTemplateCategory extends Composite {

	private TemplateCategory T;
	private VerticalPanel AnnotPanel;
	private RepresentacionTemplateCategory Father;
	
	public RepresentacionTemplateCategory(TemplateCategory t,RepresentacionTemplateCategory father) {
		
		T=t;
		Father=father;
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setHeight("100%");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setHeight("49px");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setSize("100%", "48px");
		
		Button btnNewButton_1 = new Button("<-");
		horizontalPanel_1.add(btnNewButton_1);
		btnNewButton_1.setHeight("49px");
		
		Button btnNewButton_2 = new Button("->");
		horizontalPanel_1.add(btnNewButton_2);
		btnNewButton_2.setHeight("49px");
		
		Button btnNewButton = new Button(T.getName());
		horizontalPanel.add(btnNewButton);
		btnNewButton.setSize("77px", "49px");
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		horizontalPanel.add(verticalPanel_1);
		verticalPanel_1.setHeight("49px");
		
		Button btnNewButton_3 = new Button("UP");
		verticalPanel_1.add(btnNewButton_3);
		btnNewButton_3.setWidth("100%");
		
		Button btnNewButton_4 = new Button("DOWN");
		verticalPanel_1.add(btnNewButton_4);
		btnNewButton_4.setSize("100%", "");
		
		AnnotPanel = new VerticalPanel();
		verticalPanel.add(AnnotPanel);
		AnnotPanel.setWidth("100%");
		
	}

	
	public TemplateCategory getT() {
		return T;
	}


	public void addSon(RepresentacionTemplateCategory nuevo) {
		AnnotPanel.add(nuevo);
		
	}
}
