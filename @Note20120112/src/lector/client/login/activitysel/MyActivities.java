package lector.client.login.activitysel;

import java.util.ArrayList;

import lector.client.admin.activity.ReadingActivity;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.server.Catalogo;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.language.Language;
import lector.client.login.ActualUser;
import lector.client.login.UserApp;
import lector.client.login.bookselec.ButtonActivityReader;
import lector.client.reader.Book;
import lector.client.reader.LoadingPanel;
import lector.client.reader.MainEntryPoint;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;

public class MyActivities implements EntryPoint {

	private ArrayList<ReadingActivity> BooksIDs = new ArrayList<ReadingActivity>();
	private VerticalPanel verticalPanel = new VerticalPanel();
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private ReadingActivity RA;
	private static int retryCounter=0;
	private static String RAtemp=null;

	public void onModuleLoad() {
		BooksIDs = new ArrayList<ReadingActivity>();
		verticalPanel = new VerticalPanel();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootPanel RootTXOriginal = RootPanel.get("Original");
		generaBookIds();

		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);

		MenuItem menuItem = new MenuItem("Welcome "
				+ ActualUser.getUser().getEmail(), false, (Command) null);
		menuItem.setEnabled(false);
		menuBar.addItem(menuItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		MenuItem menuItem_1 = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});

		MenuItem menuItem_2 = new MenuItem("Sign out", false, new Command() {
			public void execute() {
				//Controlador.change2Welcome();
				 Window.open(ActualUser.getUser().getLogoutUrl(), "_self", "");
				ActualUser.setUser(null);
				ActualUser.setBook(null);
				ActualUser.setReadingactivity(null);
			}
		});

		if (ActualUser.getUser().getProfile().equals(Constants.PROFESSOR))
			menuBar.addItem(menuItem_1);
		else if (ActualUser.getUser().getProfile().equals(Constants.STUDENT))
			menuBar.addItem(menuItem_2);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		RootTXOriginal.add(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");

		verticalPanel.setSpacing(10);
		horizontalPanel.add(verticalPanel);
		verticalPanel.setSize("218px", "");

		SimplePanel simplePanel = new SimplePanel();
		horizontalPanel.add(simplePanel);
		simplePanel.setSize("655px", "655px");

		Image image = new Image("logo_ucm.jpg");
		simplePanel.setWidget(image);
		image.setSize("100%", "100%");
		BooksIDs = new ArrayList<ReadingActivity>();

	}

	private void generaBookIds() {

		UserApp User = ActualUser.getUser();
		if (User.getProfile().equals(Constants.PROFESSOR)) {
			bookReaderServiceHolder.getReadingActivityByProfessorId(
					User.getId(),
					new AsyncCallback<ArrayList<ReadingActivity>>()

					{

						public void onSuccess(ArrayList<ReadingActivity> result) {
							BooksIDs = result;
							setealibros();

						}

						public void onFailure(Throwable caught) {
							Window.alert("The Activities could not be retrived, try again later");

						}
					});

		} else if (User.getProfile().equals(Constants.STUDENT)) {

			bookReaderServiceHolder.getReadingActivityByUserId(User.getId(),
					new AsyncCallback<ArrayList<ReadingActivity>>()

					{

						public void onSuccess(ArrayList<ReadingActivity> result) {
							BooksIDs = result;
							setealibros();

						}

						public void onFailure(Throwable caught) {
							Window.alert("The Activities could not be retrived, try again later");

						}
					});

		}
	}

	private void setealibros() {
		for (int i = 0; i < BooksIDs.size(); i++) {

			ButtonActivityReader button = new ButtonActivityReader(BooksIDs.get(i));
			if (!buttonexist(button)) {
				if (CheckCompleta(button))
				{
				verticalPanel.add(button);
				}else
				{
					if (ActualUser.getUser().getProfile().equals(Constants.PROFESSOR))	
					{
						verticalPanel.add(button);
						button.setEnabled(false);
						button.setTitle("Inclomplete Activity Data");
					}
				}
				button.setWidth("100%");
				button.addClickHandler(new ClickHandler() {
					private AsyncCallback<Book> callback;

					public void onClick(ClickEvent event) {

						LoadingPanel.getInstance().center();
						LoadingPanel.getInstance().setLabelTexto("Loading...");
						callback = new AsyncCallback<Book>() {

							public void onFailure(Throwable caught) {
								if (retryCounter<3)
								{
								retryCounter++;
								//TODO Error a lenguaje
								Window.alert("Try Again " + retryCounter);
								bookReaderServiceHolder.loadFullBookInGoogle(RAtemp,
										callback);
								
								}else
								{
								Window.alert("Failed to load the book, this may be unavailable or the connection is too slow.");
								LoadingPanel.getInstance().hide();
//								throw new UnsupportedOperationException(
//										"Not supported yet.");
								}
							}

							public void onSuccess(Book result) {
								ActualUser.setReadingactivity(RA);
								ActualUser.setBook(result);
								ArrayList<Long> L=new ArrayList<Long>();
								L.add(new Long(Long.MIN_VALUE));
								MainEntryPoint.setFiltroTypes(L);
								loadCatalog();
								LoadingPanel.getInstance().hide();

								// MainEntryPoint.SetBook(result);
								// labelTester.setText(result.getAuthor());
								// MainEntryPoint.getTechnicalSpecs().setBook(result);

							}


						};
						ButtonActivityReader B = (ButtonActivityReader) event.getSource();
						String[] SS = B.getRA().getBookId().split(Constants.BREAKER);
						RA=B.getRA();
						retryCounter=0;
						RAtemp=SS[1];
						bookReaderServiceHolder.loadFullBookInGoogle(SS[1],
								callback);
					}
				});
			}

		}
	}

	private boolean CheckCompleta(ButtonActivityReader button) {
		ReadingActivity RA=button.getRA();
		if ((RA.getBookId()!=null)&(RA.getCatalogId()!=null)&(RA.getGroupId()!=null)&(RA.getLanguageName()!=null)&(RA.getOpenCatalogId()!=null))
			return true; 
		else return false;
	}

	protected void loadCatalog()
	{
		bookReaderServiceHolder.loadCatalogById(RA.getCatalogId(), new AsyncCallback<Catalogo>() {
			
			public void onSuccess(Catalogo result) {
				Catalog temp=new Catalog();
				temp.setCatalogName(result.getCatalogName());
				temp.setId(result.getId());
				temp.setPrivate(result.isIsPrivate());
				temp.setProfessorId(result.getProfessorId());
				ActualUser.setCatalogo(temp);
				loadOpenCatalog();
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Book not supported yet");
				throw new UnsupportedOperationException(
						"Not supported yet.");
				
			}
		});
	}
	
	protected void loadOpenCatalog()
	{
		bookReaderServiceHolder.loadCatalogById(RA.getOpenCatalogId(), new AsyncCallback<Catalogo>() {
			
			public void onSuccess(Catalogo result) {
				Catalog temp=new Catalog();
				temp.setCatalogName(result.getCatalogName());
				temp.setId(result.getId());
				temp.setPrivate(result.isIsPrivate());
				temp.setProfessorId(result.getProfessorId());
				ActualUser.setOpenCatalog(temp);
				loadLanguage();
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Book not supported yet");
				throw new UnsupportedOperationException(
						"Not supported yet.");
				
			}
		});
	}
	
	protected void loadLanguage() {
		bookReaderServiceHolder.loadLanguageByName(RA.getLanguageName(), new AsyncCallback<Language>() {
			
			public void onSuccess(Language result) {
				ActualUser.setLanguage(result);
				Controlador.change2Reader();
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Book not supported yet");
				throw new UnsupportedOperationException(
						"Not supported yet.");
				
			}
		});
		
	}

	private boolean buttonexist(Button button) {
		for (int i = 0; i < verticalPanel.getWidgetCount(); i++) {
			Button B = ((Button) verticalPanel.getWidget(i));
			if (B.getText().equals(button.getText()))
				return true;
		}
		return false;
	}

}
