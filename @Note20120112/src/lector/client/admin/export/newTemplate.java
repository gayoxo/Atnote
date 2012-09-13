package lector.client.admin.export;

import lector.client.admin.export.admin.PanelNewTemplateCategory;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;
import lector.share.model.Template;
import lector.share.model.TemplateCategory;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.CheckBox;

public class newTemplate extends PopupPanel {

	private TextBox textBox;
	private NewAdminTemplate Yo;
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	private CheckBox chckbxNewCheckBox;

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
		
		chckbxNewCheckBox = new CheckBox(InformationConstants.ORDER_EDITABLE);
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
				T.setModificable(chckbxNewCheckBox.getValue());
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto("Loading...");
				exportServiceHolder.saveTemplate(T, new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
						LoadingPanel.getInstance().hide();
						Yo.refresh();
						hide();
						
					}
					
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ErrorConstants.ERROR_SAVING_TEMPLATE);
						
					}
				});
				}
		});
		horizontalPanel.add(Create);
		
		Create.setStyleName("gwt-ButtonCenter");
		Create.setSize("100%", "100%");

		Create.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		
		Create.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
		});
		
		Create.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});
		
		
		
		
		Button Cancel = new Button("Cancel");
		Cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		horizontalPanel.add(Cancel);
		Cancel.setStyleName("gwt-ButtonCenter");
		Cancel.setSize("100%", "100%");

		Cancel.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		
		Cancel.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
		});
		
		Cancel.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});
	}

}
