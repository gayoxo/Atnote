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
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;

public class Administrador implements EntryPoint {

	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private Button btnNewButton_4;
	private Button MyProfile;
	
	
	public void onModuleLoad() {
		RootPanel RootTXOriginal = RootPanel.get("Original");
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root2");
		RootTXOriginal.setStyleName("Root");
		
		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		
		String Bienvenida;
		if ((ActualUser.getUser().getName()!=null)&&(!ActualUser.getUser().getName().isEmpty()))
		Bienvenida= ActualUser.getUser().getName();
		else 
		Bienvenida=ActualUser.getUser().getEmail();
		
		MenuItem menuItem = new MenuItem("Welcome to the administrator page : " + Bienvenida , false, (Command) null);
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
		horizontalPanel.setStyleName("Root2");
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setSpacing(12);
		RootTXOriginal.add(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);
		verticalPanel.setSize("400px", "");
		
		Button btnNewButton = new Button("Catalogue");
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnNewButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnNewButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnNewButton.setStyleName("gwt-ButtonTOP");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2CatalogAdmin();
			}
		});
		
		HorizontalPanel Glue = new HorizontalPanel();
		verticalPanel.add(Glue);
		Glue.setSize("100%", "40px");
		verticalPanel.add(btnNewButton);
		btnNewButton.setSize("100%", "100%");
		
		
		
		Button btnLanguagesAdministration = new Button("Interface Languages");
		btnLanguagesAdministration.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnLanguagesAdministration.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnLanguagesAdministration.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnLanguagesAdministration.setStyleName("gwt-ButtonTOP");
		btnLanguagesAdministration.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2AdminLenguaje();
			}
		});
		verticalPanel.add(btnLanguagesAdministration);
		btnLanguagesAdministration.setSize("100%", "100%");
		
		Button btnTemplatesAdministration = new Button("Export Templates");
		btnTemplatesAdministration.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnTemplatesAdministration.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnTemplatesAdministration.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnTemplatesAdministration.setStyleName("gwt-ButtonTOP");
		btnTemplatesAdministration.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2AdminTemplate();
			}
		});
		verticalPanel.add(btnTemplatesAdministration);
		btnTemplatesAdministration.setSize("100%", "100%");
		
		
		
		Button btnNewButton_9 = new Button("Activity");
		btnNewButton_9.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnNewButton_9.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnNewButton_9.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnNewButton_9.setStyleName("gwt-ButtonTOP");
		btnNewButton_9.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2ActivityAdmin();
			}
		});
		verticalPanel.add(btnNewButton_9);
		btnNewButton_9.setSize("100%", "100%");
		
		
		Button btnNewButton_1 = new Button("Group");
		btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnNewButton_1.setStyleName("gwt-ButtonTOP");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2GroupAdministration();
			}
		});
		verticalPanel.add(btnNewButton_1);
		btnNewButton_1.setSize("100%", "100%");
		
		
		
		Button btnNewButton_5 = new Button("Users");
		btnNewButton_5.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnNewButton_5.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnNewButton_5.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnNewButton_5.setStyleName("gwt-ButtonTOP");
		btnNewButton_5.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2UserAdministration();
			}
		});
		verticalPanel.add(btnNewButton_5);
		btnNewButton_5.setSize("100%", "100%");
		
		Button btnNewButton_6 = new Button("Administrators");
		btnNewButton_6.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnNewButton_6.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnNewButton_6.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnNewButton_6.setStyleName("gwt-ButtonTOP");
		btnNewButton_6.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2AdminAdministration();
			}
		});
		verticalPanel.add(btnNewButton_6);
		btnNewButton_6.setSize("100%", "100%");
		
		
		Button btnNewButton_2 = new Button("Get a Book");
		btnNewButton_2.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnNewButton_2.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnNewButton_2.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnNewButton_2.setStyleName("gwt-ButtonTOP");
		btnNewButton_2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2BookAdminstrator();
			}
		});
		verticalPanel.add(btnNewButton_2);
		btnNewButton_2.setSize("100%", "100%");
		//
		Button LoadABook = new Button("Load a Book");
		LoadABook.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		LoadABook.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		LoadABook.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		LoadABook.setStyleName("gwt-ButtonTOP");
		LoadABook.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2LoadABook();
			}
		});
		verticalPanel.add(LoadABook);
		LoadABook.setSize("100%", "100%");
		//
		
		Button btnNewButton_3 = new Button("My Books");
		btnNewButton_3.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnNewButton_3.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnNewButton_3.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnNewButton_3.setStyleName("gwt-ButtonTOP");
		verticalPanel.add(btnNewButton_3);
		btnNewButton_3.setSize("100%", "100%");
		btnNewButton_3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2MyBooks();
			}
		});
		
		Button btnNewButton_10 = new Button("My Activities");
		btnNewButton_10.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnNewButton_10.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnNewButton_10.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnNewButton_10.setStyleName("gwt-ButtonTOP");
		verticalPanel.add(btnNewButton_10);
		btnNewButton_10.setSize("100%", "100%");
		btnNewButton_10.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2MyActivities();
			}
		});
		
		MyProfile = new Button("Edit Profile");
		MyProfile.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2UserEdition();
			}
		});
		MyProfile.setText("My Profile");
		MyProfile.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		MyProfile.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		MyProfile.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		MyProfile.setStyleName("gwt-ButtonTOP");
		verticalPanel.add(MyProfile);
		MyProfile.setSize("100%", "100%");
		
		
		btnNewButton_4 = new Button("Return to the Activity");
		btnNewButton_4.setStyleName("gwt-ButtonBotton");
		btnNewButton_4.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
			}
		});
		btnNewButton_4.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
			}
		});
		btnNewButton_4.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
			}
		});
		
		
		verticalPanel.add(btnNewButton_4);
		btnNewButton_4.setSize("100%", "100%");
		if (ActualUser.getReadingactivity()==null) {
			btnNewButton_4.setEnabled(false);
			btnNewButton_4.setStyleName("gwt-ButtonBottonSelect");
		}
		else {
			btnNewButton_4.setEnabled(true);
			btnNewButton_4.setStyleName("gwt-ButtonBotton");
		}
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
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setWidth("30px");
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.add(verticalPanel_1);
		verticalPanel_1.setSize("100%", "100%");
		
		Image image = new Image("Logo.jpg");
		verticalPanel_1.add(image);
	}
}
