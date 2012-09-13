package lector.client.reader.filter.advance;

import java.util.ArrayList;
import java.util.Collection;


import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Constants;
import lector.client.login.ActualUser;
import lector.share.model.Annotation;
import lector.share.model.FileDB;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class Rule extends Composite {

	private VerticalPanel RulePanel;
	private String NameRule;
	private ArrayList<Long> TiposPos=new ArrayList<Long>();
	private ArrayList<Long> TiposNeg=new ArrayList<Long>();
	private ArrayList<Long> UsuariosPos=new ArrayList<Long>();
	private ArrayList<Long> UsuariosNeg=new ArrayList<Long>();
	private ArrayList<Annotation> ResultadoRule=new ArrayList<Annotation>();
	private ScrollPanel scrollPanel;
	private Rule Yo;
	private Button btnNewButton;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	

	public Rule(String Name) {
		
		NameRule=Name;
		Yo=this;
		SimplePanel decoratorPanel = new SimplePanel();
		initWidget(decoratorPanel);
		decoratorPanel.setSize("100%", "100%");
		
		DockPanel dockPanel = new DockPanel();
		decoratorPanel.setWidget(dockPanel);
		dockPanel.setSize("100%", "100%");
		
		FlowPanel flowPanel = new FlowPanel();
		dockPanel.add(flowPanel, DockPanel.NORTH);
		flowPanel.setSize("100%", "100%");
		
		btnNewButton = new Button(ActualUser.getLanguage().getRule()+" : " + NameRule);
		btnNewButton.setStyleName("gwt-ButtonBlack");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				scrollPanel.setVisible(!scrollPanel.isVisible());
				FilterAdvance.setActualRule(Yo);
				btnNewButton.setStyleName("gwt-ButtonIzquierdaSelect");
			}
		});
		flowPanel.add(btnNewButton);
		btnNewButton.setSize("80%", "100%");
		
		btnNewButton.setStyleName("gwt-ButtonIzquierda");
		
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaPush");
			}
		});
		
		Button btnNewButton_1 = new Button(ActualUser.getLanguage().getRemove());
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FilterAdvance.getRules().remove(Yo);
			}
		});
		flowPanel.add(btnNewButton_1);
		btnNewButton_1.setSize("20%", "100%");
		
		btnNewButton_1.setStyleName("gwt-ButtonDerecha");
		btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaPush");
			}
		});
		btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
			}
		});
		btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaOver");
			}
		});
		
		SimplePanel simplePanel = new SimplePanel();
		dockPanel.add(simplePanel, DockPanel.CENTER);
		simplePanel.setSize("100%", "100%");
		
		scrollPanel = new ScrollPanel();
		simplePanel.setWidget(scrollPanel);
		scrollPanel.setVisible(false);
		scrollPanel.setSize("100%", "100%");
		
		
		
		RulePanel = new VerticalPanel();
		RulePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		scrollPanel.setWidget(RulePanel);
		RulePanel.setSize("100%", "100%");
		
		
		
