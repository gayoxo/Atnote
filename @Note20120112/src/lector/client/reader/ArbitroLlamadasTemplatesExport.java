package lector.client.reader;

import java.util.ArrayList;
import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.controler.ErrorConstants;
import lector.client.reader.LoadingPanel;
import lector.share.model.TemplateCategory;

public class ArbitroLlamadasTemplatesExport {

	private ArrayList<ElementoExportacionTemplate> Pila =new ArrayList<ElementoExportacionTemplate>();
	private boolean finales=true;
	private static ArbitroLlamadasTemplatesExport YO;
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	
	public ArbitroLlamadasTemplatesExport() {
		Pila =new ArrayList<ElementoExportacionTemplate>();
		finales=true;
	}
	
	
	public void clear() {
		Pila =new ArrayList<ElementoExportacionTemplate>();
		finales=true;

	}
	public static ArbitroLlamadasTemplatesExport getInstance(){
		if (YO==null) YO=new ArbitroLlamadasTemplatesExport();
		return YO;
	}
	
	public void addElement(ElementoExportacionTemplate EL){
		Pila.add(EL);
		if (finales)
			{
			llamadacontinua();
			
			}
	}

	private void llamadacontinua() {

		finales=false;
		ElementoExportacionTemplate EL=Pila.get(0);
		TemplateCategory TC=EL.getTemplate();
		if (TC.getSubCategories()!=null&&!TC.getSubCategories().isEmpty()){
			LoadingPanel.getInstance().center();
			LoadingPanel.getInstance().setLabelTexto("Loading...");
			exportServiceHolder.getTemplateCategoriesByIds(TC.getSubCategories(), new AsyncCallback<ArrayList<TemplateCategory>>() {
				
				public void onSuccess(ArrayList<TemplateCategory> result) {
					LoadingPanel.getInstance().hide();
					ElementoExportacionTemplate EL=Pila.get(0);
					Pila.remove(EL);
					procesallamada(result,EL);
					if (!Pila.isEmpty())
						llamadacontinua();
					else finales=true;
				}
				
				public void onFailure(Throwable caught) {
					LoadingPanel.getInstance().hide();
					Window.alert(ErrorConstants.ERROR_RETRIVING_TEMPLATE_THREAD_REFRESH);
					Pila=new ArrayList<ElementoExportacionTemplate>();
					finales=true;
					
				}
			});
		}
		else {
			ElementoExportacionTemplate ELL=Pila.get(0);
			Pila.remove(ELL);
			if (!Pila.isEmpty())
				llamadacontinua();
			else finales=true;
		}
		
	}

	protected void procesallamada(ArrayList<TemplateCategory> result, ElementoExportacionTemplate elementoLlamada) {
		ElementoExportacionTemplate Padre=elementoLlamada;
		for (TemplateCategory templateCategory : result) {
			ElementoExportacionTemplate Nuevo=new ElementoExportacionTemplate(templateCategory, Padre.getProfundidad()+1,Padre.isEditable());
			Padre.addSon(Nuevo);
			Pila.add(Nuevo);
		}
		
		
		
	}
	
}
