package lector.client.admin.export.admin;

import java.util.ArrayList;
import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import lector.client.admin.export.template.TemplateCategory;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.controler.ErrorConstants;
import lector.client.reader.LoadingPanel;

public class ArbitroLlamadasTemplates {

	private Stack<ElementoLlamada> Pila =new Stack<ElementoLlamada>();
	private boolean finales=true;
	private static ArbitroLlamadasTemplates YO;
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	
	public ArbitroLlamadasTemplates() {
		Pila =new Stack<ElementoLlamada>();
		finales=true;
	}
	
	public static ArbitroLlamadasTemplates getInstance(){
		if (YO==null) YO=new ArbitroLlamadasTemplates();
		return YO;
	}
	
	public void addElement(ElementoLlamada EL){
		Pila.add(EL);
		if (finales)
			{
			llamadacontinua();
			
			}
	}

	private void llamadacontinua() {

		finales=false;
		ElementoLlamada EL=Pila.peek();
		TemplateCategory TC=EL.getT();
		if (TC.getSubCategories()!=null&&!TC.getSubCategories().isEmpty()){
			LoadingPanel.getInstance().center();
			LoadingPanel.getInstance().setLabelTexto("Loading...");
			exportServiceHolder.getTemplateCategoriesByIds(TC.getSubCategories(), new AsyncCallback<ArrayList<TemplateCategory>>() {
				
				public void onSuccess(ArrayList<TemplateCategory> result) {
					LoadingPanel.getInstance().hide();
					procesallamada(result,Pila.pop());
				}
				
				public void onFailure(Throwable caught) {
					LoadingPanel.getInstance().hide();
					Window.alert(ErrorConstants.ERROR_RETRIVING_TEMPLATE_THREAD_REFRESH);
					Pila=new Stack<ElementoLlamada>();
					finales=true;
					
				}
			});
		}
		
	}

	protected void procesallamada(ArrayList<TemplateCategory> result, ElementoLlamada elementoLlamada) {
		// TODO Auto-generated method stub
		
	}
	
}
