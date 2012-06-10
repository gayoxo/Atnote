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

public class FinderKeys extends Finder {

	
	protected ElementKey ActualEle;
	protected ArrayList<EstadoElementKey> Lista;
	protected Catalog C;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	
	//el finder del reading activity tiene lenguaje asociado
	protected boolean InReadingActivity=false;
	protected ScrollPanel scrollPanel;
	protected ClickHandler clickHandler;
	protected BotonesStackPanelMio buttonMio;
	private ElementKey EK;
	private ClickHandler CHM;
	private ClickHandler CHS;
	
	public FinderKeys() {
		
		simplePanel.clear();
		simplePanel.setSize("100%", "100%");

		scrollPanel = new ScrollPanel();

		simplePanel.add(scrollPanel);
		scrollPanel.setSize("100%", "100%");
		
		scrollPanel.add(EK);
		EK=new ElementKey(new Folder("Cataolo", Constants.CATALOGID, Constants.CATALOGID));
		Lista=new ArrayList<EstadoElementKey>();
		
		AddElementLista(new EstadoElementKey(EK,false));
		CHM=new ClickHandler() {
			
			public void onClick(ClickEvent event) {
			
				ButtonKey B0=(ButtonKey)event.getSource();
				B0.getPadreKey();
				FinderKeysArbitro.getInstance().addfather(B0.getPadreKey());
				cargaLaRama(B0.getPadreKey());
				
			}
		};
		
		CHS=new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				ButtonKey Bk=(ButtonKey)event.getSource();
				ElementKey ActualRamaNew = Bk.getPadreKey();
				
				Long acLong=System.currentTimeMillis();
				Long Dist=acLong-NanotimeOld;
				Boolean Seleccionador=(Dist<1000);
				
					if (ActualEle!=ActualRamaNew){
						NanotimeOld=System.currentTimeMillis();
						ActualEle.getLabel().setStyleName("gwt-ButtonIzquierdaMIN");
						ActualEle.setSelected(false);
						ActualEle=ActualRamaNew;
						ActualEle.getLabel().setStyleName("gwt-ButtonIzquierdaSelectMIN");
						ActualEle.setSelected(true);
					}
					else
					{
						if (Seleccionador)
							if (ActualRamaNew.getEntidad().getID()!=Constants.CATALOGID)
								{
								Selecciona();
								ActualEle.getLabel().setStyleName("gwt-ButtonIzquierdaMIN");
								ActualEle.setSelected(false);
								}
						
							else {}
						else NanotimeOld=System.currentTimeMillis();
					}
					
									
			}
			
			private void Selecciona() {
				BotonesStackPanelMio B=buttonMio.Clone();
				B.addClickHandler(clickHandler);
				B.setStyleName("gwt-ButtonCenter");
				B.setSize("100%", "100%");
				if (ActualEle.getEntidad() instanceof File) B.setIcon("File.gif",ActualEle.getEntidad().getName());
				else if (ActualEle.getEntidad() instanceof Folder)B.setIcon("Folder.gif",ActualEle.getEntidad().getName());
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
				B.setEntidad(ActualEle.getEntidad());
				((ClickHandlerMio)clickHandler).onClickMan(B);

				
			}
		};

		
		EK.addClickButtonMas(CHM);
		
		EK.addClickButton(CHS);
		ActualEle=EK;
		scrollPanel.add(EK);

		

			
		
			
		
		
	}



	private void AddElementLista(EstadoElementKey estadoElementKey) {
		
		ElementKey EK=IsIn(estadoElementKey.getEK());
		if (EK==null)
		{
			Lista.add(estadoElementKey);
		}
		else 
		{
			ElementKey eKNuevo=estadoElementKey.getEK();
			EK.getOthers().setVisible(true);
			eKNuevo.getOthers().setVisible(true);
			EK.getOtros().add(eKNuevo);
			ArrayList<ElementKey> ListaEK=EK.getOtros();
			for (ElementKey elementKey : ListaEK) {
				elementKey.setOtros(ListaEK);
				elementKey.getOthers().setVisible(true);
			}

		}
		
	}



	private ElementKey IsIn(ElementKey ek2) {
		for (EstadoElementKey Element : Lista) {
			if (Element.getEK().getEntidad().getID().equals(ek2.getEntidad().getID()))
				return Element.getEK();
		}
		return null;
	}



	protected void cargaLaRama(ElementKey elementKeyllamada) {
		AsyncCallback<ArrayList<Entity>> callback1 = new AsyncCallback<ArrayList<Entity>>() {

			public void onFailure(Throwable caught) {
				if (InReadingActivity)  Window.alert(ActualUser.getLanguage().getE_Types_refresh());
				else Window.alert("Error : I can't refresh the types");
				LoadingPanel.getInstance().hide();
			}

			public void onSuccess(ArrayList<Entity> result) {
				
				
				sortStringExchange(result);
				ArrayList<ElementKey> ListaTemp=new ArrayList<ElementKey>();
				
				ElementKey PadreEle = FinderKeysArbitro.getInstance().getPadre();
				PadreEle.removeItems();
				for (Entity entity : result) {
					entity.setActualFather(PadreEle.getEntidad());
				}
				for (Entity entitynew : result) {
					ElementKey A=new ElementKey(entitynew);
					if (entitynew instanceof Folder) A.setHTML("Folder.gif",entitynew.getName());					
					else {
						A.setHTML("File.gif",entitynew.getName());
						A.isAFile();
					}
					PadreEle.addItem(A);
					A.addClickButtonMas(CHM);					
					A.addClickButton(CHS);
					ListaTemp.add(A);
					AddElementLista(new EstadoElementKey(A,false));
					}
				//TODO
				FinderKeysArbitro.getInstance().setfalse();
				for (ElementKey elementKey : ListaTemp) {
					FinderKeysArbitro.getInstance().add(elementKey);
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
			IdPathActual = elementKeyllamada.getEntidad().getID();
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
		ActualEle=EK;
		EK.setText(C.getCatalogName());
		ActualEle.setSelected(true);
		ActualEle.getLabel().setStyleName("gwt-ButtonIzquierdaSelectMIN");
		ActualEle.setSelected(true);
//		cargaLaRama();
	}
	
	public void setButtonTipo(BotonesStackPanelMio buttonMio) {
		this.buttonMio=buttonMio;

	}

	public void setBotonClick(ClickHandler clickHandler) {
		this.clickHandler=clickHandler;
	}

	public Entity getTopPath() {
				return ActualEle.getEntidad();
	}
	
	public void RefrescaLosDatos()
	{
		Lista.clear();
		ActualEle=EK;
		ActualEle.getLabel().setStyleName("gwt-ButtonIzquierdaSelectMIN");
		ActualEle.setSelected(true);
		FinderKeysArbitro.getInstance().refresh();
		FinderKeysArbitro.getInstance().addfather(EK);
		cargaLaRama(EK);
		//simplePanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
		//horizontalSplitPanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
	}


}
