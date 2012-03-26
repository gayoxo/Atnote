package lector.client.reader;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.language.Language;
import lector.client.login.ActualUser;
import lector.client.reader.filter.FilterBasicPopUp;
import lector.client.reader.hilocomentarios.Arbitro;
import lector.client.reader.hilocomentarios.JeraquiaSimulada;
import lector.client.reader.hilocomentarios.ParesLlamada;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

/***
 * Main entry point.
 * 
 * @author Cesar y Gayoso
 */
public class MainEntryPoint implements EntryPoint {

	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	// Google Books Widgets
	private static int currentPageNumber = 0;
	private static Book book = new Book();

	// GUI
	private enum State {

		AllAnnotations, SelectedFree, SelectedBloked, NoAnnotations
	};

	private static Image originalBook = new Image("");
	private static Button pageBack = new Button("<");
	private static Button pageForward = new Button(">");
	private static MenuBar menuBar = new MenuBar(false);
	private static TextBox selectorPageBox = new TextBox();
	private static DecoratorPanel decoratorPanel = new DecoratorPanel();
	private static boolean isSelectionMode;
	private static SelectorPanel popUpSelector;
	private static State state = State.NoAnnotations;
	private static MenuItem mntmShowAllComments;
	private static MenuItem mntmShowSelectedComments;
	private static MenuItem mntmBlockedComments;
	private static ScrollPanel ScrollAnnotationsPanel = new ScrollPanel();
	private static VerticalPanel verticalAnnotationsPanel = new VerticalPanel();
	private static TechnicalSpecs technicalSpecs;
	private MenuItem mntmRefresh;
	private MenuItem Annotacion;
	private static MenuItem mntmNoAnnotacion;
	private MenuItem AboutMenuButton;
	private static MenuItem Ficha;
	private final MenuItemSeparator separator = new MenuItemSeparator();
	private final MenuItemSeparator separator_1 = new MenuItemSeparator();
	private static MenuItem mntmFilter;
	private static FilterBasicPopUp TyposFilter;
	private final MenuItemSeparator separator_2 = new MenuItemSeparator();
	private static ArrayList<Long> filtroTypes;
	private static ArrayList<Long> filtroUsers;
	private static ArrayList<Long> filtroAnotPar;
	private final MenuItemSeparator separator_3 = new MenuItemSeparator();
	private static Language ActualLang;
	private MenuItem mntmBrowser;
	private final MenuItemSeparator separator_4 = new MenuItemSeparator();
	private final MenuItemSeparator separator_5 = new MenuItemSeparator();
	private static MenuItem FilterInfo;
	private final HorizontalPanel horizontalPanel = new HorizontalPanel();

	public MainEntryPoint() {
		

		
		pageForward.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				
			}
		});
	
	pageForward.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		

		pageForward.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
	});
		

		pageForward.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});

		pageForward.setStyleName("gwt-ButtonCenter");
		
		
		pageBack.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				
			}
		});
	
