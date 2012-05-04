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
		simplePanel.clear();
		ScrollPanel SP=new ScrollPanel();
		SP.setSize("100%", "100%");
		panelDelGrafo = new PanelGrafo(C.getId());
		simplePanel.setWidget(SP);
		SP.setWidget(panelDelGrafo);
		
		
		
	}

	protected void SeleccionaLaRama() {
		
		panelDelGrafo.refresca(C.getId());

		LoadingPanel.getInstance().hide();
		
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
		cargaLaRama();
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
	
@Override
public void RefrescaLosDatos() {
	SeleccionaLaRama();
}



}
