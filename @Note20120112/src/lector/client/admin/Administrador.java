package lector.client.admin;

import lector.client.admin.activity.ReadingActivity;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Controlador;
import lector.client.login.ActualUser;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.MenuItemSeparator;

public class Administrador implements EntryPoint {

	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private Button btnNewButton_4;
	
	
	public void onModuleLoad() {
		RootPanel RootTXOriginal = RootPanel.get("Original");
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root2");
		RootTXOriginal.setStyleName("Root");
		
		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		
		MenuItem menuItem = new MenuItem("Welcome to de administrator page : " + ActualUser.getUser().getEmail() , false, (Command) null);
		menuItem.setEnabled(false);
		menuBar.addItem(menuItem);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuItem mntmNewItem = new MenuItem("Close Session", false, new Command() {
			public void execute() {
				//Controlador.change2Welcome();
				Window.open(ActualUser.getUser().getLogoutUrl(), "_self", "");
				ActualUser.setUser(null);
				ActualUser.setBook(null);
				ActualUser.setReadingactivity(null);
			}
		});
		menuBar.addItem(mntmNewItem);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		RootTXOriginal.add(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);
		verticalPanel.setSpacing(10);
		verticalPanel.setSize("218px", "");
		
		Button btnNewButton = new Button("Catalogue");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2CatalogAdmin();
			}
		});
		verticalPanel.add(btnNewButton);
		btnNewButton.setWidth("100%");
		
		
		
		Button btnLanguagesAdministration = new Button("Interface Languages");
		btnLanguagesAdministration.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2AdminLenguaje();
			}
		});
		verticalPanel.add(btnLanguagesAdministration);
		btnLanguagesAdministration.setWidth("100%");
		
		
		Button btnNewButton_9 = new Button("Activity");
		btnNewButton_9.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2ActivityAdmin();
			}
		});
		verticalPanel.add(btnNewButton_9);
		btnNewButton_9.setWidth("100%");
		
		
		Button btnNewButton_1 = new Button("Group");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2GroupAdministration();
			}
		});
		verticalPanel.add(btnNewButton_1);
		btnNewButton_1.setWidth("100%");
		
		
		
		Button btnNewButton_5 = new Button("Users");
		btnNewButton_5.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2UserAdministration();
			}
		});
		verticalPanel.add(btnNewButton_5);
		btnNewButton_5.setWidth("100%");
		
		Button btnNewButton_6 = new Button("Administrators");
		btnNewButton_6.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2AdminAdministration();
			}
		});
		verticalPanel.add(btnNewButton_6);
		btnNewButton_6.setWidth("100%");
		
		
		Button btnNewButton_2 = new Button("Get a Book");
		btnNewButton_2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2BookAdminstrator();
			}
		});
		verticalPanel.add(btnNewButton_2);
		btnNewButton_2.setWidth("100%");
		
		Button btnNewButton_3 = new Button("My Books");
		verticalPanel.add(btnNewButton_3);
		btnNewButton_3.setWidth("100%");
		btnNewButton_3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2MyBooks();
			}
		});
		
		Button btnNewButton_10 = new Button("My Activities");
		verticalPanel.add(btnNewButton_10);
		btnNewButton_10.setWidth("100%");
		btnNewButton_10.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2MyActivities();
			}
		});
		
		btnNewButton_4 = new Button("Return to the Activity");
		verticalPanel.add(btnNewButton_4);
		btnNewButton_4.setWidth("100%");
		if (ActualUser.getReadingactivity()==null) btnNewButton_4.setEnabled(false);
		else btnNewButton_4.setEnabled(true);
		btnNewButton_4.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				bookReaderServiceHolder.loadReadingActivityById(ActualUser.getReadingactivity().getId(), new AsyncCallback<ReadingActivity>() {
					
					public void onSuccess(ReadingActivity result) {
						ActualUser.setReadingactivity(null);
						if (checkComplete(result)){
							ActualUser.setReadingactivity(result);
							Controlador.change2Reader();
							
						}
						else {
							btnNewButton_4.setEnabled(false);
							Window.alert("Some atributes was modificated and the Reading Activity are now inaccessible");
						}
					}
					
					private boolean checkComplete(ReadingActivity result) {
						
						return (
								(result.getBookId()!=null)&&
								(result.getCatalogId()!=null)&&
								(result.getGroupId()!=null)&&
								(result.getLanguageName()!=null)
								);
					}

					public void onFailure(Throwable caught) {
						ActualUser.setReadingactivity(null);
						btnNewButton_4.setEnabled(false);
						Window.alert("The acivity don't exist because was removed recently");
						
					}
				});
			}
		});
		
		SimplePanel simplePanel = new SimplePanel();
		horizontalPanel.add(simplePanel);
		simplePanel.setSize("655px", "655px");
		
		Image image = new Image("logo_ucm.jpg");
		simplePanel.setWidget(image);
		image.setSize("100%", "100%");
	}
}
