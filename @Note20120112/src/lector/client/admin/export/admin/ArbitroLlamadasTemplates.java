package lector.client.admin.export.admin;

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

public class ArbitroLlamadasTemplates {

	private ArrayList<RepresentacionTemplateCategory> Pila =new ArrayList<RepresentacionTemplateCategory>();
	private boolean finales=true;
	private static ArbitroLlamadasTemplates YO;
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	
	public ArbitroLlamadasTemplates() {
		Pila =new ArrayList<RepresentacionTemplateCategory>();
		finales=true;
	}
	
	
	public void clear() {
		Pila =new ArrayList<RepresentacionTemplateCategory>();
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
		RepresentacionTemplateCategory EL=Pila.get(0);
		TemplateCategory TC=EL.getT();
		if (TC.getSubCategories()!=null&&!TC.getSubCategories().isEmpty()){
			LoadingPanel.getInstance().center();
			LoadingPanel.getInstance().setLabelTexto("Loading...");
			exportServiceHolder.getTemplateCategoriesByIds(TC.getSubCategories(), new AsyncCallback<ArrayList<TemplateCategory>>() {
				
				public void onSuccess(ArrayList<TemplateCategory> result) {
					LoadingPanel.getInstance().hide();
					RepresentacionTemplateCategory EL=Pila.get(0);
					Pila.remove(EL);
					procesallamada(result,EL);
					if (!Pila.isEmpty())
						llamadacontinua();
					else finales=true;
				}
				
				public void onFailure(Throwable caught) {
					LoadingPanel.getInstance().hide();
					Window.alert(ErrorConstants.ERROR_RETRIVING_TEMPLATE_THREAD_REFRESH);
					Pila=new ArrayList<RepresentacionTemplateCategory>();
					finales=true;
					
				}
			});
		}
		else {
			RepresentacionTemplateCategory ELL=Pila.get(0);
			Pila.remove(ELL);
			if (!Pila.isEmpty())
				llamadacontinua();
			else finales=true;
		}
		
	}

	protected void procesallamada(ArrayList<TemplateCategory> result, RepresentacionTemplateCategory elementoLlamada) {
		RepresentacionTemplateCategory Padre=elementoLlamada;
		for (TemplateCategory templateCategory : result) {
			RepresentacionTemplateCategory Nuevo=new RepresentacionTemplateCategory(templateCategory, Padre,Padre.getProfundidad());
			Padre.addSon(Nuevo);
			Nuevo.setclickHandel(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					ButtonTemplateRep Mas=(ButtonTemplateRep)event.getSource();
					PanelGestionTemplate.setActual(Mas.getTRep());
					
				}
			});
			Pila.add(Nuevo);
		}
		
		
		
	}
	
}
