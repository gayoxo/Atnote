package lector.client.admin.export.admin;

import java.util.ArrayList;

import lector.client.admin.export.template.TemplateCategory;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NewPanelRename extends PopupPanel {
	
	
	private static String SAVE = "Save";
	private static String CLOSE = "Close";
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	private TemplateCategory TC;
	private TextBox textBox;
	
	public NewPanelRename(RepresentacionTemplateCategory tC) {
		TC=tC.getT();
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSpacing(1);
		setWidget(verticalPanel);
		verticalPanel.setSize("340px", "155px");
		
		Label lblNewLabel = new Label(InformationConstants.RENAME_TEMPLATE_CATEGORY + TC.getName());
		verticalPanel.add(lblNewLabel);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel);
		
		Label lblNewLabel_1 = new Label(InformationConstants.NAME);
		horizontalPanel.add(lblNewLabel_1);
		
		textBox = new TextBox();
		horizontalPanel.add(textBox);
		
		HorizontalPanel Botonera = new HorizontalPanel();
		Botonera.setSpacing(5);
		verticalPanel.add(Botonera);
		
		Button btnNewButton = new Button(CLOSE);
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		btnNewButton.setStyleName("gwt-ButtonCenter");
		btnNewButton.setSize("100%", "100%");

		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		
		btnNewButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
		});
		
		btnNewButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});
		
		Botonera.add(btnNewButton);
		
		Button btnNewButton_1 = new Button(SAVE);
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!textBox.getText().isEmpty()){
					
					TC.setName(textBox.getText()); 
//					TemplateCategory T=new TemplateCategory(textBox.getText(), new ArrayList<Long>(), new ArrayList<Long>(), TC.getId(), TC.getTemplateId());
//					TC.setOrder(TC.getSubCategories().size()+1);
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto(InformationConstants.SAVING);
					exportServiceHolder.saveTemplateCategory(TC , new AsyncCallback<Void>() {
						
						public void onSuccess(Void result) {
							LoadingPanel.getInstance().hide();
							EditTemplate.getPGT().refresh();
							hide();
							
						}
						
						public void onFailure(Throwable caught) {
							LoadingPanel.getInstance().hide();
							Window.alert(ErrorConstants.ERROR_SAVING_NEW_TEMPLATE_CATEGORY);
							
						}
					});
				}
			}
		});
		Botonera.add(btnNewButton_1);
	
	
	btnNewButton_1.setStyleName("gwt-ButtonCenter");
	btnNewButton_1.setSize("100%", "100%");

	btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
		public void onMouseDown(MouseDownEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
		}
	});
	
	btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
		public void onMouseOut(MouseOutEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
	}
	});
	
	btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
		public void onMouseOver(MouseOverEvent event) {
			
			((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
		
	}
});
	
	}
	

}
