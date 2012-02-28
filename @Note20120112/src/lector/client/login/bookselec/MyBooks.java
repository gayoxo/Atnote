package lector.client.login.bookselec;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.login.ActualUser;
import lector.client.login.UserApp;
import lector.client.login.activitysel.Visor;
import lector.client.reader.Book;
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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class MyBooks implements EntryPoint {

	private ArrayList<String> BooksIDs = new ArrayList<String>();
	private VerticalPanel verticalPanel = new VerticalPanel();
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	public void onModuleLoad() {
		BooksIDs = new ArrayList<String>();
		verticalPanel = new VerticalPanel();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootPanel RootTXOriginal = RootPanel.get("Original");
		generaBookIds();

		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);

		MenuItem menuItem = new MenuItem(
				"Welcome " + ActualUser.getUser().getEmail(), false,
				(Command) null);
		menuItem.setEnabled(false);
		menuBar.addItem(menuItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		MenuItem menuItem_1 = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		
		
		menuBar.addItem(menuItem_1);


		HorizontalPanel horizontalPanel = new HorizontalPanel();
		RootTXOriginal.add(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");

		
		verticalPanel.setSpacing(10);
		horizontalPanel.add(verticalPanel);
		verticalPanel.setSize("218px", "");

		

		SimplePanel simplePanel = new SimplePanel();
		horizontalPanel.add(simplePanel);
		simplePanel.setSize("655px", "655px");
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		simplePanel.setWidget(verticalPanel_1);
		verticalPanel_1.setSize("100%", "100%");
				
				HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
				horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
				horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				verticalPanel_1.add(horizontalPanel_1);
		
				Image image = new Image("Logo.jpg");
				horizontalPanel_1.add(image);
		BooksIDs = new ArrayList<String>();

	}

	private void generaBookIds() {
		
		UserApp User = ActualUser.getUser();
		if (User.getProfile().equals(Constants.PROFESSOR)){
			BooksIDs = User.getBookIds();
			setealibros();
		}
		
	}

	private void setealibros() {
		for (int i = 0; i < BooksIDs.size(); i++) {

			Button button = new Button(BooksIDs.get(i));
			if (!buttonexist(button)){
				verticalPanel.add(button);
			button.setWidth("100%");
			button.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {

					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto("Loading...");
					AsyncCallback<Book> callback = new AsyncCallback<Book>() {

						public void onFailure(Throwable caught) {
							Window.alert("Book not supported yet");
//							throw new UnsupportedOperationException(
//									"Not supported yet.");
							LoadingPanel.getInstance().hide();

						}

						public void onSuccess(Book result) {
							Visor Visor=new Visor(result.getImagesPath());
							Visor.center();
//							Controlador.change2VisorEntry(result.getImagesPath());
							LoadingPanel.getInstance().hide();
						}
					};
					Button B = (Button) event.getSource();
					String[] SS=B.getHTML().split(Constants.BREAKER);

					bookReaderServiceHolder.loadFullBookInGoogle(SS[1],
							callback);
				}
			});
			button.setSize("100%", "100%");
			button.setStyleName("gwt-ButtonTOP");
			button.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
				}
			});
			button.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
				}
			});
			button.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonPush");
				}
			});
		}
		
	}
		if (verticalPanel.getWidgetCount()>0)
		{
		Widget W=verticalPanel.getWidget(verticalPanel.getWidgetCount()-1);
		Button B=(Button)W;
		B.setSize("100%", "100%");
		B.setStyleName("gwt-ButtonBotton");
		B.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
				}
			});
		B.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
				}
			});
		B.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
				}
			});
	}
	}



	private boolean buttonexist(Button button) {
		for (int i = 0; i < verticalPanel.getWidgetCount(); i++) {
			Button B=((Button)verticalPanel.getWidget(i));
			if (B.getText().equals(button.getText())) return true;
		}
		return false;
	}

}
