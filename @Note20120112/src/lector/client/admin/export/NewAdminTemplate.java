package lector.client.admin.export;

import java.util.ArrayList;

import lector.client.admin.export.template.Template;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.util.PreventSpuriousRebuilds;

public class NewAdminTemplate implements EntryPoint  {
	private VerticalPanel Actual;
	private NewAdminTemplate yo;
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);

	public void onModuleLoad() {
		RootPanel RootTXOriginal = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root");
		RootTXOriginal.setStyleName("Root");

		yo = this;

		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		menuBar.setWidth("100%");

		MenuItem menuItem = new MenuItem("Activity", false,
				(Command) null);
		menuItem.setHTML("Template Administration");
		menuItem.setEnabled(false);
		menuBar.addItem(menuItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		MenuItem mntmNewItem = new MenuItem("New item", false, new Command() {
			public void execute() {
				newTemplate NL = new newTemplate(yo);
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
		horizontalPanel.setWidth("100%");
		
				Actual = new VerticalPanel();
				horizontalPanel.add(Actual);
				Actual.setWidth("400px");
				
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

		RecargaLosTEmplates();

	}

	private void RecargaLosTEmplates() {
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
		exportServiceHolder.getTemplates(new AsyncCallback<ArrayList<Template>>() {
			
			public void onSuccess(ArrayList<Template> result) {
				LoadingPanel.getInstance().hide();
				for (int i = 0; i < result.size(); i++) {
					ButtonTemplate B;
					if (i==(result.size()-1))
						B=new ButtonTemplate(result.get(i),true);
					else B=new ButtonTemplate(result.get(i),false);
					Actual.add(B);
					B.addClickHandler(new ClickHandler() {
						
						public void onClick(ClickEvent event) {
							event.preventDefault();
							event.stopPropagation();
							ButtonTemplate BT=(ButtonTemplate)event.getSource();
							Template T=BT.getTemplate();
							if (event.getNativeButton() == NativeEvent.BUTTON_LEFT)
								{
								MenuOpciones MO=new MenuOpciones(T,yo);
								MO.showRelativeTo(BT);
								
								}
							
						}
					});
				}
				
			}
			
			public void onFailure(Throwable caught) {
				LoadingPanel.getInstance().hide();
				Window.alert(ErrorConstants.ERROR_REFRESH_TEMPLATES);
				
			}
		});
		
	}

	public void refresh() {
		Actual.clear();
		RecargaLosTEmplates();

	}

}