//		AssertRule AR=new AssertRule(RulePanel,0L,Tiposids.Tipo);
//		AR.setWidth("100%");
//		RulePanel.add(AR);
		
	}
	
	
	public void addAssertRule(AssertRule AR)
	{
		AR.setParental(RulePanel);
		RulePanel.add(AR);
		RulePanel.setSize("100%", "100");
	}
	
	public void evaluarReglas()
	{
		TiposPos=new ArrayList<Long>();
		TiposNeg=new ArrayList<Long>();
		UsuariosPos=new ArrayList<Long>();
		UsuariosNeg=new ArrayList<Long>();
		
		for (int i = 0; i < RulePanel.getWidgetCount(); i++) {
			AssertRule AR = (AssertRule) RulePanel.getWidget(i);
			if (AR.isStateImage())
				if (AR.getAssertName().getIdtipo()==Tiposids.Tipo)
					TiposPos.add(AR.getAssertName().getID());
				else UsuariosPos.add(AR.getAssertName().getID());
			else 
				if (AR.getAssertName().getIdtipo()==Tiposids.Tipo)
					TiposNeg.add(AR.getAssertName().getID());
				else UsuariosNeg.add(AR.getAssertName().getID());
		}
		
		
		bookReaderServiceHolder.getEntriesIdsByIdsRec(TiposPos, new AsyncCallback<ArrayList<FileDB>>() {
			

			public void onSuccess(ArrayList<FileDB> result) {
				ArrayList<Long> ListaAnot=listaAnotClear(result);
				
				
				AsyncCallback<ArrayList<Annotation>> callback=new AsyncCallback<ArrayList<Annotation>>() {
					
					public void onSuccess(ArrayList<Annotation> result) {
						ResultadoRule=new ArrayList<Annotation>();
						result=borrarTiposNegativos(result);
						result=borrarUsuariosNegativos(result);
						ArrayList<Annotation> Auxnot=FilterAsyncSystem.getAnotaciones();
						result=clearRep(result,Auxnot);
						Auxnot.addAll(result);
						FilterAsyncSystem.setAnotaciones(Auxnot);
						FilterAsyncSystem.nextRule();
					}
					
					

					private ArrayList<Annotation> clearRep(
							ArrayList<Annotation> result,
							ArrayList<Annotation> auxnot) {
						ArrayList<Annotation> Salida=new ArrayList<Annotation>();
						for (Annotation A1 : result) {
							if (!Esta(A1,auxnot))
								Salida.add(A1);
						}
						return Salida;
					}



					private boolean Esta(Annotation a1,
							ArrayList<Annotation> auxnot) {
						for (Annotation A2 : auxnot) {
							if (a1.getId().equals(A2.getId()))
								return true;
						}
						return false;
					}



					private ArrayList<Annotation> borrarUsuariosNegativos(ArrayList<Annotation> result) {
						ArrayList<Annotation> ResultadoAux = new ArrayList<Annotation>();
						for (Annotation annotation : result) {
							boolean esta=false;
							for (Long User : UsuariosNeg) {
								if (annotation.getUserId().equals(User)) 
									{
									esta=true;
									break;
									}
							}
							if (!esta) ResultadoAux.add(annotation);
							
						} 
						return ResultadoAux;
					}

					private ArrayList<Annotation> borrarTiposNegativos(ArrayList<Annotation> result) {
						ArrayList<Annotation> ResultadoAux = new ArrayList<Annotation>();
						for (Annotation annotation : result) {
							boolean esta=false;
							for (Long Type : TiposNeg) {
								for (Long  FileID : annotation.getFileIds()) {
									if (FileID.equals(Type)) 
									{
									esta=true;
									break;
									}	
								}
								if (esta) break;
								
							}
							if (!esta) ResultadoAux.add(annotation);
							
						} 
						return ResultadoAux;
					}
					
					public void onFailure(Throwable caught) {
						FilterAsyncSystem.nextRule();
						Window.alert(ActualUser.getLanguage().getE_filteringmesageAnnotations() + ActualUser.getLanguage().getRule()+" :" + NameRule  );
						
					}
				};
				
				if (ActualUser.getRol().equals(Constants.PROFESSOR)){
					bookReaderServiceHolder.getAnnotationsByIdsAndAuthorsTeacher(ListaAnot,UsuariosPos,ActualUser.getReadingactivity().getId(),callback);
					}else bookReaderServiceHolder.getAnnotationsByIdsAndAuthorsStudent(ListaAnot,UsuariosPos,ActualUser.getReadingactivity().getId(),ActualUser.getUser().getId(),callback );
				
				
				
				
			}
			
			private ArrayList<Long> listaAnotClear(ArrayList<FileDB> result) {
				ArrayList<Long> Salida=new ArrayList<Long>();
				if (!result.isEmpty())
				{			
				for (FileDB FileUni : result) {
					Salida=addSalida(Salida,FileUni);
				}
				}
				return Salida;
				

				
			}

			private ArrayList<Long> addSalida(
					ArrayList<Long> entrada, FileDB fileUni) {
				ArrayList<Long> Actual=fileUni.getAnnotationsIds();
				
				for (Long long1 : Actual) {
					if (!IsIn(long1,entrada)) entrada.add(long1); 
				}
				return entrada;
			}

			private boolean IsIn(Long long1, ArrayList<Long> entrada) {
				for (Long long2 : entrada) {
					if (long2.equals(long1)) return true;
				}
				return false;
			}

			public void onFailure(Throwable caught) {
				FilterAsyncSystem.nextRule();
				Window.alert(ActualUser.getLanguage().getE_filteringmesageAnnotations() + ActualUser.getLanguage().getRule()+" :" + NameRule  );
				
			}
		});
		
		
		
		
	}
	
	public ArrayList<Annotation> getResultadoRule() {
		return ResultadoRule;
	}
	
	
	public void setResultadoRule(ArrayList<Annotation> resultadoRule) {
		ResultadoRule = resultadoRule;
	}

	public void setActual(boolean estado)
	{
		scrollPanel.setVisible(estado);
		if (estado) btnNewButton.setStyleName("gwt-ButtonIzquierdaSelect");
		else btnNewButton.setStyleName("gwt-ButtonIzquierda");
		
	}
	
	public VerticalPanel getRulePanel() {
		return RulePanel;
	}
	
	public void setRulePanel(VerticalPanel rulePanel) {
		RulePanel = rulePanel;
	}
}
