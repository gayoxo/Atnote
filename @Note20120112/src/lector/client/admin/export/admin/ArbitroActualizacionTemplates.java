package lector.client.admin.export.admin;

import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import lector.client.admin.export.template.TemplateCategory;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.reader.LoadingPanel;

public class ArbitroActualizacionTemplates {

	private Stack<TemplateCategory> RTCS;
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	private RepresentacionTemplateCategory Updateable;
	
	public ArbitroActualizacionTemplates(Stack<RepresentacionTemplateCategory> RTC,RepresentacionTemplateCategory updateable) {
		Updateable=updateable;
		RTCS=new Stack<TemplateCategory>();
		for (RepresentacionTemplateCategory representacionTemplateCategory : RTC) {
			RTCS.add(representacionTemplateCategory.getT());
		}
		
	}

	private void LLamadaEnCadena() {
		if (!RTCS.isEmpty())
		exportServiceHolder.saveTemplateCategory(RTCS.pop() , new AsyncCallback<Void>() {
			
			public void onSuccess(Void result) {
				
				LLamadaEnCadena();
				
			}
			
			public void onFailure(Throwable caught) {
				LoadingPanel.getInstance().hide();
				Window.alert(ErrorConstants.ERROR_UPDATING_TEMPLATE_CATEGORY);
				
			}
		});
		else {
			LoadingPanel.getInstance().hide();
			EditTemplate.getPGT().refresh();
			}
		
	}
	
	
	public void Delete()
	{
		if (Updateable!=null)
		{
			LoadingPanel.getInstance().center();
			LoadingPanel.getInstance().setLabelTexto(InformationConstants.SAVING);
			exportServiceHolder.deleteTemplateCategory(Updateable.getT().getId(), new AsyncCallback<Void>() {

				public void onFailure(Throwable caught) {
					LoadingPanel.getInstance().hide();
					Window.alert(ErrorConstants.ERROR_DELETING_TEMPLATE_CATEGORY);
					
				}

				public void onSuccess(Void result) {
					LLamadaEnCadena();
					
				}
			});
		}else {
			LLamadaEnCadena();
		}
	}
	
	
	public void Update()
	{
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(InformationConstants.SAVING);
		LLamadaEnCadena();
	}
}
