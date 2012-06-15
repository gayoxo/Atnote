package lector.client.admin.export.admin;


import lector.client.admin.export.template.Template;
import lector.client.admin.export.template.TemplateCategory;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.controler.ErrorConstants;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.SimplePanel;

public class RepresentacionTemplateCategory extends Composite {

	private TemplateCategory T;
	private VerticalPanelTemplate AnnotPanel;
	private RepresentacionTemplateCategory Father;
	private ButtonTemplateRep BotonSelect;
	private boolean Selected;
	private RepresentacionTemplateCategory YO;
	private ExportServiceAsync exportServiceHolder = GWT
	.create(ExportService.class);
	
	public RepresentacionTemplateCategory(TemplateCategory t,RepresentacionTemplateCategory father) {
		
		T=t;
		Father=father;
		Selected=false;
		YO=this;
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setHeight("100%");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setHeight("49px");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setSize("100%", "100%");
		
		Button btnNewButton_1 = new Button("<-");
		horizontalPanel_1.add(btnNewButton_1);
		btnNewButton_1.setSize("100%", "100%");
		btnNewButton_1.setStyleName("gwt-ButtonIzquierda");
		btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
			}
		});
		btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaOver");
			}
		});
		btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaPush");
			}
		});
		btnNewButton_1.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
			}
		});
		btnNewButton_1.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				if (YO.getParent() instanceof VerticalPanelTemplate)
				{			
					RepresentacionTemplateCategory Padreact=((VerticalPanelTemplate) YO.getParent()).getFatherObject();
					if (Padreact.getParent() instanceof VerticalPanelTemplate)
					{			
						RepresentacionTemplateCategory Padrenew=((VerticalPanelTemplate) Padreact.getParent()).getFatherObject();
						
						exportServiceHolder.moveCategory(Padreact.getT().getId(), Padrenew.getT().getId(), YO.getT().getId(), YO.getT().getTemplateId(), new AsyncCallback<Void>() {
							
							public void onSuccess(Void result) {
								EditTemplate.getPGT().refresh();
								
							}
							
							public void onFailure(Throwable caught) {
								Window.alert(ErrorConstants.ERROR_ON_MOVE_CATEGORY_PROMOTING);	
								
							}
						});
						
					}else
					{
					Window.alert(ErrorConstants.ERROR_TOP_LEVEL_TEMPLATECATEGORY);	
					}
					
				}else
				{
				Window.alert(ErrorConstants.ERROR_THIS_IS_A_TEMPLATE);	
				}
				
			}
		});
        
		
		Button btnNewButton_2 = new Button("->");
		horizontalPanel_1.add(btnNewButton_2);
		btnNewButton_2.setSize("100%", "100%");
		btnNewButton_2.setStyleName("gwt-ButtonDerecha");
		btnNewButton_2.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
			}
		});
		btnNewButton_2.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaOver");
			}
		});
		btnNewButton_2.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaPush");
			}
		});
		btnNewButton_2.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
			}
		});
		btnNewButton_2.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				if (YO.getParent() instanceof VerticalPanelTemplate)
				{			
					RepresentacionTemplateCategory Padreact=((VerticalPanelTemplate) YO.getParent()).getFatherObject();
					//Borrar del padre actualizar alturas
					
				}else
				{
				Window.alert(ErrorConstants.ERROR_THIS_IS_A_TEMPLATE);	
				}
				
			}
		});
		
		BotonSelect = new ButtonTemplateRep(T.getName(),this);
	
		horizontalPanel.add(BotonSelect);
		BotonSelect.setSize("77px", "49px");
		
		
		BotonSelect.setStyleName("gwt-ButtonCenter");
		BotonSelect.setSize("100%", "100%");

		BotonSelect.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				if (!Selected)
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		
		BotonSelect.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!Selected)
					((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
		});
		
		BotonSelect.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				if (!Selected)
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel.add(horizontalPanel_2);
		horizontalPanel_2.setHeight("100%");
		
		Button btnNewButton_3 = new Button("UP");
		horizontalPanel_2.add(btnNewButton_3);
		btnNewButton_3.setSize("100%", "100%");
		btnNewButton_3.setStyleName("gwt-ButtonIzquierda");
		btnNewButton_3.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
			}
		});
		btnNewButton_3.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaOver");
			}
		});
		btnNewButton_3.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaPush");
			}
		});
		btnNewButton_3.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
			}
		});
		btnNewButton_3.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				if (YO.getParent() instanceof VerticalPanelTemplate)
				{			
					RepresentacionTemplateCategory Padreact=((VerticalPanelTemplate) YO.getParent()).getFatherObject();
					//Mover los Pesos salvar los dos
					
				}else
				{
				Window.alert(ErrorConstants.ERROR_THIS_IS_A_TEMPLATE);	
				}
				
			}
		});
		
		
		Button btnNewButton_4 = new Button("DOWN");
		horizontalPanel_2.add(btnNewButton_4);
		btnNewButton_4.setSize("100%", "100%");
		btnNewButton_4.setStyleName("gwt-ButtonDerecha");
		btnNewButton_4.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
			}
		});
		btnNewButton_4.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaOver");
			}
		});
		btnNewButton_4.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaPush");
			}
		});
		btnNewButton_4.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
			}
		});
		btnNewButton_4.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				if (YO.getParent() instanceof VerticalPanelTemplate)
				{			
					RepresentacionTemplateCategory Padreact=((VerticalPanelTemplate) YO.getParent()).getFatherObject();
					//Mover los Pesos salvar los dos
					
				}else
				{
				Window.alert(ErrorConstants.ERROR_THIS_IS_A_TEMPLATE);	
				}
				
			}
		});
		
		
		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_3);
		
		SimplePanel simplePanel = new SimplePanel();
		horizontalPanel_3.add(simplePanel);
		simplePanel.setWidth("35px");
		
		AnnotPanel = new VerticalPanelTemplate(YO);
		horizontalPanel_3.add(AnnotPanel);
		AnnotPanel.setWidth("100%");
		
	}

	
	public TemplateCategory getT() {
		return T;
	}


	public void addSon(RepresentacionTemplateCategory nuevo) {
		AnnotPanel.add(nuevo);
		
	}


	public void setclickHandel(ClickHandler clickHandler) {
		BotonSelect.addClickHandler(clickHandler);
		
	}


	public void clear() {
		AnnotPanel.clear();
		
	}
	
	public boolean isSelected() {
		return Selected;
	}
	
	public ButtonTemplateRep getBotonSelect() {
		return BotonSelect;
	}
	
	public void setSelected(boolean selected) {
		Selected = selected;
	}
	
	public RepresentacionTemplateCategory getFather() {
		return Father;
	}
	
	public VerticalPanelTemplate getAnnotPanel() {
		return AnnotPanel;
	}
}
