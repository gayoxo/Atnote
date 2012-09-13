package lector.client.login.bookselec;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.InformationConstants;
import lector.client.login.ActualUser;
import lector.client.login.activitysel.Visor;
import lector.client.reader.Book;
import lector.client.reader.LoadingPanel;
import lector.share.model.UserApp;

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
	private VerticalPanel PanelBotones;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	public void onModuleLoad() {
		BooksIDs = new ArrayList<String>();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootPanel RootTXOriginal = RootPanel.get("Original");
		

		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);

		String Bienvenida;
		if ((ActualUser.getUser().getName()!=null)&&(!ActualUser.getUser().getName().isEmpty()))
		Bienvenida= ActualUser.getUser().getName();
		else 
		Bienvenida=ActualUser.getUser().getEmail();
		
		MenuItem TextoBienvenida = new MenuItem(
				Bienvenida + "'s Personal Library ", false,
				(Command) null);
		TextoBienvenida.setEnabled(false);
		menuBar.addItem(TextoBienvenida);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		MenuItem BotonAtras = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		
		
		menuBar.addItem(BotonAtras);


		HorizontalPanel PanelGeneral = new HorizontalPanel();
		
		RootTXOriginal.add(PanelGeneral);
		PanelGeneral.setSize("100%", "100%");
		
		VerticalPanel PanelLibrosGeneral = new VerticalPanel();
		PanelGeneral.add(PanelLibrosGeneral);
		
		VerticalPanel Glue40px = new VerticalPanel();
		PanelLibrosGeneral.add(Glue40px);
		Glue40px.setHeight("40px");
		PanelBotones = new VerticalPanel();
		PanelLibrosGeneral.add(PanelBotones);
		PanelBotones.setSize("500px", "");

		

		SimplePanel PanelIconoGeneral = new SimplePanel();
		PanelGeneral.add(PanelIconoGeneral);
		PanelIconoGeneral.setSize("655px", "655px");
		
		VerticalPanel CentaradorVertical = new VerticalPanel();
		CentaradorVertical.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		CentaradorVertical.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		PanelIconoGeneral.setWidget(CentaradorVertical);
		CentaradorVertical.setSize("100%", "100%");
				
				HorizontalPanel CentradorHorizontal = new HorizontalPanel();
				CentradorHorizontal.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
				CentradorHorizontal.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				CentaradorVertical.add(CentradorHorizontal);
		
				Image Logo = new Image("Logo.jpg");
				CentradorHorizontal.add(Logo);
		BooksIDs = new ArrayList<String>();
		
		generaBookIds();

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
			button.setSize("100%", "100%");
			if (!buttonexist(button)){
				PanelBotones.add(button);
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
					
					if (!SS[SS.length-1].startsWith(" ##"))
						bookReaderServiceHolder.loadFullBookInGoogle(SS[1],
							callback);
					else {
						Window.alert(InformationConstants.THIS_IS_A_LOCAL_BOOK);
						LoadingPanel.getInstance().hide();
					}
				}
			});			
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
		if (PanelBotones.getWidgetCount()>0)
		{
		Widget W=PanelBotones.getWidget(PanelBotones.getWidgetCount()-1);
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
		for (int i = 0; i < PanelBotones.getWidgetCount(); i++) {
			Button B=((Button)PanelBotones.getWidget(i));
			if (B.getText().equals(button.getText())) return true;
		}
		return false;
	}

}
