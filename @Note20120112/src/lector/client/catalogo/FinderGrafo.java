package lector.client.catalogo;


import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.Tree.Node;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.Folder;
import lector.client.catalogo.grafo.PanelGrafo;
import lector.client.catalogo.server.Catalogo;
import lector.client.controler.Constants;
import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class FinderGrafo extends Finder {


	
	//el finder del reading activity tiene lenguaje asociado
	private PanelGrafo panelDelGrafo;
	
	public FinderGrafo(Catalog C) {
		
		this.C=C;
		panelSeleccion.clear();
		ScrollPanel SP=new ScrollPanel();
		SP.setSize("100%", "100%");
		SP.setStyleName("Dialog");
		panelDelGrafo = new PanelGrafo(C.getId());
		panelSeleccion.setWidget(SP);
		SP.setWidget(panelDelGrafo);
		
		
		
	}

	protected void SeleccionaLaRama() {
		
		panelDelGrafo.refresca(C.getId());

		LoadingPanel.getInstance().hide();
		
	}

	protected void cargaLaRama() {
		AsyncCallback<ArrayList<Entity>> callback1 = new AsyncCallback<ArrayList<Entity>>() {

			public void onFailure(Throwable caught) {
				if (InReadingActivity)  Window.alert(ActualUser.getLanguage().getE_Types_refresh());
				else Window.alert("Error : I can't refresh the types");
				LoadingPanel.getInstance().hide();
			}

			public void onSuccess(ArrayList<Entity> result) {
				
				ActualRama.removeItems();
				for (Entity entity : result) {
					entity.setActualFather(ActualRama.getEntidad());
				}
				for (Entity entitynew : result) {
					Node A=new Node(entitynew);
					if (entitynew instanceof Folder) A.setHTML("Folder.gif",entitynew.getName());					
					else A.setHTML("File.gif",entitynew.getName());
					ActualRama.addItem(A);
					}
				LoadingPanel.getInstance().hide();
			}
		};
		LoadingPanel.getInstance().center();
		if (InReadingActivity)  LoadingPanel.getInstance().setLabelTexto(ActualUser.getLanguage().getLoading());
		else LoadingPanel.getInstance().setLabelTexto("Loading...");
		Long IdPathActual = 0l;
/*		if (ActualRama.getEntidad().getID())
			IdPathActual = null;
		else*/
			IdPathActual = ActualRama.getEntidad().getID();
		bookReaderServiceHolder.getSons(IdPathActual, C
				.getId(), callback1);
		
	}

	
	@Override
	protected void cargaLaRamaYLaSeleccion() {
		cargaLaRama();
	}
	protected void cargaLaRamaYLaSeleccionGrafo() {
		AsyncCallback<ArrayList<Entity>> callback1 = new AsyncCallback<ArrayList<Entity>>() {

			public void onFailure(Throwable caught) {
				if (InReadingActivity)  Window.alert(ActualUser.getLanguage().getE_Types_refresh());
				else Window.alert("Error : I can't refresh the types");
				LoadingPanel.getInstance().hide();
			}

			public void onSuccess(ArrayList<Entity> result) {
				
				ActualRama.removeItems();
				for (Entity entity : result) {
					entity.setActualFather(ActualRama.getEntidad());
				}
				for (Entity entitynew : result) {
					Node A=new Node(entitynew);
					if (entitynew instanceof Folder) A.setHTML("Folder.gif",entitynew.getName());					
					else A.setHTML("File.gif",entitynew.getName());
					ActualRama.addItem(A);
					}
				SeleccionaLaRama();
				LoadingPanel.getInstance().hide();
			}
		};
		LoadingPanel.getInstance().center();
		if (InReadingActivity)  LoadingPanel.getInstance().setLabelTexto(ActualUser.getLanguage().getLoading());
		else LoadingPanel.getInstance().setLabelTexto("Loading...");
		Long IdPathActual = 0l;
/*		if (ActualRama.getEntidad()==null)
			IdPathActual = null;
		else*/
			IdPathActual = ActualRama.getEntidad().getID();
		bookReaderServiceHolder.getSons(IdPathActual, C
				.getId(), callback1);
		
	}
	
	public boolean isInReadingActivity() {
		return InReadingActivity;
	}
	
	public void setInReadingActivity(boolean inReadingActivity) {
		InReadingActivity = inReadingActivity;
	}
	
	public Catalog getCatalogo() {
		return C;
	}
	
	public void setCatalogo(Catalog c) {
		C = c;
		ActualRama=trtmNewItem;
		cargaLaRamaYLaSeleccion();
	}
	
	public static void setButtonTipoGrafo(BotonesStackPanelMio buttonMio) {
		PanelGrafo.setBotonTipo(buttonMio);

	}

	public static void setBotonClickGrafo(ClickHandler clickHandler) {
		PanelGrafo.setAccionAsociada(clickHandler);

	}

	public Entity getTopPath() {
				return ActualRama.getEntidad();
	}
	



	public void RefrescaLosDatos()
	{
		cargaLaRamaYLaSeleccionGrafo();
		//simplePanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
		//horizontalSplitPanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
	}

}
