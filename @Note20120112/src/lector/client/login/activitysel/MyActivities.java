package lector.client.login.activitysel;

import java.util.ArrayList;

import lector.client.admin.activity.ReadingActivity;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.book.reader.ImageService;
import lector.client.book.reader.ImageServiceAsync;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.server.Catalogo;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.language.Language;
import lector.client.login.ActualUser;
import lector.client.login.UserApp;
import lector.client.login.bookselec.ButtonActivityReader;
import lector.client.reader.Book;
import lector.client.reader.BookBlob;
import lector.client.reader.LoadingPanel;
import lector.client.reader.MainEntryPoint;

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
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class MyActivities implements EntryPoint {

	private ArrayList<ReadingActivity> BooksIDs = new ArrayList<ReadingActivity>();
	private VerticalPanel verticalPanel = new VerticalPanel();
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	static ImageServiceAsync userImageService = GWT.create(ImageService.class);
	private ReadingActivity RA;
	private static int retryCounter=0;
	private static String RAtemp=null;

	public void onModuleLoad() {
		
		BooksIDs = new ArrayList<ReadingActivity>();
		RootPanel RootMenu = RootPanel.get("Menu");
		
		RootPanel RootTXOriginal = RootPanel.get();
		RootTXOriginal.setStyleName("Root2");
		generaBookIds();

		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);

		String Bienvenida;
		if ((ActualUser.getUser().getName()!=null)&&(!ActualUser.getUser().getName().isEmpty()))
		Bienvenida="Welcome " + ActualUser.getUser().getName();
		else 
		Bienvenida="Welcome " + ActualUser.getUser().getEmail();
		MenuItem menuItem = new MenuItem(Bienvenida, false, (Command) null);
		menuItem.setEnabled(false);
		menuBar.addItem(menuItem);
		
		MenuItem mntmNewItem = new MenuItem("Edit Profile", false, new Command() {
			public void execute() {
				Controlador.change2UserEdition();
			}
		});
		mntmNewItem.setHTML("Edit User");
		

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
			{
			menuBar.addItem(mntmNewItem);
			menuBar.addItem(menuItem_2);
			}

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		RootTXOriginal.add(horizontalPanel, 0, 24);
		horizontalPanel.setSize("100%", "100%");
		verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);
		verticalPanel.setWidth("272px");
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalPanel Glue = new HorizontalPanel();
		verticalPanel.add(Glue);
		Glue.setHeight("20px");
		
