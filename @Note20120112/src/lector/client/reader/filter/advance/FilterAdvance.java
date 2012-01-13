package lector.client.reader.filter.advance;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.browser.BotonesStackPanelBrowser;
import lector.client.catalogo.Finder2;
import lector.client.controler.Controlador;
import lector.client.login.ActualUser;
import lector.client.login.UserApp;
import lector.client.reader.MainEntryPoint;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;

public class FilterAdvance implements EntryPoint{

	private static Rule ActualRule=null;
	private static VerticalPanel Rules;
	private VerticalPanel AlumnosYProfesores;
	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private Finder2 FinderButton2;
	private Finder2 FinderButton1;
	private static VerticalPanel AnotacionesResultado;
	
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
		SplitLayoutPanel General = new SplitLayoutPanel();
		rootPanel.add(General,0,25);
		General.setSize("100%", "96%");
		
		ScrollPanel scrollPanel_1 = new ScrollPanel();
		General.addEast(scrollPanel_1, 400.0);
		
		AnotacionesResultado = new VerticalPanel();
		scrollPanel_1.setWidget(AnotacionesResultado);
		AnotacionesResultado.setSize("100%", "100%");
		
		SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel();
		General.addWest(splitLayoutPanel, 400.0);
		
		
		SimplePanel Catalogo2 = new SimplePanel();
		splitLayoutPanel.addNorth(Catalogo2, 200.0);
		FinderButton2 = new Finder2();
		Catalogo2.add(FinderButton2);
		FinderButton2.setButtonTipo(new BotonesStackPanelBrowser(
				"prototipo", new VerticalPanel(), new VerticalPanel()));
		FinderButton2.setBotonClick(new ClickHandler() {

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
		
		FinderButton2.setSize("100%", "100%");
		
		
		FinderButton2.setCatalogo(ActualUser.getCatalogo());
		Catalogo2.setSize("100%", "100%");
		
		SimplePanel decoratorPanel = new SimplePanel();
		splitLayoutPanel.addSouth(decoratorPanel, 200.0);
		decoratorPanel.setSize("100%", "100%");
		
		ScrollPanel scrollPanel_3 = new ScrollPanel();
		decoratorPanel.setWidget(scrollPanel_3);
		scrollPanel_3.setSize("100%", "100%");
		
		AlumnosYProfesores = new VerticalPanel();
		scrollPanel_3.setWidget(AlumnosYProfesores);
		AlumnosYProfesores.setSize("100%", "100%");
		
		SimplePanel Catalogo1 = new SimplePanel();
		splitLayoutPanel.add(Catalogo1);
		Catalogo1.setSize("100%", "100%");
		
		
		FinderButton1 = new Finder2();
		Catalogo1.add(FinderButton1);
		FinderButton1.setButtonTipo(new BotonesStackPanelBrowser(
				"prototipo", new VerticalPanel(), new VerticalPanel()));
		FinderButton1.setBotonClick(new ClickHandler() {

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
		
		FinderButton1.setSize("100%", "100%");
		FinderButton1.setCatalogo(ActualUser.getOpenCatalog());
		
		ScrollPanel scrollPanel = new ScrollPanel();
		General.add(scrollPanel);
		scrollPanel.setSize("100%", "100%");
		
		scrollPanel.setWidget(Rules);
		
		bookReaderServiceHolder.getUsersByGroupId(ActualUser.getReadingactivity().getGroupId(), new AsyncCallback<ArrayList<UserApp>>() {
			
			public void onSuccess(ArrayList<UserApp> result) {
				for (UserApp User : result) {
					ButtonUser B=new ButtonUser(User.getEmail(),User.getId());
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
				}
				bookReaderServiceHolder.loadUserById(ActualUser.getReadingactivity().getProfessorId(), new AsyncCallback<UserApp>() {
					
					public void onSuccess(UserApp result) {
						ButtonUser B=new ButtonUser(result.getEmail(),result.getId());
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
	
	public static VerticalPanel getAnotacionesResultado() {
		return AnotacionesResultado;
	}
	
	public static void setAnotacionesResultado(
			VerticalPanel anotacionesResultado) {
		AnotacionesResultado = anotacionesResultado;
	}
	
}
