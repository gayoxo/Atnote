package lector.client.admin.export.admin;

import java.util.ArrayList;
import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import lector.client.admin.export.template.TemplateCategory;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.controler.ErrorConstants;
import lector.client.reader.LoadingPanel;

public class ArbitroLlamadasTemplates {

	private Stack<RepresentacionTemplateCategory> Pila =new Stack<RepresentacionTemplateCategory>();
	private boolean finales=true;
	private static ArbitroLlamadasTemplates YO;
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	
	public ArbitroLlamadasTemplates() {
		Pila =new Stack<RepresentacionTemplateCategory>();
		finales=true;
	}
	
	
	public void clear() {
		Pila =new Stack<RepresentacionTemplateCategory>();
		finales=true;

	}
	public static ArbitroLlamadasTemplates getInstance(){
		if (YO==null) YO=new ArbitroLlamadasTemplates();
		return YO;
	}
	
	public void addElement(RepresentacionTemplateCategory EL){
		Pila.add(EL);
		if (finales)
			{
			llamadacontinua();
			
			}
	}

	private void llamadacontinua() {

		finales=false;
		RepresentacionTemplateCategory EL=Pila.peek();
		TemplateCategory TC=EL.getT();
		if (TC.getSubCategories()!=null&&!TC.getSubCategories().isEmpty()){
			LoadingPanel.getInstance().center();
			LoadingPanel.getInstance().setLabelTexto("Loading...");
			exportServiceHolder.getTemplateCategoriesByIds(TC.getSubCategories(), new AsyncCallback<ArrayList<TemplateCategory>>() {
				
				public void onSuccess(ArrayList<TemplateCategory> result) {
					LoadingPanel.getInstance().hide();
					procesallamada(result,Pila.pop());
					if (!Pila.isEmpty())
						llamadacontinua();
					else finales=true;
				}
				
				public void onFailure(Throwable caught) {
					LoadingPanel.getInstance().hide();
					Window.alert(ErrorConstants.ERROR_RETRIVING_TEMPLATE_THREAD_REFRESH);
					Pila=new Stack<RepresentacionTemplateCategory>();
					finales=true;
					
				}
			});
		}
		else {
			Pila.pop();
			if (!Pila.isEmpty())
				llamadacontinua();
			else finales=true;
		}
		
	}

	protected void procesallamada(ArrayList<TemplateCategory> result, RepresentacionTemplateCategory elementoLlamada) {
		RepresentacionTemplateCategory Padre=elementoLlamada;
		for (TemplateCategory templateCategory : result) {
			RepresentacionTemplateCategory Nuevo=new RepresentacionTemplateCategory(templateCategory, Padre);
			Padre.addSon(Nuevo);
			Nuevo.setclickHandel(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					ButtonTemplateRep Mas=(ButtonTemplateRep)event.getSource();
					PanelGestionTemplate.setActual(Mas.getTRep());
					
				}
			});
			Pila.addElement(Nuevo);
		}
		
		
		
	}
	
}
