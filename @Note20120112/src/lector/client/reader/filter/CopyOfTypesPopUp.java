package lector.client.reader.filter;

//import java.util.ArrayList;
//
//
//import lector.client.catalogo.Finder;
//import lector.client.catalogo.client.Entity;
//import lector.client.catalogo.client.Folder;
//import lector.client.controler.Constants;
//import lector.client.language.Language;
//import lector.client.login.ActualUser;
//import lector.client.reader.MainEntryPoint;
//
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.HasHorizontalAlignment;
//import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
//import com.google.gwt.user.client.ui.VerticalPanel;
//import com.google.gwt.user.client.ui.SimplePanel;
//import com.google.gwt.user.client.ui.ScrollPanel;
//import com.google.gwt.user.client.ui.HorizontalPanel;
//import com.google.gwt.user.client.ui.Widget;

public class CopyOfTypesPopUp extends PopupPanel {

//	private ScrollPanel FinderPanel;
//	private VerticalPanel AddedPanel;
//	private Finder finder;
//	private HorizontalPanel horizontalPanel;
//	private Button All; 
//	private enum Seleccion {All, None, Selected};
//	private Seleccion seleccion;
//	private Button None;
//	private PopupPanel Me=this;
//	private Language Lang;
	
	public CopyOfTypesPopUp() {
		
	
//		super(true);
//		seleccion=Seleccion.Selected;
//		VerticalPanel verticalPanel = new VerticalPanel();
//		setWidget(verticalPanel);
//		verticalPanel.setSize("100%", "100%");
//		
//		SimplePanel simplePanel = new SimplePanel();
//		verticalPanel.add(simplePanel);
//		simplePanel.setSize("100%", "100%");
//		
//		FinderPanel = new ScrollPanel();
//		simplePanel.setWidget(FinderPanel);
//		FinderPanel.setSize("100%", "248px");
//		
//		SimplePanel simplePanel_1 = new SimplePanel();
//		verticalPanel.add(simplePanel_1);
//		simplePanel_1.setSize("100%", "100%");
//		
//		AddedPanel = new VerticalPanel();
//		simplePanel_1.setWidget(AddedPanel);
//		AddedPanel.setSize("100%", "100%");
//		Lang=ActualUser.getLanguage();
//		
//		finder= new Finder();
//		finder.setInReadingActivity(true);
//		finder.setButtonTipo(new BotonesStackPanelfilterMio("prototipo", new VerticalPanel(), AddedPanel));
//		finder.setCatalogo(ActualUser.getCatalogo());
//		finder.RefrescaLosDatos();
//		
//        finder.setBotonClick(new ClickHandler() {
//
//	        public void onClick(ClickEvent event) {
//	        	BotonesStackPanelfilterMio BS=((BotonesStackPanelfilterMio) event.getSource());
//	        Entity Act=finder.getSonbyName(BS.getHTML());
//	        
//	        if (Act instanceof Folder)
//	        {
//	        	finder.add2Pad(Act);
//	        	finder.RefrescaLosDatos();
//	        }
//	        else {
//	        	if (seleccion!=Seleccion.Selected)
//	        		{
//	        		AddedPanel.clear();
//	        		seleccion=Seleccion.Selected;
//	        		}
//	        	BS.Swap();
//	        }
//	        }
//	        });
//        
//        
//	FinderPanel.setWidget(finder);
//	finder.setSize("100%", "100%");
//	
//	horizontalPanel = new HorizontalPanel();
//	verticalPanel.add(horizontalPanel);
//	
//	Button btnNewButton = new Button(Lang.getFilterButton());
//	horizontalPanel.add(btnNewButton);
//	horizontalPanel.setCellHorizontalAlignment(btnNewButton, HasHorizontalAlignment.ALIGN_CENTER);
//	
//	All = new Button(Lang.getSelect_All());
//	All.addClickHandler(new ClickHandler() {
//		public void onClick(ClickEvent event) {
//			AddedPanel.clear();
//			seleccion=Seleccion.All;	
//			AddedPanel.add(new Label(Lang.getSelect_All()));
//		}
//	});
//	horizontalPanel.add(All);
//	
//	None = new Button(Lang.getSelect_None());
//	None.addClickHandler(new ClickHandler() {
//		public void onClick(ClickEvent event) {
//			AddedPanel.clear();
//		}
//	});
//		horizontalPanel.add(None);
//
//		btnNewButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				ArrayList<Long> filtro = new ArrayList<Long>();
//				if (AddedPanel.getWidgetCount() > 0) {
//					Widget W = AddedPanel.getWidget(0);					
//					if (W instanceof BotonesStackPanelfilterMio) {
//
//						// StringBuffer Alerta =new StringBuffer();
//						for (int Padreit = 0; Padreit < AddedPanel
//								.getWidgetCount(); Padreit++) {
//							BotonesStackPanelfilterMio TPadre = (BotonesStackPanelfilterMio) AddedPanel
//									.getWidget(Padreit);
//							Entity Padre = TPadre.getEntidad();
//							filtro.add(Padre.getID());
//						}
//					}
//					else filtro.add(Constants.ALL);
//				}
//				MainEntryPoint.setFiltroTypes(filtro);
//				Me.hide();
//			}
//		});
//	
	}

}
