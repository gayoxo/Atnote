package lector.client.admin.langedit;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Controlador;
import lector.client.reader.LoadingPanel;
import lector.share.model.Language;

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

public class NewAdminLangs implements EntryPoint {

	private VerticalPanel Actual;
	private NewAdminLangs yo;
	static GWTServiceAsync bookReaderServiceHolder = GWT
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
		
		MenuItem menuItem = new MenuItem("GroupAdministration", false, (Command) null);
		menuItem.setHTML("Interface Languages");
		menuItem.setEnabled(false);
		menuBar.addItem(menuItem);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuItem mntmNewItem = new MenuItem("New item", false, new Command() {
			public void execute() {
				newLang NL=new newLang(yo);
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
		horizontalPanel.setSpacing(6);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
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
		
		bookReaderServiceHolder.getLanguages(new AsyncCallback<ArrayList<Language>>() {
			
			public void onSuccess(ArrayList<Language> result) {
				for (int i = 0; i < result.size()-1; i++) {

					BottonLang nue=new BottonLang(Actual,new VerticalPanel(),result.get(i));
					nue.setSize("100%", "100%");

					nue.addClickHandler(new ClickHandler() {
						
						public void onClick(ClickEvent event) {
							SeleccionMenu panel=new SeleccionMenu((BottonLang)event.getSource(),yo);
							panel.showRelativeTo((BottonLang)event.getSource());
						}
					});
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
					nue.setStyleName("gwt-ButtonTOP");
					
				}
				
				if (!result.isEmpty()) {

					BottonLang nue=new BottonLang(Actual,new VerticalPanel(),result.get(result.size()-1));
					nue.setSize("100%", "100%");

					nue.addClickHandler(new ClickHandler() {
						
						public void onClick(ClickEvent event) {
							SeleccionMenu panel=new SeleccionMenu((BottonLang)event.getSource(),yo);
							panel.showRelativeTo((BottonLang)event.getSource());
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
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("I could not refresh the Languages");
				
			}
		});
		
		
		
	}
	
	
	public void refresh()
	{
		Actual.clear();
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Saving...");
bookReaderServiceHolder.getLanguages(new AsyncCallback<ArrayList<Language>>() {
			
			public void onSuccess(ArrayList<Language> result) {
				LoadingPanel.getInstance().hide();
				for (int i = 0; i < result.size()-1; i++) {

					BottonLang nue=new BottonLang(Actual,new VerticalPanel(),result.get(i));
					nue.setSize("100%", "100%");

					nue.addClickHandler(new ClickHandler() {
						
						public void onClick(ClickEvent event) {
							SeleccionMenu panel=new SeleccionMenu((BottonLang)event.getSource(),yo);
							panel.showRelativeTo((BottonLang)event.getSource());
						}
					});
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
					nue.setStyleName("gwt-ButtonTOP");
					
				}
				
				if (!result.isEmpty()) {

					BottonLang nue=new BottonLang(Actual,new VerticalPanel(),result.get(result.size()-1));
					

					nue.addClickHandler(new ClickHandler() {
						
						public void onClick(ClickEvent event) {
							SeleccionMenu panel=new SeleccionMenu((BottonLang)event.getSource(),yo);
							panel.showRelativeTo((BottonLang)event.getSource());
						}
					});
					nue.setSize("100%", "100%");
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
				
			}
			
			public void onFailure(Throwable caught) {
				LoadingPanel.getInstance().hide();
				Window.alert("I could not refresh the Languages");
				
			}
		});
	}
}
