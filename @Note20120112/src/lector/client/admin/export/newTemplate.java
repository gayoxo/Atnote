package lector.client.admin.export;

import lector.client.admin.export.template.Template;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.InformationConstants;
import lector.client.login.ActualUser;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.CheckBox;

public class newTemplate extends PopupPanel {

	private TextBox textBox;
	private NewAdminTemplate Yo;
	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	public newTemplate(NewAdminTemplate yo) {
		super(true);
		setGlassEnabled(true);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("253px", "131px");
		Yo=yo;
		
		Label lblNewLabel = new Label(InformationConstants.TYPE_NAME_FOR_THE_NEW_EXPORT_TEMPLATE);
		lblNewLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(lblNewLabel);
		verticalPanel.setCellVerticalAlignment(lblNewLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		lblNewLabel.setSize("100%", "100%");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setSize("100%", "100%");
		
		textBox = new TextBox();
		horizontalPanel_1.add(textBox);
		textBox.setWidth("184px");
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel_2);
		horizontalPanel_2.setSize("100%", "100%");
		
		CheckBox chckbxNewCheckBox = new CheckBox(InformationConstants.ORDER_EDITABLE);
		horizontalPanel_2.add(chckbxNewCheckBox);
		chckbxNewCheckBox.setSize("240px", "19px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setSpacing(5);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		Button Create = new Button("Create");
		Create.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Template T=new Template();
				T.setName(textBox.getText());
				T.setUserApp(ActualUser.getUser().getId());
				//TODO LLamada
				}
		});
		horizontalPanel.add(Create);
		
		Button Cancel = new Button("Cancel");
		Cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		horizontalPanel.add(Cancel);
	}

}
