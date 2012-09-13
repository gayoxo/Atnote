package lector.client.admin.catalog;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.Catalog;
import lector.client.controler.Controlador;

import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;
import lector.share.model.Catalogo;

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
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;

public class NewAdminCatalogs implements EntryPoint {

	private VerticalPanel Actual;
	private NewAdminCatalogs yo;
	private GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);

	public void onModuleLoad() {
		RootPanel RootTXOriginal = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root");
		RootTXOriginal.setStyleName("Root");
		
		yo=this;
		
		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		menuBar.setWidth("99%");
		
		MenuItem menuItem = new MenuItem("Catalogue", false, (Command) null);
		menuItem.setHTML("Catalogue Administration");
		menuItem.setEnabled(false);
		menuBar.addItem(menuItem);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuItem mntmNewItem = new MenuItem("New item", false, new Command() {
			public void execute() {
				newCatalog NL=new newCatalog(yo);
				NL.center();
				
			}
		});
		mntmNewItem.setHTML("New");
		menuBar.addItem(mntmNewItem);
		
		MenuItemSeparator separator_2 = new MenuItemSeparator();
		menuBar.addSeparator(separator_2);
		
		MenuItem mntmBack = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		menuBar.addItem(mntmBack);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		RootTXOriginal.add(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");
		
		Actual = new VerticalPanel();
		horizontalPanel.add(Actual);
		Actual.setWidth("400px");
		Actual.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.add(verticalPanel_1);
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_1.add(horizontalPanel_1);
		
		Image image = new Image("Logo.jpg");
		horizontalPanel_1.add(image);
		
//		HorizontalPanel horizontalPanel = new HorizontalPanel();
//		Actual.add(horizontalPanel);
//		horizontalPanel.setHeight("20px");
		

		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
bookReaderServiceHolder.getVisbibleCatalogsByProfessorId(ActualUser.getUser().getId(), new AsyncCallback<ArrayList<Catalogo>>() {

	public void onFailure(Throwable caught) {
		LoadingPanel.getInstance().hide();
		
	}

	public void onSuccess(ArrayList<Catalogo> result) {
		LoadingPanel.getInstance().hide();
		ArrayList<Catalogo> CatalogMostrar=result;
		for (int i = 0; i < CatalogMostrar.size()-1; i++) {
			Catalog C=Catalog.cloneCatalogo(CatalogMostrar.get(i));
			BottonCatalog nue=new BottonCatalog(Actual,new VerticalPanel(),C);
			nue.setSize("100%", "100%");

			nue.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					SeleccionMenuCatalog panel=new SeleccionMenuCatalog((BottonCatalog)event.getSource(),yo);
					panel.showRelativeTo((BottonCatalog)event.getSource());
				}
			});
			nue.setStyleName("gwt-ButtonTOP");
			nue.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonPush");
				}
			});
			nue.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
				}
			});
			nue.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
				}
			});
		}
		if (!CatalogMostrar.isEmpty())
		{
			Catalog C=Catalog.cloneCatalogo(CatalogMostrar.get(CatalogMostrar.size()-1));
			BottonCatalog nue=new BottonCatalog(Actual,new VerticalPanel(),C);
			nue.setSize("100%", "100%");

			nue.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					SeleccionMenuCatalog panel=new SeleccionMenuCatalog((BottonCatalog)event.getSource(),yo);
					panel.showRelativeTo((BottonCatalog)event.getSource());
				}
			});
			nue.setStyleName("gwt-ButtonBotton");
			nue.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
				}
			});
			nue.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
				}
			});
			nue.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
				}
			});
		}
		
		
	}});

		
		
	}
	
	
	public void refresh()
	{
		Actual.clear();
//		HorizontalPanel horizontalPanel = new HorizontalPanel();
//		Actual.add(horizontalPanel);
//		horizontalPanel.setHeight("20px");
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
bookReaderServiceHolder.getVisbibleCatalogsByProfessorId(ActualUser.getUser().getId(), new AsyncCallback<ArrayList<Catalogo>>() {

	public void onFailure(Throwable caught) {
		Window.alert("There was a problem encounter while loading the catalogs, please try again later");
		LoadingPanel.getInstance().hide();
		
	}

	public void onSuccess(ArrayList<Catalogo> result) {
		LoadingPanel.getInstance().hide();
		ArrayList<Catalogo> CatalogMostrar=result;
		for (int i = 0; i < CatalogMostrar.size()-1; i++) {
			Catalog C=Catalog.cloneCatalogo(CatalogMostrar.get(i));
			BottonCatalog nue=new BottonCatalog(Actual,new VerticalPanel(),C);
			nue.setSize("100%", "100%");

			nue.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					SeleccionMenuCatalog panel=new SeleccionMenuCatalog((BottonCatalog)event.getSource(),yo);
					panel.showRelativeTo((BottonCatalog)event.getSource());
				}
			});
			nue.setStyleName("gwt-ButtonTOP");
			nue.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonPush");
				}
			});
			nue.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
				}
			});
			nue.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
				}
			});
		}
		if (!CatalogMostrar.isEmpty())
		{
			Catalog C=Catalog.cloneCatalogo(CatalogMostrar.get(CatalogMostrar.size()-1));
			BottonCatalog nue=new BottonCatalog(Actual,new VerticalPanel(),C);
			nue.setSize("100%", "100%");;

			nue.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					SeleccionMenuCatalog panel=new SeleccionMenuCatalog((BottonCatalog)event.getSource(),yo);
					panel.showRelativeTo((BottonCatalog)event.getSource());
				}
			});
			nue.setStyleName("gwt-ButtonBotton");
			nue.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
				}
			});
			nue.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
				}
			});
			nue.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
				}
			});
		}
		
	}});

			
		
	}
}
