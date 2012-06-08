package lector.client.admin.export.admin;

import java.util.ArrayList;

import lector.client.admin.export.template.Template;
import lector.client.admin.export.template.TemplateCategory;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.controler.ErrorConstants;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PanelGestionTemplate extends Composite {

	public Template T;
	private VerticalPanel PanelTemplate;
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	
	public PanelGestionTemplate(Template t) {
		
		T=t;
		PanelTemplate = new VerticalPanel();
		initWidget(PanelTemplate);
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
		exportServiceHolder.getTemplateCategoriesByIds(T.getCategories(), new AsyncCallback<ArrayList<TemplateCategory>>() {
			
			public void onSuccess(ArrayList<TemplateCategory> result) {
				LoadingPanel.getInstance().hide();
				for (TemplateCategory templateCategory : result) {
					RepresentacionTemplateCategory Nuevo=new RepresentacionTemplateCategory(templateCategory, null);
					PanelTemplate.add(Nuevo);
					ArbitroLlamadasTemplates.getInstance().addElement(Nuevo);
				}
				
				
			}
			
			public void onFailure(Throwable caught) {
				LoadingPanel.getInstance().hide();
				Window.alert(ErrorConstants.ERROR_RETRIVING_TEMPLATE_MASTER_CATEGORIES1+ T.getName() + ErrorConstants.ERROR_RETRIVING_TEMPLATE_MASTER_CATEGORIES2);
				
			}
		});
		
		
		
	}

}
