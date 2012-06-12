package lector.client.admin.export.admin;

import java.util.ArrayList;

import lector.client.admin.export.newTemplate;
import lector.client.admin.export.template.TemplateCategory;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.controler.Constants;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class PanelNewTemplateCategory extends PopupPanel {

	private static String SAVE = "Save";
	private static String CLOSE = "Close";
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	private TemplateCategory TC;
	private TextBox textBox;
	private EditTemplate PadreLLamada;
	
	public PanelNewTemplateCategory(TemplateCategory tC, EditTemplate yO) {
		
		TC=tC;
		PadreLLamada=yO;
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSpacing(1);
		setWidget(verticalPanel);
		verticalPanel.setSize("340px", "155px");
		
		Label lblNewLabel = new Label(InformationConstants.CREATE_NEW_TEMPLATECATEGORY + TC.getName());
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
		Botonera.add(btnNewButton);
		
		Button btnNewButton_1 = new Button(SAVE);
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!textBox.getText().isEmpty()){
					TemplateCategory T=new TemplateCategory(textBox.getText(), new ArrayList<Long>(), new ArrayList<Long>(), TC.getId(), TC.getTemplateId());
					exportServiceHolder.saveTemplateCategory(T , new AsyncCallback<Void>() {
						
						public void onSuccess(Void result) {
							PadreLLamada.refresh();
							hide();
							
						}
						
						public void onFailure(Throwable caught) {
							Window.alert(ErrorConstants.ERROR_SAVING_NEW_TEMPLATE_CATEGORY);
							
						}
					});
				}
			}
		});
		Botonera.add(btnNewButton_1);
		TC=tC;
	}

}
