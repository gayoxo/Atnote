package lector.client.browser;

import java.util.ArrayList;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.Finder2;
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
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.sun.java.swing.plaf.windows.resources.windows;
import com.google.gwt.user.client.ui.ScrollPanel;

public class Browser implements EntryPoint {

	private Finder2 FinderButton;
	private VerticalPanel Selected;
	private Finder2 FinderButton2;
	private VerticalPanel SelectedB;
	private Language ActualLang;
	private Button btnNewButton;
	private VerticalPanel AnnotationPanel;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private static ArrayList<Long> filtroResidual;

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
		WindowPanel.addWest(BrowserSelectPanel, 500.0);
		
//		SimplePanel simplePanel = new SimplePanel();
//		splitLayoutPanel_1.addNorth(simplePanel, 110.0);
		
		Selected = new VerticalPanel();
		BrowserSelectPanel.addSouth(Selected, 200.0);
		Selected.setWidth("100%");
		
		btnNewButton = new Button(ActualLang.getFilterButtonBrowser());
		btnNewButton.setStyleName("gwt-MenuItemMio");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				refresh(); 
			}
		});
		btnNewButton.setVisible(false);
		Selected.add(btnNewButton);
		btnNewButton.setWidth("100%");
		SelectedB = new VerticalPanel();
		Selected.add(SelectedB);
		SelectedB.setWidth("100%");
		
		FinderButton2 = new Finder2();
		SimplePanel simplePanel= new SimplePanel();
		simplePanel.setSize("100%", "100%");
		simplePanel.add(FinderButton2);
		FinderButton2.setButtonTipo(new BotonesStackPanelBrowser(
				"prototipo", new VerticalPanel(), SelectedB));
		FinderButton2.setBotonClick(new ClickHandler() {

			public void onClick(ClickEvent event) {
				
				BotonesStackPanelBrowser BS = ((BotonesStackPanelBrowser) event
						.getSource());
			
				if ((FinderButton2.getTopPath()==null)&&(BS.getEntidad().getFather()==null)) 
					BS.Swap();
				else if (FinderButton2.getTopPath().getID().equals(BS.getEntidad().getFather().getID())) 
					BS.Swap();
				
				if (SelectedB.getWidgetCount()==0)btnNewButton.setVisible(false);
				else btnNewButton.setVisible(true);
				
			}
		});
		
				FinderButton2.setSize("100%", "100%");
				BrowserSelectPanel.addNorth(simplePanel, 200.0);
				simplePanel.setSize("100%", "100%");
		
		
		
		FinderButton = new Finder2();
		SimplePanel FinderPanel= new SimplePanel();
		FinderPanel.setSize("100%", "100%");
		FinderPanel.add(FinderButton);
		FinderButton.setButtonTipo(new BotonesStackPanelBrowser(
				"prototipo", new VerticalPanel(), SelectedB));
		
		
		FinderButton.setBotonClick(new ClickHandler() {

			public void onClick(ClickEvent event) {
				BotonesStackPanelBrowser BS = ((BotonesStackPanelBrowser) event
						.getSource());
			
				if ((FinderButton.getTopPath()==null)&&(BS.getEntidad().getFather()==null)) 
					BS.Swap();
				else if (FinderButton.getTopPath().getID().equals(BS.getEntidad().getFather().getID())) 
					BS.Swap();
				
				if (SelectedB.getWidgetCount()==0)btnNewButton.setVisible(false);
				else btnNewButton.setVisible(true);
			}
		});

		FinderButton.setSize("100%", "100%");
		
		BrowserSelectPanel.add(FinderPanel);
		
		ScrollPanel scrollPanel = new ScrollPanel();
		WindowPanel.add(scrollPanel);
		
		AnnotationPanel = new VerticalPanel();
		scrollPanel.setWidget(AnnotationPanel);
		AnnotationPanel.setSize("100%", "100%");;
		
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
		
		FinderButton2.setCatalogo(ActualUser.getCatalogo());
		FinderButton.setCatalogo(ActualUser.getOpenCatalog());
		
		
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
				AnnotationPanel.clear();
				for (Annotation AIndiv : result) {
					AnnotationPanel.add(new CommentPanelBrowser(AIndiv, new Image(ActualUser.getBook().getWebLinks().get(AIndiv.getPageNumber()))));
				}
				LoadingPanel.getInstance().hide();
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert(ActualLang.getE_filteringmesageAnnotations());
				LoadingPanel.getInstance().hide();
				
			}
		};
		if (ActualUser.getRol().equals(Constants.PROFESSOR)){
		bookReaderServiceHolder.getAnnotationsByIdsTeacher(ArrayAnotaciones,callback );
		}else bookReaderServiceHolder.getAnnotationsByIdsStudent(ArrayAnotaciones,ActualUser.getUser().getId(),callback );
	}
	
	public static ArrayList<Long> getFiltroResidual() {
		return filtroResidual;
	}
}
