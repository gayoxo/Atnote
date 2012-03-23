package lector.client.catalogo;


import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.Tree.Node;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.Folder;
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

public class CopyOfFinder extends Composite {

	protected Node ActualRama;
	protected Catalog C;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	
	//el finder del reading activity tiene lenguaje asociado
	protected boolean InReadingActivity=false;
	private StackPanelMio SPmio;
	protected SplitLayoutPanel horizontalSplitPanel;
	protected SimplePanel simplePanel;
	protected Node trtmNewItem;
	protected ScrollPanel scrollPanel;
	protected SimplePanel panelSeleccion;
	
	public CopyOfFinder() {
		
		simplePanel = new SimplePanel();
		initWidget(simplePanel);
		
		horizontalSplitPanel = new SplitLayoutPanel();
		simplePanel.add(horizontalSplitPanel);
		simplePanel.setSize("100%", "100%");
	//	simplePanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
		horizontalSplitPanel.setSize("100%", "100%");
	//	horizontalSplitPanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
		scrollPanel = new ScrollPanel();
		horizontalSplitPanel.addWest(scrollPanel, 200.0);
		scrollPanel.setSize("100%", "100%");
		
		
		
		Tree ArbolDeNavegacion = new Tree();
		ArbolDeNavegacion.addSelectionHandler(new SelectionHandler<TreeItem>() {
			public void onSelection(SelectionEvent<TreeItem> event) {
				ActualRama=(Node)event.getSelectedItem();
				cargaLaRama();
				
			}
		});
		ArbolDeNavegacion.addOpenHandler(new OpenHandler<TreeItem>() {
			public void onOpen(OpenEvent<TreeItem> event) {
				cargaLaRamaYLaSeleccion();
			}
		});
		scrollPanel.setWidget(ArbolDeNavegacion);
		ArbolDeNavegacion.setSize("100%", "100%");
		
		trtmNewItem = new Node(new Folder("Catalogo", Constants.CATALOGID, Constants.CATALOGID));
		trtmNewItem.setText("\\");
		ArbolDeNavegacion.addItem(trtmNewItem);
		trtmNewItem.setState(true);
		ActualRama=trtmNewItem;
		
		
		panelSeleccion = new SimplePanel();
		horizontalSplitPanel.add(panelSeleccion);
		panelSeleccion.setSize("100%", "100%");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		panelSeleccion.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		SPmio = new StackPanelMio();
		verticalPanel.add(SPmio);
		SPmio.setWidth("100%");
		
		
		
	}

	protected void SeleccionaLaRama() {
		
		ArrayList<Entity>AMostrar = new ArrayList<Entity>();
		for (int i = 0; i < ActualRama.getChildCount(); i++) {
			AMostrar.add(((Node)ActualRama.getChild(i)).getEntidad()); 
		}

		SPmio.Clear();
		for (Entity entitynew : AMostrar) {
			if (AMostrar.size() <= 10)
				SPmio.addBotonLessTen(entitynew);
			else
				SPmio.addBoton(entitynew);
		}
		SPmio.ClearEmpty();
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

	protected void cargaLaRamaYLaSeleccion() {
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
	
	public void setButtonTipo(BotonesStackPanelMio buttonMio) {
		SPmio.setBotonTipo(buttonMio);

	}

	public void setBotonClick(ClickHandler clickHandler) {
		SPmio.setBotonClick(clickHandler);

	}

	public Entity getTopPath() {
				return ActualRama.getEntidad();
	}
	
	public void RefrescaLosDatos()
	{
		cargaLaRamaYLaSeleccion();
		//simplePanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
		//horizontalSplitPanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
	}


}