//		
//		ButtonActivityReader button = new ButtonActivityReader(new ReadingActivity("HOLA",null,null,null,null));
//		button.setStyleName("gwt-ButtonTOP");
//		button.setWidth("100%");
//		verticalPanel.add(button);
//		button.addMouseDownHandler(new MouseDownHandler() {
//				public void onMouseDown(MouseDownEvent event) {
//					((Button)event.getSource()).setStyleName("gwt-ButtonPush");
//				}
//			});
//		button.addMouseOutHandler(new MouseOutHandler() {
//				public void onMouseOut(MouseOutEvent event) {
//					((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
//				}
//			});
//		button.addMouseOverHandler(new MouseOverHandler() {
//				public void onMouseOver(MouseOverEvent event) {
//					((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
//				}
//			});
		
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.add(verticalPanel_1);
		verticalPanel_1.setSize("100%", "100%");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_1.add(horizontalPanel_1);
		
		Image image_1 = new Image("Logo.jpg");
		horizontalPanel_1.add(image_1);
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
		for (int i = 0; i < BooksIDs.size()-1; i++) {

			ButtonActivityReader button = new ButtonActivityReader(BooksIDs.get(i));
			button.setSize("100%", "100%");
			if (!buttonexist(button)) {
				if (CheckCompleta(button))
				{
				verticalPanel.add(button);
				button.setStyleName("gwt-ButtonTOP");
				button.addMouseDownHandler(new MouseDownHandler() {
					public void onMouseDown(MouseDownEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonPush");
					}
				});
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
				}else
				{
					if (ActualUser.getUser().getProfile().equals(Constants.PROFESSOR))	
					{
						verticalPanel.add(button);
						button.setEnabled(false);
						button.setTitle("Inclomplete Activity Data");
						button.setStyleName("gwt-ButtonGris");
						button.addMouseDownHandler(new MouseDownHandler() {
							public void onMouseDown(MouseDownEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonGrisPush");
							}
						});
						button.addMouseOutHandler(new MouseOutHandler() {
							public void onMouseOut(MouseOutEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonGris");
							}
						});
						button.addMouseOverHandler(new MouseOverHandler() {
							public void onMouseOver(MouseOverEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonGrisOver");
							}
						});
					}
				}
	
				
				button.addClickHandler(new ClickHandler() {
					private AsyncCallback<Book> callback;
					private AsyncCallback<BookBlob> callback2;

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
						
						
						callback2=new AsyncCallback<BookBlob>() {
							
							public void onSuccess(BookBlob result) {
								ActualUser.setReadingactivity(RA);
								Book B=new Book(result.getAuthor(), String.valueOf(result.getId()), result.getPagesCount(), result.getPublishedYear(), result.getTitle(), "LocalBooks.jpg", (String)null);
								ActualUser.setBook(B);
								B.setWebLinks(result.getWebLinks());
								B.setImagesPath(B.getWebLinks().get(0));
								ArrayList<Long> L=new ArrayList<Long>();
								L.add(new Long(Long.MIN_VALUE));
								MainEntryPoint.setFiltroTypes(L);
								loadCatalog();
								LoadingPanel.getInstance().hide();
								
							}
							
							public void onFailure(Throwable caught) {
								if (retryCounter<3)
								{
								retryCounter++;
								//TODO Error a lenguaje
								Window.alert("Try Again " + retryCounter);
								userImageService.loadBookBlobById(Long.parseLong(RAtemp), callback2);
								
								}else
								{
								Window.alert("Failed to load the book, this may be unavailable or the connection is too slow.");
								LoadingPanel.getInstance().hide();
//								throw new UnsupportedOperationException(
//										"Not supported yet.");
								}
								
							}
						};
						ButtonActivityReader B = (ButtonActivityReader) event.getSource();
						String[] SS = B.getRA().getBookId().split(Constants.BREAKER);
						RA=B.getRA();
						retryCounter=0;
						RAtemp=SS[1];
						 if (!RAtemp.startsWith(" ##"))
							 bookReaderServiceHolder.loadFullBookInGoogle(SS[1],
									 callback);
						 else 
						 {
							String[] RAtemp2=RAtemp.split("##");
							RAtemp=RAtemp2[1];
							userImageService.loadBookBlobById(Long.parseLong(RAtemp), callback2);
							
						 }
					}
				});
			}

		}
		
		//Ultimo Botton
		if (!BooksIDs.isEmpty())
		{
			ButtonActivityReader button = new ButtonActivityReader(BooksIDs.get(BooksIDs.size()-1));
			button.setSize("100%", "100%");
			if (!buttonexist(button)) {
				if (CheckCompleta(button))
				{
				verticalPanel.add(button);
				button.setStyleName("gwt-ButtonBotton");
				button.addMouseOutHandler(new MouseOutHandler() {
					public void onMouseOut(MouseOutEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
					}
				});
				button.addMouseOverHandler(new MouseOverHandler() {
					public void onMouseOver(MouseOverEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
					}
				});
				button.addMouseDownHandler(new MouseDownHandler() {
					public void onMouseDown(MouseDownEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
					}
				});
				}else
				{
					if (ActualUser.getUser().getProfile().equals(Constants.PROFESSOR))	
					{
						verticalPanel.add(button);
						button.setEnabled(false);
						button.setTitle("Inclomplete Activity Data");
						button.setStyleName("gwt-ButtonGrisDown");
						button.addMouseOutHandler(new MouseOutHandler() {
							public void onMouseOut(MouseOutEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonGrisDown");
							}
						});
						button.addMouseOverHandler(new MouseOverHandler() {
							public void onMouseOver(MouseOverEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonGrisDownOver");
							}
						});
						button.addMouseDownHandler(new MouseDownHandler() {
							public void onMouseDown(MouseDownEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonGrisDownPush");
							}
						});
					}
				}
				
				
				button.addClickHandler(new ClickHandler() {
					private AsyncCallback<Book> callback;
					private AsyncCallback<BookBlob> callback2;

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
						callback2=new AsyncCallback<BookBlob>() {
							
							public void onSuccess(BookBlob result) {
								ActualUser.setReadingactivity(RA);
								Book B=new Book(result.getAuthor(), String.valueOf(result.getId()), result.getPagesCount(), result.getPublishedYear(), result.getTitle(), "LocalBooks.jpg", (String)null);
								ActualUser.setBook(B);
								B.setWebLinks(result.getWebLinks());
								B.setImagesPath(B.getWebLinks().get(0));
								ArrayList<Long> L=new ArrayList<Long>();
								L.add(new Long(Long.MIN_VALUE));
								MainEntryPoint.setFiltroTypes(L);
								loadCatalog();
								LoadingPanel.getInstance().hide();
								
							}
							
							public void onFailure(Throwable caught) {
								if (retryCounter<3)
								{
								retryCounter++;
								//TODO Error a lenguaje
								Window.alert("Try Again " + retryCounter);
								userImageService.loadBookBlobById(Long.parseLong(RAtemp), callback2);
								
								}else
								{
								Window.alert("Failed to load the book, this may be unavailable or the connection is too slow.");
								LoadingPanel.getInstance().hide();
//								throw new UnsupportedOperationException(
//										"Not supported yet.");
								}
								
							}
						};
						ButtonActivityReader B = (ButtonActivityReader) event.getSource();
						String[] SS = B.getRA().getBookId().split(Constants.BREAKER);
						RA=B.getRA();
						retryCounter=0;
						RAtemp=SS[SS.length-1];
						 if (!RAtemp.startsWith(" ##"))
							 bookReaderServiceHolder.loadFullBookInGoogle(SS[1],
									 callback);
						 else 
						 {
							String[] RAtemp2=RAtemp.split("##");
							RAtemp=RAtemp2[1];
							userImageService.loadBookBlobById(Long.parseLong(RAtemp), callback2);
							
						 }
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
		//El primero es un Glue
		for (int i = 1; i < verticalPanel.getWidgetCount(); i++) {
			Button B = ((Button) verticalPanel.getWidget(i));
			if (B.getText().equals(button.getText()))
				return true;
		}
		return false;
	}
}
