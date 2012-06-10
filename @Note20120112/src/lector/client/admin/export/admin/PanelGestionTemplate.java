package lector.client.admin.export.admin;

import java.util.ArrayList;

import lector.client.admin.export.template.Template;
import lector.client.admin.export.template.TemplateCategory;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.controler.Constants;
import lector.client.controler.ErrorConstants;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PanelGestionTemplate extends Composite {

	public Template T;
	private VerticalPanel PanelTemplate;
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	private static TemplateCategory Actual;
	
	public PanelGestionTemplate(Template t) {
		
		T=t;
		Actual=new TemplateCategory(T.getName(),T.getCategories(),
				new ArrayList<Long>(), Constants.TEMPLATEID, T.getId());
		PanelTemplate = new VerticalPanel();
		initWidget(PanelTemplate);
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
		exportServiceHolder.getTemplateCategoriesByIds(T.getCategories(), new AsyncCallback<ArrayList<TemplateCategory>>() {
			
			public void onSuccess(ArrayList<TemplateCategory> result) {
				LoadingPanel.getInstance().hide();
				for (TemplateCategory templateCategory : result) {
					RepresentacionTemplateCategory Nuevo=new RepresentacionTemplateCategory(templateCategory, null);
					Nuevo.setclickHandel(new ClickHandler() {
						
						public void onClick(ClickEvent event) {
							ButtonTemplateRep Mas=(ButtonTemplateRep)event.getSource();
							Actual=Mas.getT();
							
						}
					});
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

	public TemplateCategory getActualNode() {
		return Actual;
	}
	
	public static void setActual(TemplateCategory actual) {
		Actual = actual;
	}

}