pageBack.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		

		pageBack.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
	});
		

		pageBack.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});

		pageBack.setStyleName("gwt-ButtonCenter");

		pageBack.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (currentPageNumber == book.getWebLinks().size() - 1) {
					if (!pageForward.isVisible()) {
						pageForward.setVisible(true);
					}
				}
				currentPageNumber--;
				if (currentPageNumber == 0) {
					pageBack.setVisible(false);
				}

				MainEntryPoint.refreshP();
				Refresh();
			}
		});
		pageForward.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (currentPageNumber == 0) {
					if (!pageBack.isVisible()) {
						pageBack.setVisible(true);
					}
				}
				currentPageNumber++;
				if (currentPageNumber == book.getWebLinks().size() - 1) {
					pageForward.setVisible(false);
				}
				MainEntryPoint.refreshP();
				Refresh();
			}
		});

		selectorPageBox.addChangeHandler(new ChangeHandler() {

			public void onChange(ChangeEvent event) {

				TextBox aux = (TextBox) event.getSource();
				String ele = aux.getText();
				try {
					int pag = Integer.parseInt(ele);
					if ((pag >= 0) && (pag <= book.getWebLinks().size() - 1)) {
						currentPageNumber = pag;
						if (currentPageNumber == 0) {
							pageBack.setVisible(false);
						} else {
							pageBack.setVisible(true);
						}
						if (currentPageNumber == book.getWebLinks().size() - 1) {
							pageForward.setVisible(false);
						} else {
							pageForward.setVisible(true);
						}
						MainEntryPoint.refreshP();
						Refresh();
					} else {
						Window.alert(ActualLang.getE_Page_Dont_Exist());
					}
				} catch (Exception e) {
					Window.alert(ActualLang.getE_Not_a_number());
				}
			}
		});

		
		
		filtroTypes=new ArrayList<Long>();
		filtroUsers=new ArrayList<Long>();
		filtroAnotPar=new ArrayList<Long>();
		originalBook=new Image();
		
	}

	public void onModuleLoad() {

		// Paneles
		final RootPanel RootAnnotation = RootPanel.get("Etiquetas");
		RootPanel RootMenu = RootPanel.get("Menu");
		RootPanel RootTXOriginal = RootPanel.get("Original");
		RootPanel RootTXOriginalB = RootPanel.get("OriginalB");

		// Stilos
		RootMenu.setStyleName("Root");
		RootTXOriginal.setStyleName("Root");

		isSelectionMode = false;
		if (state == null)
			state = State.NoAnnotations; // ya esta inicializado, no es
											// necesario

		// Botones Y Panel de isSelectionMode de Pagina

		selectorPageBox.setReadOnly(false);

		RootTXOriginal.add(decoratorPanel);
		
		ActualLang=ActualUser.getLanguage();
			
		decoratorPanel.setWidget(originalBook);

		// Propiedades de los Elementos

		originalBook.setVisible(false);
		decoratorPanel.setVisible(false);
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		RootTXOriginalB.add(horizontalPanel);
		horizontalPanel.add(pageBack);
		pageBack.setVisible(false);
		horizontalPanel.add(selectorPageBox);
		horizontalPanel.add(pageForward);
		menuBar.clearItems();
		menuBar.setAnimationEnabled(true);
		RootMenu.add(menuBar);

		AboutMenuButton = new MenuItem(ActualLang.getNamePage(), false, new Command() {
			public void execute() {
				PopupPanel About = new About();
				About.showRelativeTo(AboutMenuButton);
			}
		});
		AboutMenuButton.setEnabled(true);

		AboutMenuButton.setHTML(ActualLang.getNamePage());
		
		menuBar.addItem(AboutMenuButton);
		
		menuBar.addSeparator(separator_3);

		Ficha = new MenuItem("New item", false, new Command() {

			public void execute() {
				PopUpFicha PF = new PopUpFicha(technicalSpecs);
				PF.showRelativeTo(Ficha);
			}
		});
		Ficha.setEnabled(false);
		Ficha.setHTML(ActualLang.getSpecifications());
		menuBar.addItem(Ficha);
		MenuBar menuBar_1 = new MenuBar(true);
		menuBar_1.setAnimationEnabled(true);

		Annotacion = new MenuItem(ActualLang.getAnnotation(), false, menuBar_1);

		mntmNoAnnotacion = new MenuItem(ActualLang.getNo_Annotation(), false, new Command() {

			public void execute() {
				mntmNoAnnotacion.setEnabled(false);
				mntmShowSelectedComments.setEnabled(true);
				mntmShowAllComments.setEnabled(true);
				mntmBlockedComments.setEnabled(false);
				mntmBlockedComments.setHTML("<img src=\"NAvar.gif\">");
				state = State.NoAnnotations;
				ScrollAnnotationsPanel.setVisible(false);
				mntmFilter.setEnabled(false);
				mntmFilter.setVisible(false);
				separator_2.setVisible(false);
				mntmBrowser.setVisible(false);
			}
		});
		menuBar_1.addItem(mntmNoAnnotacion);
		mntmNoAnnotacion.setEnabled(false);

		mntmShowAllComments = new MenuItem(ActualLang.getAll_Annotation(), false,
				new Command() {

					public void execute() {
						mntmNoAnnotacion.setEnabled(true);
						mntmShowSelectedComments.setEnabled(true);
						mntmShowAllComments.setEnabled(false);
						mntmBlockedComments.setEnabled(false);
						mntmBlockedComments.setHTML("<img src=\"All.gif\">");
						state = State.AllAnnotations;
						ScrollAnnotationsPanel.setVisible(true);
						mntmFilter.setEnabled(true);
						mntmFilter.setVisible(true);
						separator_2.setVisible(true);
						mntmBrowser.setVisible(true);
						refreshP();
					}
				});
		menuBar_1.addItem(mntmShowAllComments);
		mntmShowAllComments.setEnabled(false);

		mntmShowSelectedComments = new MenuItem(ActualLang.getOnly_Selected(), false,
				new Command() {

					public void execute() {
						mntmNoAnnotacion.setEnabled(true);
						mntmShowSelectedComments.setEnabled(false);
						mntmShowAllComments.setEnabled(true);
						state = State.SelectedFree;
						mntmBlockedComments.setEnabled(false);
						mntmBlockedComments.setHTML("<img src=\"Free.gif\">");
						refreshP();
						ScrollAnnotationsPanel.setVisible(true);
						verticalAnnotationsPanel.clear();
						mntmFilter.setEnabled(true);
						mntmFilter.setVisible(true);
						separator_2.setVisible(true);
						mntmBrowser.setVisible(true);
					}
				});
		menuBar_1.addItem(mntmShowSelectedComments);
		mntmShowSelectedComments.setEnabled(false);
		menuBar.addItem(Annotacion);

		menuBar.addSeparator(separator_1);

		mntmBlockedComments = new MenuItem("Anotaciones Bloqueadas", false,
				new Command() {

					public void execute() {
						state = State.SelectedFree;
						mntmBlockedComments.setHTML("<img src=\"Free.gif\">");
						mntmBlockedComments.setEnabled(false);
					}
				});

		mntmBlockedComments.setEnabled(false);
		mntmBlockedComments.setHTML("<img src=\"NAvar.gif\">");

		menuBar.addItem(mntmBlockedComments);

		mntmRefresh = new MenuItem("Refresh", false, new Command() {

			public void execute() {
				refreshP();
				Refresh();
			}
		});
		mntmRefresh.setHTML("<img src=\"RefreshIco.gif\">");
		menuBar.addItem(mntmRefresh);
		menuBar.addSeparator(separator);

		RefresCoDeTypesYTags();

		mntmFilter = new MenuItem("New item", false, new Command() {

			public void execute() {
				TyposFilter.showRelativeTo(mntmFilter);

			}
		});

		mntmFilter.setHTML(ActualLang.getFilterMainButton());
		mntmFilter.setVisible(false);
		menuBar.addItem(mntmFilter);
		
		mntmBrowser = new MenuItem("New item", false, new Command() {
			public void execute() {
				ActualUser.setBook(book);
				Controlador.change2Browser();
			}
		});
		mntmBrowser.setHTML(ActualLang.getBrowserMainButton());
		mntmBrowser.setVisible(false);
		menuBar.addItem(mntmBrowser);

		menuBar.addSeparator(separator_2);

		MenuItem mntmManage = new MenuItem(ActualLang.getBackAdministrationButton(), false,
				new Command() {

					public void execute() {
						ActualUser.setBook(book);
						Controlador.change2Administrator();
					}
				});
		mntmManage.setHTML(ActualLang.getBackAdministrationButton());
		menuBar.addItem(mntmManage);
		if (!(ActualUser.getUser().getProfile().equals(Constants.PROFESSOR))) {
			mntmManage.setEnabled(false);
			mntmManage.setVisible(false);
		}

		MenuItem mntmManage2 = new MenuItem(ActualLang.getBackUserButton(), false, new Command() {

			public void execute() {
				ActualUser.setBook(book);
				Controlador.change2MyActivities();
			}
		});
		mntmManage2.setHTML(ActualLang.getBackUserButton());
		menuBar.addItem(mntmManage2);
		
		menuBar.addSeparator(separator_4);
		
		menuBar.addSeparator(separator_5);
		
		FilterInfo = new MenuItem(ActualLang.getAnnotationsFiltering(), false, new Command() {
			public void execute() {
				AceptWindow A=new AceptWindow();
				A.center();
			}
		});
		
		if (filtroTypes.size()==1 && filtroTypes.get(0)==Long.MIN_VALUE && filtroUsers.isEmpty())
			setfilterinfo(false);
		else setfilterinfo(true);
		FilterInfo.setStyleName("gwt-MenuItemFiltering");
		menuBar.addItem(FilterInfo);
		if (!(ActualUser.getUser().getProfile().equals(Constants.STUDENT))) {
			mntmManage2.setEnabled(false);
			mntmManage2.setVisible(false);
		}
		
		selectorPageBox.setSize("44px", "100%");
		
		SimplePanel SP=new SimplePanel();
		SP.setHeight("875px");
		//SP.setSize("1000px", "875px");
		SP.add(ScrollAnnotationsPanel);
		RootAnnotation.add(SP);
		
		ScrollAnnotationsPanel.setWidget(verticalAnnotationsPanel);
		ScrollAnnotationsPanel.setHeight("875px");
		ScrollAnnotationsPanel.setWidth("");
		//verticalAnnotationsPanel.setSize("100%", "100%");
		//ScrollAnnotationsPanel.setSize("340px", "875px");
		ScrollAnnotationsPanel.setVisible(false);
		//ScrollAnnotationsPanel.setAlwaysShowScrollBars(true);
		selectorPageBox.setVisible(false);
		pageForward.setVisible(false);

		if (filtroTypes == null ) {
			filtroTypes = new ArrayList<Long>();
		}
		
		if (filtroUsers == null ) {
			filtroUsers = new ArrayList<Long>();
		}
		

		switch (state) {
		case AllAnnotations:
			mntmNoAnnotacion.setEnabled(true);
			mntmShowSelectedComments.setEnabled(true);
			mntmShowAllComments.setEnabled(false);
			mntmBlockedComments.setEnabled(false);
			mntmBlockedComments.setHTML("<img src=\"All.gif\">");
			ScrollAnnotationsPanel.setVisible(true);
			mntmFilter.setVisible(true);
			separator_2.setVisible(true);
			mntmBrowser.setVisible(true);
			break;

		case NoAnnotations:
			mntmNoAnnotacion.setEnabled(false);
			mntmShowSelectedComments.setEnabled(true);
			mntmShowAllComments.setEnabled(true);
			mntmBlockedComments.setEnabled(false);
			mntmBlockedComments.setHTML("<img src=\"NAvar.gif\">");
			ScrollAnnotationsPanel.setVisible(false);
			mntmFilter.setVisible(false);
			separator_2.setVisible(false);
			mntmBrowser.setVisible(false);
			break;

		case SelectedBloked:
			mntmNoAnnotacion.setEnabled(true);
			mntmShowSelectedComments.setEnabled(false);
			mntmShowAllComments.setEnabled(true);
			mntmBlockedComments.setEnabled(false);
			mntmBlockedComments.setHTML("<img src=\"Free.gif\">");
			ScrollAnnotationsPanel.setVisible(true);
			verticalAnnotationsPanel.clear();
			mntmFilter.setVisible(true);
			mntmBlockedComments.setEnabled(false);
			separator_2.setVisible(true);
			mntmBrowser.setVisible(true);
			mntmBlockedComments.setHTML("<img src=\"NAvar.gif\">");
			break;
		case SelectedFree:
			mntmNoAnnotacion.setEnabled(true);
			mntmShowSelectedComments.setEnabled(false);
			mntmShowAllComments.setEnabled(true);
			mntmBlockedComments.setEnabled(false);
			mntmBlockedComments.setHTML("<img src=\"Free.gif\">");
			ScrollAnnotationsPanel.setVisible(true);
			verticalAnnotationsPanel.clear();
			mntmFilter.setVisible(true);
			separator_2.setVisible(true);
			mntmBrowser.setVisible(true);
			break;
		default:
			break;
		}
	}

	protected static void Refresh() {
		if (book.getImagesPath() != null) {
			book.setImagesPath(book.getWebLinks().get(currentPageNumber));
			imageOnLoad(book.getImagesPath());
			//originalBook.setUrl(book.getImagesPath());
			selectorPageBox.setText(Integer.toString(currentPageNumber));
		}
	}

	protected static ArrayList<Annotation> refreshSelected(int x, int y) {
		ArrayList<Annotation> toverticalAnnotationsPanel = new ArrayList<Annotation>();
		if (book.getAnnotations() != null) {
			ArrayList<Annotation> List = book.getAnnotations();
			for (int i = 0; i < List.size(); i++) {
				if (isIn(List.get(i), x, y)) {
					toverticalAnnotationsPanel.add(List.get(i));
				}
			}
		}
		return toverticalAnnotationsPanel;
	}

	protected static void insertrefreshedAnot(ArrayList<Annotation> List) {
		if (List != null) {
			for (int i = 0; i < List.size(); i++) {
				CommentPanel commentPanel = new CommentPanel(List.get(i),
						originalBook);
				JeraquiaSimulada JS=new JeraquiaSimulada();
				Arbitro.getInstance().addLlamada(new ParesLlamada(JS.getVerticalPanel(), List.get(i).getId(),Constants.THREADFATHERNULLID));
				verticalAnnotationsPanel.add(commentPanel);
				verticalAnnotationsPanel.add(JS);
			}
		}
	}

	private static boolean isIn(Annotation annotationTest, int horizontal,
			int vertical) {
		TextSelector TS = annotationTest.getTextSelector();
		boolean resultado = (horizontal >= TS.getX())
				&& (horizontal <= TS.getX() + TS.getWidth());
		resultado = resultado && (vertical >= TS.getY())
				&& (vertical <= TS.getY() + TS.getHeight());
		return resultado;
	}

	protected static void refreshC(int X, int Y) {
		switch (state) {
		case NoAnnotations:
			verticalAnnotationsPanel.clear();
			break;
		case SelectedFree:
			if (verticalAnnotationsPanel.getWidgetCount() != 0) {
				verticalAnnotationsPanel.clear();
			}
			ArrayList<Annotation> Result = refreshSelected(X, Y);
			Result=applyConcreteFilter(Result);
			ArrayList<AnnotationRanked> ResultRanked = setFilerByType(Result);
			Result = OrdenaResultado(ResultRanked);
			insertrefreshedAnot(Result);
			break;
		default:
			break;
		}

	}

	private static ArrayList<Annotation> OrdenaResultado(
			ArrayList<AnnotationRanked> resultRanked) {
		
		ArrayList<Annotation> Resulout =new ArrayList<Annotation>();
		quickSort(resultRanked,0,resultRanked.size()-1);
		for (AnnotationRanked annotation : resultRanked) {
			Resulout.add(annotation.getAnotacionRankeada());
		}
		return Resulout;
	}

	
	private static int partition(ArrayList<AnnotationRanked> resultRanked, int left, int right)
	{
	      int i = left, j = right;
	      AnnotationRanked tmp;
	      float pivot = resultRanked.get((left + right) / 2).getRanking();
	     
	      while (i <= j) {
	            while (resultRanked.get(i).getRanking() > pivot)
	                  i++;
	            while (resultRanked.get(j).getRanking()  < pivot)
	                  j--;
	            if (i <= j) {
	                  tmp = resultRanked.get(i);
	                  resultRanked.set(i, resultRanked.get(j));
	                  resultRanked.set(j, tmp);
	                  i++;
	                  j--;
	            }
	      };
	     
	      return i;
	}
	 
	private static void quickSort(ArrayList<AnnotationRanked> resultRanked, int left, int right) {
		if (!resultRanked.isEmpty()){
	      int index = partition(resultRanked, left, right);
	      if (left < index - 1)
	            quickSort(resultRanked, left, index - 1);
	      if (index < right)
	            quickSort(resultRanked, index, right);
		}
	}
	
	public static void refreshP() {
		if (popUpSelector != null) {
			popUpSelector.hide();
		}
		RefresCoDeTypesYTags();
		verticalAnnotationsPanel.clear();
		switch (state) {
		case AllAnnotations:
			refreshAllComments();
			break;
		case SelectedFree:
			refreshLoadComments();
			break;
		default:
			break;
		}

	}

	private static ArrayList<AnnotationRanked> setFilerByType(
			ArrayList<Annotation> result) {
		
		ArrayList<AnnotationRanked> resultout = new ArrayList<AnnotationRanked>();
		if ((filtroTypes.size() == 1 && filtroTypes.get(0).equals(Constants.ALL))||(filtroTypes.isEmpty()))
			{
			for (Annotation annotationRanked : result) {
				resultout.add(new AnnotationRanked(annotationRanked));
			}
			return resultout;
			}
		
		
//		resultout.addAll(result);
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				ArrayList<Long> A = result.get(i).getFileIds();
				int resulInt=isInType(A);
				if (resulInt!=0) {
					AnnotationRanked act=new AnnotationRanked(result.get(i));
					act.setRanking(resulInt);
					resultout.add(act);
				}
			}
		}

		
		return resultout;
	}



	private static int isInType(ArrayList<Long> a) {
		if (a == null) {
			return 1;
		}
		int points=0;
		for (int pos = 0; pos < filtroTypes.size(); pos++){ 
			
			for (int pos2 = 0; pos2 < a.size();pos2++){
				if (a.get(pos2).equals(filtroTypes.get(pos)))
				points++;
			}
		}
		return points;
	}

	
	private static void RefresCoDeTypesYTags() {
		TyposFilter = new FilterBasicPopUp();	
	}

	protected static void refreshLoadComments() {
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ActualLang.getLoading());
		AsyncCallback<ArrayList<Annotation>> callback = new AsyncCallback<ArrayList<Annotation>>() {

			public void onFailure(Throwable caught) {
				if (!(caught instanceof AnnotationNotFoundException)) {
					Window.alert(ActualLang.getE_Coments_dont_be_refresh());
				}
				book.setAnnotations(new ArrayList<Annotation>());
				LoadingPanel.getInstance().hide();
			}

			public void onSuccess(ArrayList<Annotation> result) {

				book.setAnnotations(result);
				LoadingPanel.getInstance().hide();
			}
		};
		if (ActualUser.getUser().getProfile().equals(Constants.PROFESSOR)) {
			bookReaderServiceHolder.getAnnotationsByPageNumber(
					currentPageNumber, book.getId(),ActualUser.getReadingactivity().getId(), callback);
		} else {
			bookReaderServiceHolder.getAnnotationsByPageNumbertByStudentId(
					currentPageNumber, book.getId(), ActualUser.getUser()
							.getId(), ActualUser.getReadingactivity().getId(), callback);
		}

	}

	protected static void refreshAllComments() {
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ActualLang.getLoading());
		AsyncCallback<ArrayList<Annotation>> callback = new AsyncCallback<ArrayList<Annotation>>() {

			public void onFailure(Throwable caught) {
				if (!(caught instanceof AnnotationNotFoundException)) {
					Window.alert(ActualLang.getE_Coments_dont_be_refresh());
				}
				book.setAnnotations(new ArrayList<Annotation>());
				LoadingPanel.getInstance().hide();
			}

			public void onSuccess(ArrayList<Annotation> result) {

				book.setAnnotations(result);
				result=applyConcreteFilter(result);
				ArrayList<AnnotationRanked> ResultRanked = setFilerByType(result);
				result = OrdenaResultado(ResultRanked);
				insertrefreshedAnot(result);
				LoadingPanel.getInstance().hide();
			}
		};
		if (ActualUser.getUser().getProfile().equals(Constants.PROFESSOR)) {
			bookReaderServiceHolder.getAnnotationsByPageNumber(
					currentPageNumber, book.getId(),ActualUser.getReadingactivity().getId(), callback);
		} else {
			bookReaderServiceHolder.getAnnotationsByPageNumbertByStudentId(
					currentPageNumber, book.getId(), ActualUser.getUser()
							.getId(), ActualUser.getReadingactivity().getId(), callback);
		}

	}


	public static int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public static void setCurrentPageNumber(int currentPageNumber) {
		MainEntryPoint.currentPageNumber = currentPageNumber;
		if (currentPageNumber <= book.getWebLinks().size() - 1) {
				pageForward.setVisible(true);
		}
		else pageForward.setVisible(false);
		if (currentPageNumber >= 0) {
			pageBack.setVisible(true);
		}
		else pageBack.setVisible(false);
		
		
	}

	public static void SetBook(Book result) {
		originalBook.setVisible(true);
		decoratorPanel.setVisible(true);
		pageForward.setVisible(true);
		pageBack.setVisible(false);
		selectorPageBox.setVisible(true);
		Ficha.setEnabled(true);
		book = result;
		verticalAnnotationsPanel.clear();
		MainEntryPoint.refreshP();
		Refresh();
	}

	public static TechnicalSpecs getTechnicalSpecs() {
		if (technicalSpecs==null) technicalSpecs=new TechnicalSpecs(book);
		return technicalSpecs;
	}

	public static ArrayList<Long> getFiltroTypes() {
		return filtroTypes;
	}

	public static void setFiltroTypes(ArrayList<Long> filtroTypes) {
		if (filtroTypes.size()==1 && filtroTypes.get(0)==Long.MIN_VALUE)
			setfilterinfo(false);
		else setfilterinfo(true);
		MainEntryPoint.filtroTypes = filtroTypes;
		filtroAnotPar=new ArrayList<Long>();
		filtroUsers=new ArrayList<Long>();
		if (state == State.AllAnnotations) {
			verticalAnnotationsPanel.clear();
			ArrayList<Annotation> Result = book.getAnnotations();
			Result=applyConcreteFilter(Result);
			ArrayList<AnnotationRanked> ResultRanked = setFilerByType(Result);
			Result = OrdenaResultado(ResultRanked);
			insertrefreshedAnot(Result);
		}
	}

	public static void hidePopUpSelector() {
		popUpSelector.hide();
	}
	
	
	public static void setFiltroTypesAndUser(ArrayList<Long> filtroTypes, ArrayList<Long> User) {
		if (filtroTypes.size()==1 && filtroTypes.get(0)==Long.MIN_VALUE && User.isEmpty())
			setfilterinfo(false);
		else setfilterinfo(true);
		MainEntryPoint.filtroTypes = filtroTypes;
		MainEntryPoint.filtroUsers=User;
		filtroAnotPar=new ArrayList<Long>();
		if (state == State.AllAnnotations) {
			verticalAnnotationsPanel.clear();
			ArrayList<Annotation> Result = book.getAnnotations();
			Result=applyConcreteFilter(Result);
			ArrayList<AnnotationRanked> ResultRanked = setFilerByType(Result);
			ResultRanked=setfilterByUser(ResultRanked);
			Result = OrdenaResultado(ResultRanked);
			insertrefreshedAnot(Result);
		}
	}

	private static ArrayList<Annotation> applyConcreteFilter(
			ArrayList<Annotation> result) {
		if (filtroAnotPar.isEmpty()) 
			return result;
		ArrayList<Annotation> Salida=new ArrayList<Annotation>();
		for (Annotation annotation : result) {
			if (isInList(annotation)) 
				Salida.add(annotation);
		}
		return Salida;
	}

	private static boolean isInList(Annotation annotation) {
		for (Long long1 : filtroAnotPar) {
			if (long1.equals(annotation.getId())) 
				return true;
		}
		return false;
	}

	private static ArrayList<AnnotationRanked> setfilterByUser(
			ArrayList<AnnotationRanked> resultRanked) {
		
		ArrayList<AnnotationRanked> resultout = new ArrayList<AnnotationRanked>();
		
		if (filtroUsers.isEmpty())
			return resultRanked;

		if (resultRanked != null) {
			for (int i = 0; i < resultRanked.size(); i++) {
				Long A = resultRanked.get(i).getAnotacionRankeada().getUserId();
				if (isInUser(A)) {
					resultRanked.get(i).setRanking(resultRanked.get(i).getRanking()+1);
					resultout.add(resultRanked.get(i));
				}
			}
		}

		
		return resultout;
	}

	private static boolean isInUser(Long a) {
		if (a == null) {
			return true;
		}
		for (int pos = 0; pos < filtroUsers.size(); pos++){ 
				if (a.equals(filtroUsers.get(pos)))
					{
					return true;
					}
		}
		return false;
	}
	
	public static void setFiltroAnotPar(ArrayList<Long> filtroAnotPar) {
		MainEntryPoint.filtroAnotPar = filtroAnotPar;
		filtroTypes=new ArrayList<Long>();
		filtroUsers=new ArrayList<Long>();
		
	}
	
	public static void imageOnLoad(String URL)
	{
		
		decoratorPanel.remove(originalBook);
		
		originalBook=new Image();
		
		decoratorPanel.add(originalBook);
		
		originalBook.addMouseMoveHandler(new MouseMoveHandler() {

			public void onMouseMove(MouseMoveEvent event) {
				if (state != State.NoAnnotations) {
					if (isSelectionMode == true) {
						popUpSelector.setTamagno(event.getX(), event.getY());
					} else {
						refreshC(event.getX(), event.getY());
					}

					if (event.isControlKeyDown()
							&& (state == State.SelectedFree)) {
						state = State.SelectedBloked;
						mntmBlockedComments.setHTML("<img src=\"Bloked.gif\">");
						mntmBlockedComments.setEnabled(true);
					}

					if (event.isAltKeyDown() && (state == State.SelectedBloked)) {
						state = State.SelectedFree;
						mntmBlockedComments.setHTML("<img src=\"Free.gif\">");
						mntmBlockedComments.setEnabled(false);
					}

				}

			}
		});

		originalBook.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				event.preventDefault();
				event.stopPropagation();

			}
		});

		originalBook.addMouseDownHandler(new MouseDownHandler() {

			public void onMouseDown(MouseDownEvent event) {
				event.preventDefault();
				event.stopPropagation();
				if (state != State.NoAnnotations) {
					if (!isSelectionMode
							&& (event.getNativeButton() == NativeEvent.BUTTON_LEFT)) {

						popUpSelector = new SelectorPanel(event.getX(), event
								.getY(), originalBook.getAbsoluteLeft(),
								originalBook.getAbsoluteTop(), 0, 0);
						popUpSelector.show();
						isSelectionMode = true;
					}

				}

			}
		});

		originalBook.addMouseUpHandler(new MouseUpHandler() {

			public void onMouseUp(MouseUpEvent event) {
				event.preventDefault();
				event.stopPropagation();
				if (state != State.NoAnnotations) {
					if (isSelectionMode) {
						TextComment TC = new TextComment(popUpSelector
								.getSelector(), book);
						TC.center();
						isSelectionMode = false;
					}

				}

			}
		});
		
		originalBook.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				Image I = (Image) event.getSource();
				float He = I.getHeight();
				float Wi = I.getWidth();
				float prop = He / 830;
				float Winew = (Wi / prop);
				originalBook.setSize(Winew + "px", "830px");
				// Window.alert("Altura: " + He + "Ancho: " + Wi );
			}
		});
		
		originalBook.setUrl(URL);
	}
	
//	public static void setPorcentScrollAnnotationsPanel() {
//		ScrollAnnotationsPanel.setWidth("120%");
//	}
	
	
	public static void setfilterinfo(boolean estado)
	{
		if (FilterInfo!=null){
			FilterInfo.setEnabled(estado);
			FilterInfo.setVisible(estado);
		}
		
	}
}
