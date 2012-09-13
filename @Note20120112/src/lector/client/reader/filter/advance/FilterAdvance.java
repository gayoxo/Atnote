package lector.client.reader.filter.advance;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.browser.BotonesStackPanelBrowser;
import lector.client.catalogo.Finder;
import lector.client.catalogo.FinderGrafo;
import lector.client.catalogo.FinderKeys;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.login.ActualUser;
import lector.client.reader.BotonesStackPanelReaderSelectMio;
import lector.client.reader.ClickHandlerMioSelector;
import lector.client.reader.MainEntryPoint;
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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;

public class FilterAdvance implements EntryPoint{

	private static Rule ActualRule=null;
	private static VerticalPanel Rules;
	private VerticalPanel AlumnosYProfesores;
	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private Finder FinderButton2;
	private Finder FinderButton1;
//	private static VerticalPanel AnotacionesResultado;
	private DecoratedTabPanel decoratedTabPanel;
	
	public FilterAdvance() {
		Rules = new VerticalPanel();
		Rules.setSize("100%", "100%");
	}
	
	public void onModuleLoad() {
		RootPanel rootPanel=RootPanel.get();
		RootPanel rootMenu=RootPanel.get("Menu");
		
		MenuBar menuBar = new MenuBar(false);
		rootMenu.add(menuBar);
		
		MenuItem mntmClose = new MenuItem(ActualUser.getLanguage().getCloseFA(), false, new Command() {
			public void execute() {
				Controlador.change2Reader();
			}
		});
		menuBar.addItem(mntmClose);
		
		MenuItem mntmFilter = new MenuItem(ActualUser.getLanguage().getFilterButtonFA(), false, new Command() {
			public void execute() {
				ArrayList<Rule> auxRul=new ArrayList<Rule>();
				for (int i = 0; i < Rules.getWidgetCount(); i++) {
					auxRul.add((Rule)Rules.getWidget(i));
				}
				new FilterAsyncSystem(auxRul);
			}
		});
		menuBar.addItem(mntmFilter);
		
		MenuItem mntmNewRule = new MenuItem(ActualUser.getLanguage().getNew_Rule(), false, new Command() {
			public void execute() {
				newRule NR=new newRule(); 
				NR.center();
			}
		});
		menuBar.addItem(mntmNewRule);
		SimplePanel General = new SimplePanel();
		rootPanel.add(General,0,25);
		General.setSize("100%", "96%");
//		
//		ScrollPanel scrollPanel_1 = new ScrollPanel();
//		General.addEast(scrollPanel_1, 400.0);
//		
//		AnotacionesResultado = new VerticalPanel();
////		scrollPanel_1.setWidget(AnotacionesResultado);
//		AnotacionesResultado.setSize("100%", "100%");
		
		SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel();
		General.add(splitLayoutPanel);
		splitLayoutPanel.setSize("100%", "100%");
		
		
		FinderGrafo.setButtonTipoGrafo(new BotonesStackPanelBrowser(
				"prototipo", new VerticalPanel(), new VerticalPanel(),FinderButton2));
		FinderGrafo.setBotonClickGrafo(new ClickHandler() {

			public void onClick(ClickEvent event) {
				Rule R=FilterAdvance.getActualRule();
				if (R!=null)
					{
					AssertRule A= new AssertRule(((BotonesStackPanelBrowser)event.getSource()).getEntidad().getName(),
							R.getRulePanel(),
							((BotonesStackPanelBrowser)event.getSource()).getEntidad().getID(),
							Tiposids.Tipo);
					R.addAssertRule(A);
					}
				
				
			}
		});
		
		FinderKeys.setButtonTipo(new BotonesStackPanelBrowser(
				"prototipo", new VerticalPanel(), new VerticalPanel(),FinderButton2));
		 FinderKeys.setBotonClick(new ClickHandlerMioFilterAdvance());
		 
//		FinderButton2.setCatalogo(ActualUser.getCatalogo());
		
//		FinderButton2 = new FinderGrafo(ActualUser.getCatalogo());
		
		if (ActualUser.getReadingactivity().getVisualizacion()==null||ActualUser.getReadingactivity().getVisualizacion().equals(Constants.VISUAL_ARBOL))
        {
			FinderButton2= new FinderGrafo(ActualUser.getCatalogo());
			//FinderButton2.RefrescaLosDatos();
        }
        else 
        {
        	FinderButton2= new FinderKeys();
        	FinderButton2.setCatalogo(ActualUser.getCatalogo());
          //  FinderButton2.RefrescaLosDatos();
        }
		
//		FinderGrafo.setButtonTipoGrafo(new BotonesStackPanelBrowser(
//				"prototipo", new VerticalPanel(), new VerticalPanel(),FinderButton2));
//		FinderGrafo.setBotonClickGrafo(new ClickHandler() {
//
//			public void onClick(ClickEvent event) {
//				Rule R=FilterAdvance.getActualRule();
//				if (R!=null)
//					{
//					AssertRule A= new AssertRule(((BotonesStackPanelBrowser)event.getSource()).getEntidad().getName(),
//							R.getRulePanel(),
//							((BotonesStackPanelBrowser)event.getSource()).getEntidad().getID(),
//							Tiposids.Tipo);
//					R.addAssertRule(A);
//					}
//				
//				
//			}
//		});
//		
		FinderButton2.setSize("100%", "100%");
		
		SplitLayoutPanel splitLayoutPanel_1 = new SplitLayoutPanel();
		splitLayoutPanel.addSouth(splitLayoutPanel_1, 185.0);
		
		SimplePanel decoratorPanel = new SimplePanel();
		splitLayoutPanel_1.addWest(decoratorPanel, 400.0);
		decoratorPanel.setSize("100%", "100%");
		
		ScrollPanel scrollPanel_3 = new ScrollPanel();
		decoratorPanel.setWidget(scrollPanel_3);
		scrollPanel_3.setSize("100%", "100%");
		
		AlumnosYProfesores = new VerticalPanel();
		scrollPanel_3.setWidget(AlumnosYProfesores);
		AlumnosYProfesores.setSize("100%", "100%");
		
		ScrollPanel scrollPanel = new ScrollPanel();
		splitLayoutPanel_1.add(scrollPanel);
		scrollPanel.setSize("100%", "100%");
		
		scrollPanel.setWidget(Rules);
		
		
		
		FinderGrafo.setButtonTipoGrafo(new BotonesStackPanelBrowser(
				"prototipo", new VerticalPanel(), new VerticalPanel(),FinderButton1));
		FinderGrafo.setBotonClickGrafo(new ClickHandler() {

			public void onClick(ClickEvent event) {
				Rule R=FilterAdvance.getActualRule();
				if (R!=null)
					{
					AssertRule A= new AssertRule(((BotonesStackPanelBrowser)event.getSource()).getEntidad().getName(),
							R.getRulePanel(),
							((BotonesStackPanelBrowser)event.getSource()).getEntidad().getID(),
							Tiposids.Tipo);
					R.addAssertRule(A);
					}
				
				
			}
		});
		
		FinderKeys.setButtonTipo(new BotonesStackPanelBrowser(
				"prototipo", new VerticalPanel(), new VerticalPanel(),FinderButton1));
		 FinderKeys.setBotonClick(new ClickHandlerMioFilterAdvance());
		 
		 
	//	FinderButton1.setCatalogo(ActualUser.getOpenCatalog());
		
		 //FinderButton1 = new FinderGrafo(ActualUser.getOpenCatalog());
		 if (ActualUser.getReadingactivity().getVisualizacion()==null||ActualUser.getReadingactivity().getVisualizacion().equals(Constants.VISUAL_ARBOL))
	        {
			 FinderButton1= new FinderGrafo(ActualUser.getOpenCatalog());
			// FinderButton1.RefrescaLosDatos();
	        }
	        else 
	        {
	        	FinderButton1= new FinderKeys();
	        	FinderButton1.setCatalogo(ActualUser.getOpenCatalog());
	        //	FinderButton1.RefrescaLosDatos();
	        }
		
		FinderButton1.setSize("100%", "100%");
		
		
		ScrollPanel scrollPanel_1 = new ScrollPanel();
		splitLayoutPanel.add(scrollPanel_1);
		
		decoratedTabPanel = new DecoratedTabPanel();
		decoratedTabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				int selectedd=event.getSelectedItem();
				if (selectedd==0)
					FinderButton2.RefrescaLosDatos();
				else 
					FinderButton1.RefrescaLosDatos();
			}
		});
		scrollPanel_1.setWidget(decoratedTabPanel);
		decoratedTabPanel.setSize("100%", "100%");
		
		SimplePanel Catalogo2 = new SimplePanel();
		decoratedTabPanel.add(Catalogo2, ActualUser.getLanguage().getTeacherTypes(), false);
		Catalogo2.add(FinderButton2);
		Catalogo2.setSize("100%", "100%");
		
		decoratedTabPanel.selectTab(0);
		
		SimplePanel Catalogo1 = new SimplePanel();
		decoratedTabPanel.add(Catalogo1, ActualUser.getLanguage().getOpenTypes(), false);
		Catalogo1.setSize("100%", "100%");
		Catalogo1.add(FinderButton1);
		
		
		
		
		
		bookReaderServiceHolder.getUsersByGroupId(ActualUser.getReadingactivity().getGroupId(), new AsyncCallback<ArrayList<UserApp>>() {
			
			public void onSuccess(ArrayList<UserApp> result) {
				for (UserApp User : result) {
					ButtonUser B;
					if (User.getName()!=null&&!User.getName().isEmpty())
					 B=new ButtonUser(User.getName(),User.getId());
					else  B=new ButtonUser(User.getEmail(),User.getId());
					B.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							Rule R=FilterAdvance.getActualRule();
							if (R!=null)
								{
								AssertRule A= new AssertRule(((ButtonUser)event.getSource()).getHTML(),
										R.getRulePanel(),((ButtonUser)event.getSource()).getID(),
										Tiposids.User);
								R.addAssertRule(A);
								}
						}
					});
					B.setSize("100%", "100%");
					B.setStyleName("gwt-ButtonTOP");
					B.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
						}
					});
					B.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
						}
					});
					B.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonPush");
						}
					});
					AlumnosYProfesores.add(B);
				}
				
				bookReaderServiceHolder.loadUserById(ActualUser.getReadingactivity().getProfessorId(), new AsyncCallback<UserApp>() {
					
					public void onSuccess(UserApp result) {
						ButtonUser B;
						if (result.getName()!=null&&!result.getName().isEmpty())
							 B=new ButtonUser(result.getName(),result.getId());
							else  B=new ButtonUser(result.getEmail(),result.getId());
						B.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								Rule R=FilterAdvance.getActualRule();
								if (R!=null)
									{
									AssertRule A= new AssertRule(((ButtonUser)event.getSource()).getHTML(),
											R.getRulePanel(),((ButtonUser)event.getSource()).getID(),
											Tiposids.User);
									R.addAssertRule(A);
									}
							}
						});
						AlumnosYProfesores.add(B);
						B.setSize("100%", "100%");
						B.setStyleName("gwt-ButtonTOP");
						B.addMouseOutHandler(new MouseOutHandler() {
							public void onMouseOut(MouseOutEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
							}
						});
						B.addMouseOverHandler(new MouseOverHandler() {
							public void onMouseOver(MouseOverEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
							}
						});
						B.addMouseDownHandler(new MouseDownHandler() {
							public void onMouseDown(MouseDownEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonPush");
							}
						});
						if (AlumnosYProfesores.getWidgetCount()>0)
						 {
							Widget W= AlumnosYProfesores.getWidget(AlumnosYProfesores.getWidgetCount()-1);
							Button B2=(Button)W;
							B2.setSize("100%", "100%");
							B2.setStyleName("gwt-ButtonBotton");
							B2.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
								}
							});
							B2.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
								}
							});
							B2.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
								}
							});
						 }
					}
					
					public void onFailure(Throwable caught) {
						Window.alert(ActualUser.getLanguage().getE_UserLoad());
						
					}
				});
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert(ActualUser.getLanguage().getE_UserLoad());
				
			}
		});
		
	}
	
	public static Rule getActualRule() {
		return ActualRule;
	}
	
	public static void setActualRule(Rule actualRule) {
		if (ActualRule!=null) ActualRule.setActual(false);
		ActualRule = actualRule;
		ActualRule.setActual(true);
	}
	
	public static VerticalPanel getRules() {
		return Rules;
	}
	
	public static void setRules(VerticalPanel rules) {
		Rules = rules;
	}
	
//	public static VerticalPanel getAnotacionesResultado() {
//		return AnotacionesResultado;
//	}
//	
//	public static void setAnotacionesResultado(
//			VerticalPanel anotacionesResultado) {
//		AnotacionesResultado = anotacionesResultado;
//	}
//	
}
