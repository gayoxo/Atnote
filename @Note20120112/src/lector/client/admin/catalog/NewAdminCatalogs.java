package lector.client.admin.catalog;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.server.Catalogo;
import lector.client.controler.Controlador;

import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class NewAdminCatalogs implements EntryPoint {

	private VerticalPanel Actual;
	private VerticalPanel Selected;
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
		menuBar.setWidth("100%");
		
		MenuItem menuItem = new MenuItem("Catalogue", false, (Command) null);
		menuItem.setHTML("Catalog Administration");
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
		
		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		RootTXOriginal.add(horizontalSplitPanel, 0, 25);
		horizontalSplitPanel.setSize("100%", "100%");
		
		Selected = new VerticalPanel();
		Selected.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalSplitPanel.setRightWidget(Selected);
		Selected.setSize("100%", "");
		
		Actual = new VerticalPanel();
		Actual.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalSplitPanel.setLeftWidget(Actual);
		Actual.setSize("100%", "");
		

		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
bookReaderServiceHolder.getVisbibleCatalogsByProfessorId(ActualUser.getUser().getId(), new AsyncCallback<ArrayList<Catalogo>>() {

	public void onFailure(Throwable caught) {
		LoadingPanel.getInstance().hide();
		
	}

	public void onSuccess(ArrayList<Catalogo> result) {
		LoadingPanel.getInstance().hide();
		ArrayList<Catalogo> CatalogMostrar=result;
		for (Catalogo catalog : CatalogMostrar) {
			Catalog C=Catalog.cloneCatalogo(catalog);
			BottonCatalog nue=new BottonCatalog(Actual,Selected,C);
			nue.setWidth("100%");

			nue.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					SeleccionMenuCatalog panel=new SeleccionMenuCatalog((BottonCatalog)event.getSource(),yo);
					panel.showRelativeTo((BottonCatalog)event.getSource());
				}
			});
			
			
		}
		
	}});

		
		
	}
	
	
	public void refresh()
	{
		Actual.clear();
		Selected.clear();
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
		for (Catalogo catalog : CatalogMostrar) {
			Catalog C=Catalog.cloneCatalogo(catalog);
			BottonCatalog nue=new BottonCatalog(Actual,Selected,C);
			nue.setWidth("100%");

			nue.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					SeleccionMenuCatalog panel=new SeleccionMenuCatalog((BottonCatalog)event.getSource(),yo);
					panel.showRelativeTo((BottonCatalog)event.getSource());
				}
			});
			
			
		}
		
	}});

			
		
	}
}
