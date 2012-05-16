package lector.client.browser;

import java.util.ArrayList;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.Finder;
import lector.client.catalogo.FinderGrafo;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.server.FileDB;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.language.Language;
import lector.client.login.ActualUser;
import lector.client.reader.Annotation;
import lector.client.reader.AnnotationNotFoundException;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;

public class Browser implements EntryPoint {

	private FinderGrafo FinderButton;
	private VerticalPanel Selected;
	private FinderGrafo FinderButton2;
	private static VerticalPanel SelectedB;
	private Language ActualLang;
	private static Button btnNewButton;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private static ArrayList<Long> filtroResidual;

	public Browser() {
		SelectedB = new VerticalPanel();
	}
	
	public void onModuleLoad() {
		
		ActualLang=ActualUser.getLanguage();
		
		RootPanel rootPanel = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
//		
		
//		RootPanel rootPanel = RootPanel.get();
		
		SplitLayoutPanel WindowPanel = new SplitLayoutPanel();
		rootPanel.add(WindowPanel,0, 25);
		WindowPanel.setSize("100%", "96%");
		
		SplitLayoutPanel BrowserSelectPanel = new SplitLayoutPanel();
		WindowPanel.add(BrowserSelectPanel);
		
//		SimplePanel simplePanel = new SimplePanel();
//		splitLayoutPanel_1.addNorth(simplePanel, 110.0);
		
		Selected = new VerticalPanel();
		BrowserSelectPanel.addSouth(Selected, 200.0);
		Selected.setWidth("100%");
		Selected.add(SelectedB);
		SelectedB.setWidth("100%");
		
		FinderGrafo.setButtonTipoGrafo(new BotonesStackPanelBrowser(
				"prototipo", new VerticalPanel(), SelectedB,FinderButton2));
		FinderGrafo.setBotonClickGrafo(new ClickHandler() {

			public void onClick(ClickEvent event) {
				
				BotonesStackPanelBrowser BS = ((BotonesStackPanelBrowser) event
						.getSource());
			
				if ((FinderButton2.getTopPath()==null)||(BS.getEntidad().getFathers().isEmpty())) 
					{
					BS.Swap();
					}
				else if (EqualsFinderButton(BS))
					{
					BS.Swap();
					}
				
				if (SelectedB.getWidgetCount()==0)btnNewButton.setVisible(false);
				else btnNewButton.setVisible(true);
				
			}

			private boolean EqualsFinderButton(BotonesStackPanelBrowser bS) {
				for (Entity entity : bS.getEntidad().getFathers()) {
					if (FinderButton2.getTopPath().getID().equals(entity.getID())) return true;
				}		
				return false;
			}
		});
		FinderButton2 = new FinderGrafo(ActualUser.getCatalogo());
		
		
				FinderButton2.setSize("100%", "100%");
		
		
		
		FinderButton = new FinderGrafo(ActualUser.getOpenCatalog());
		FinderGrafo.setButtonTipoGrafo(new BotonesStackPanelBrowser(
				"prototipo", new VerticalPanel(), SelectedB,FinderButton));
		
		btnNewButton = new Button(ActualLang.getFilterButtonBrowser());
		Selected.add(btnNewButton);
		btnNewButton.setStyleName("gwt-MenuItemMio");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				refresh(); 
			}
		});
		btnNewButton.setStyleName("gwt-ButtonBotton");
		btnNewButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
			}
		});
		btnNewButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
			}
		});
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
			}
		});
		if (SelectedB.getWidgetCount()==0) btnNewButton.setVisible(false);
		btnNewButton.setSize("100%", "100%");
		
		
		FinderGrafo.setBotonClickGrafo(new ClickHandler() {

			public void onClick(ClickEvent event) {
				BotonesStackPanelBrowser BS = ((BotonesStackPanelBrowser) event
						.getSource());
//			//TODO REvisar Despierto.
//				if ((FinderButton.getTopPath()==null)||(BS.getEntidad().getFathers().isEmpty())) 
//					{
//					BS.Swap();
//					}
//				else
				if (!EqualsFinderButton(BS))
					{
					BS.Swap();
					}
				
				if (SelectedB.getWidgetCount()==0)btnNewButton.setVisible(false);
				else btnNewButton.setVisible(true);
			}

			private boolean EqualsFinderButton(BotonesStackPanelBrowser bS) {
				for (int i=0; i<SelectedB.getWidgetCount();i++) {
					BotonesStackPanelBrowser Boton=(BotonesStackPanelBrowser)SelectedB.getWidget(i);
					if (Boton.getEntidad().getID().equals(bS.getEntidad().getID())) return true;
				}		
				return false;
			}
		});

		FinderButton.setSize("100%", "100%");
		
		ScrollPanel scrollPanel = new ScrollPanel();
		BrowserSelectPanel.add(scrollPanel);
		scrollPanel.setSize("", "");
		
		DecoratedTabPanel decoratedTabPanel = new DecoratedTabPanel();
		scrollPanel.setWidget(decoratedTabPanel);
		decoratedTabPanel.setSize("100%", "100%");
		SimplePanel CatalogoProf= new SimplePanel();
		decoratedTabPanel.add(CatalogoProf, ActualLang.getTeacherTypes(), false);
		CatalogoProf.setSize("100%", "98%");
		CatalogoProf.add(FinderButton2);
		
		decoratedTabPanel.selectTab(0);
		//FinderButton2.setCatalogo(ActualUser.getCatalogo());
		
		//FinderButton.setCatalogo(ActualUser.getOpenCatalog());
		
		SimplePanel CatalogoAbierto= new SimplePanel();
		decoratedTabPanel.add(CatalogoAbierto, ActualLang.getOpenTypes(), false);
		CatalogoAbierto.setSize("100%", "98%");
		CatalogoAbierto.add(FinderButton);;
		
		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		menuBar.setWidth("100%");
		
		MenuItem BotonClose = new MenuItem("New item", false, new Command() {
			public void execute() {
				Controlador.change2Reader();
			}
		});


		BotonClose.setHTML(ActualLang.getClose());
		menuBar.addItem(BotonClose);
		BotonClose.setWidth("100%");
		
		
		
		
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ActualLang.getLoading());
				LoadingPanel.getInstance().hide();
			
	}

	public void refresh() {
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ActualLang.getFiltering());
		ArrayList<Long> Tipos=new ArrayList<Long>();
		for (int i = 0; i < SelectedB.getWidgetCount(); i++) {
			BotonesStackPanelBrowser BSM= (BotonesStackPanelBrowser)SelectedB.getWidget(i);
			Tipos.add(BSM.getEntidad().getID()) ;
		}
		bookReaderServiceHolder.getEntriesIdsByIdsRec(Tipos, new AsyncCallback<ArrayList<FileDB>>() {
			
			public void onSuccess(ArrayList<FileDB> result) {
				generafiltro(result);
				filterAndAdd(result);
				
				
			}
			
			private void generafiltro(ArrayList<FileDB> result) {
				filtroResidual=new ArrayList<Long>();
				for (FileDB long1 : result) {
					filtroResidual.add(long1.getId());
				}
			}

			public void onFailure(Throwable caught) {
				Window.alert(ActualLang.getE_filteringmesagetypes());
				LoadingPanel.getInstance().hide();
			}
		});
	}

	protected void filterAndAdd(ArrayList<FileDB> result) {
		ArrayList<Long> ArrayAnotaciones=new ArrayList<Long>();
		for (FileDB long1 : result) {
			boolean esta=false;
			for (Long PosibleIds : long1.getAnnotationsIds()) {
				for (Long EstaIds : ArrayAnotaciones) {
					if (EstaIds.equals(PosibleIds)) {
						esta=true;
						break;
					}
					
				}
				if (!esta) ArrayAnotaciones.add(PosibleIds);
				
			}
		}
		AsyncCallback<ArrayList<Annotation>> callback=new AsyncCallback<ArrayList<Annotation>>() {
			
			public void onSuccess(ArrayList<Annotation> result) {
				VerticalPanel AnnotationPanel=new VerticalPanel();
				AnotationResultPanel APR=new AnotationResultPanel(AnnotationPanel);
				AnnotationPanel.clear();
				for (Annotation AIndiv : result) {
					AnnotationPanel.add(new CommentPanelBrowser(AIndiv, new Image(ActualUser.getBook().getWebLinks().get(AIndiv.getPageNumber())),APR.getHeight()));
				}
				LoadingPanel.getInstance().hide();
				
				APR.center();
			}
			
			public void onFailure(Throwable caught) {
				Window.alert(ActualLang.getE_filteringmesageAnnotations());
				LoadingPanel.getInstance().hide();
				
			}
		};
		if (ActualUser.getRol().equals(Constants.PROFESSOR)){
		bookReaderServiceHolder.getAnnotationsByIdsTeacher(ArrayAnotaciones,ActualUser.getReadingactivity().getId(),callback );
		}else bookReaderServiceHolder.getAnnotationsByIdsStudent(ArrayAnotaciones,ActualUser.getUser().getId(),ActualUser.getReadingactivity().getId(),callback );
	}
	
	public static ArrayList<Long> getFiltroResidual() {
		return filtroResidual;
	}
	
	public static void refreshButton()
	{
		if (SelectedB.getWidgetCount()==0)btnNewButton.setVisible(false);
		else btnNewButton.setVisible(true);
	}
}
