package lector.client.catalogo;


import java.util.ArrayList;

import lector.client.admin.tagstypes.ClickHandlerMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.Tree.Node;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.controler.Constants;
import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class Finder extends Composite {

	protected Node ActualRama;
	protected Long NanotimeOld;
	protected Catalog C;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	
	//el finder del reading activity tiene lenguaje asociado
	protected boolean InReadingActivity=false;
	protected SimplePanel simplePanel;
	protected Node trtmNewItem;
	protected ScrollPanel scrollPanel;
	protected ClickHandler clickHandler;
	protected BotonesStackPanelMio buttonMio;
	private Tree ArbolDeNavegacion;
	
	public Finder() {
		
		NanotimeOld=0l;
		simplePanel = new SimplePanel();
		initWidget(simplePanel);
		
//		horizontalSplitPanel = new SplitLayoutPanel();
//		simplePanel.add(horizontalSplitPanel);
		simplePanel.setSize("100%", "100%");
	//	simplePanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
//		horizontalSplitPanel.setSize("100%", "100%");
	//	horizontalSplitPanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
		scrollPanel = new ScrollPanel();
//		horizontalSplitPanel.addWest(scrollPanel, 200.0);
//		
//		
		simplePanel.add(scrollPanel);
		scrollPanel.setSize("100%", "100%");
		
		
		
		ArbolDeNavegacion = new Tree();
		ArbolDeNavegacion.addSelectionHandler(new SelectionHandler<TreeItem>() {
			public void onSelection(SelectionEvent<TreeItem> event) {
				Node ActualRamaNew=(Node)event.getSelectedItem();
				Long acLong=System.currentTimeMillis();
				Long Dist=acLong-NanotimeOld;
				Boolean Seleccionador=(Dist<1000);
				if (((Node)event.getSelectedItem()).getEntidad() instanceof Folder){
					if (ActualRama!=ActualRamaNew){
						ActualRama=ActualRamaNew;
						NanotimeOld=System.currentTimeMillis();
						cargaLaRama();
					}
					else
					{
						if (Seleccionador)
							if (ActualRamaNew.getEntidad().getID()!=Constants.CATALOGID)
								if (buttonMio!=null)Selecciona();
							else {}
						else NanotimeOld=System.currentTimeMillis();
					}
				}
				else if (ActualRamaNew.getEntidad() instanceof File)
					if (ActualRama!=ActualRamaNew){
						NanotimeOld=System.currentTimeMillis();
						ActualRama=ActualRamaNew;
					}
					else
					{
						if (Seleccionador)
							if (ActualRamaNew.getEntidad().getID()!=Constants.CATALOGID)
								Selecciona();
							else {}
						else NanotimeOld=System.currentTimeMillis();
					}
				
			}

			private void Selecciona() {
				BotonesStackPanelMio B=buttonMio.Clone();
				B.addClickHandler(clickHandler);
				B.setStyleName("gwt-ButtonCenter");
				B.setSize("100%", "100%");
				if (ActualRama.getEntidad() instanceof File) B.setIcon("File.gif",ActualRama.getEntidad().getName());
				else if (ActualRama.getEntidad() instanceof Folder)B.setIcon("Folder.gif",ActualRama.getEntidad().getName());
				B.addClickHandler(new ClickHandler() {
					
					public void onClick(ClickEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
						
					}
				});

				B.addMouseDownHandler(new MouseDownHandler() {
					public void onMouseDown(MouseDownEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
					}
				});
				
				B.addMouseOutHandler(new MouseOutHandler() {
					public void onMouseOut(MouseOutEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				}
				});
				
				B.addMouseOverHandler(new MouseOverHandler() {
					public void onMouseOver(MouseOverEvent event) {
						
						((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
					
				}
			});
				B.setEntidad(ActualRama.getEntidad());
				((ClickHandlerMio)clickHandler).onClickMan(B);

				
			}
		});

		ArbolDeNavegacion.addOpenHandler(new OpenHandler<TreeItem>() {
			public void onOpen(OpenEvent<TreeItem> event) {
				cargaLaRama();
			}
		});

		scrollPanel.setWidget(ArbolDeNavegacion);
		ArbolDeNavegacion.setSize("100%", "100%");
		
		trtmNewItem = new Node(new Folder("Cataolo", Constants.CATALOGID, Constants.CATALOGID));
		trtmNewItem.setText("//");
		ArbolDeNavegacion.addItem(trtmNewItem);
		ActualRama=trtmNewItem;
		
		
		
	}



	protected void cargaLaRama() {
		AsyncCallback<ArrayList<Entity>> callback1 = new AsyncCallback<ArrayList<Entity>>() {

			public void onFailure(Throwable caught) {
				if (InReadingActivity)  Window.alert(ActualUser.getLanguage().getE_Types_refresh());
				else Window.alert("Error : I can't refresh the types");
				LoadingPanel.getInstance().hide();
			}

			public void onSuccess(ArrayList<Entity> result) {
				
				sortStringExchange(result);
				
				ActualRama.removeItems();
				for (Entity entity : result) {
					entity.setActualFather(ActualRama.getEntidad());
				}
				for (Entity entitynew : result) {
					Node A=new Node(entitynew);
					if (entitynew instanceof Folder) A.setHTML("Folder.gif",entitynew.getName());					
					else A.setHTML("File.gif",entitynew.getName());
					ActualRama.addItem(A);
					ActualRama.setState(true,false);
					}
				LoadingPanel.getInstance().hide();
			}
			
			  public void sortStringExchange( ArrayList<Entity>  x )
		      {
		            int i, j;
		            Entity temp;

		            for ( i = 0;  i < x.size() - 1;  i++ )
		            {
		                for ( j = i + 1;  j < x.size();  j++ )
		                {  
		                         if ( x.get(i).getName().compareToIgnoreCase( x.get(j).getName()) > 0 )
		                          {                                             // ascending sort
		                                      temp = x.get(i);
		                                      x.set(i, x.get(j));    // swapping
		                                      x.set(j, temp); 
		                                      
		                           } 
		                   } 
		             } 
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
		trtmNewItem.setText(C.getCatalogName());
		cargaLaRama();
	}
	
	public void setButtonTipo(BotonesStackPanelMio buttonMio) {
		this.buttonMio=buttonMio;

	}

	public void setBotonClick(ClickHandler clickHandler) {
		this.clickHandler=clickHandler;
	}

	public Entity getTopPath() {
				return ActualRama.getEntidad();
	}
	
	public void RefrescaLosDatos()
	{
		ActualRama=trtmNewItem;
		cargaLaRama();
		//simplePanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
		//horizontalSplitPanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
	}


}
