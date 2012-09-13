package lector.client.reader;

import java.util.ArrayList;

import lector.share.model.TemplateCategory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class ElementoExportacionTemplate extends Composite{

	private TemplateCategory Template;
	private int Profundidad;
	private Button button;
	private Button UP;
	private Button Down;
	private VerticalPanel VerticalPanel;
	private ElementoExportacionTemplate yo;
	private VerticalPanel Fondo;
	private SimplePanel simplePanel;
	private HorizontalPanel horizontalPanel_1;
	private boolean Editable;
	private Button Glue;
	private boolean Seleccionado;
	
	public ElementoExportacionTemplate(TemplateCategory Templatein, int Profundidadin,boolean isEtiable) {
		Template=Templatein;
		Profundidad=Profundidadin;
		yo=this;
		Seleccionado=false;
		Editable=isEtiable;
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		 final HorizontalPanel horizontalPanel = new HorizontalPanel();
		 horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		 horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	        verticalPanel.add(horizontalPanel);
	        horizontalPanel.setHeight("28px");

	        button = new Button(Template.getName());
	        button.addClickHandler(new ClickHandler() {
	        	public void onClick(ClickEvent event) {
	        		button.setStyleName("gwt-ButtonIzquierda");
	        		PopUPEXportacion.setActual(yo);
	        	}
	        });
	       button.setHTML(Template.getName());
	        horizontalPanel.add(button);
	        button.setEnabled(true);
	        button.setVisible(true);
	        button.setSize("254px", "30px");
	        button.setStyleName("gwt-ButtonIzquierda");
	        button.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					if (!Seleccionado) 
						((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
				}
			});
	        button.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
				if (!Seleccionado) 
					((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaOver");
				}
			});
	        button.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					if (!Seleccionado) 
					((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaPush");
				}
			});
	        button.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					if (!Seleccionado) 
					((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
				}
			});

	        horizontalPanel.add(button);
	        
	        Glue = new Button(" ");
	        horizontalPanel.add(Glue);
	        Glue.setSize("50px", "30px");
	        Glue.setStyleName("gwt-ButtonCenterContinuoEnd");
	        Glue.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoEnd");
				}
			});
	        Glue.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoEndOver");
				}
			});
	        Glue.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoEndPush");
				}
			});
	        Glue.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoEnd");
				}
			});
	        
	        
	        UP = new Button("UP");
	        horizontalPanel.add(UP);
	        horizontalPanel.setCellVerticalAlignment(UP, HasVerticalAlignment.ALIGN_MIDDLE);
	        horizontalPanel.setCellHorizontalAlignment(UP, HasHorizontalAlignment.ALIGN_CENTER);
	        UP.setHTML("<img src=\"/BotonesTemplate/Arriba.gif\" alt=\"UP\">");
	        UP.setSize("50px", "30px");
	        UP.setStyleName("gwt-ButtonCenterContinuo");
	        UP.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuo");
				}
			});
	        UP.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoOver");
				}
			});
	        UP.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoPush");
				}
			});
	        UP.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuo");
				}
			});
	        
	        Down = new Button("DOWN");
	        horizontalPanel.add(Down);
	        horizontalPanel.setCellHorizontalAlignment(Down, HasHorizontalAlignment.ALIGN_CENTER);
	        horizontalPanel.setCellVerticalAlignment(Down, HasVerticalAlignment.ALIGN_MIDDLE);
	        Down.setHTML("<img src=\"/BotonesTemplate/Abajo.gif\" alt=\"<-\">");
	        Down.setSize("50px", "30px");
	        Down.setStyleName("gwt-ButtonCenterContinuoEnd");
	        Down.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoEnd");
				}
			});
	        Down.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoEndOver");
				}
			});
	        Down.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoEndPush");
				}
			});
	        Down.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoEnd");
				}
			});
	        
	        horizontalPanel_1 = new HorizontalPanel();
	        verticalPanel.add(horizontalPanel_1);
	        horizontalPanel_1.setWidth("");
	        
	        simplePanel = new SimplePanel();
	        horizontalPanel_1.add(simplePanel);
	        simplePanel.setWidth("31px");
	        
	        Fondo = new VerticalPanel();
	        horizontalPanel_1.add(Fondo);
	        Fondo.setSize("", "100%");
	        
	        if (!Editable)
	        	{
	        	UP.setVisible(false);
	        	Down.setVisible(false);
	        	
	        	}
	        else Glue.setVisible(false);
	}
	
	public void addCliker(VerticalPanel verticalPanel) {
		
		VerticalPanel=verticalPanel;
				
		UP.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {

        	for (int i = 0; i < VerticalPanel.getWidgetCount(); i++) {
				if (VerticalPanel.getWidget(i)==yo)
					{
					if (i>0)
						{
						yo.removeFromParent();
						VerticalPanel.insert(yo,i-1);
						}
					}
			}
        	}
        });
		
		Down.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		for (int i = 0; i < VerticalPanel.getWidgetCount(); i++) {
    				if (VerticalPanel.getWidget(i)==yo)
    					{
    					if (i+1 < VerticalPanel.getWidgetCount())
    						{
    						yo.removeFromParent();
    						VerticalPanel.insert(yo,i+1);
    						}
    					}
    			}
        	}
        });
		
	}
	
public int getProfundidad() {
	return Profundidad;
}

public TemplateCategory getTemplate() {
	return Template;
}

public void addSon(ElementoExportacionTemplate Hijo) {
	Fondo.add(Hijo);
	Hijo.addCliker(Fondo);
	
}

public boolean isEditable() {
	return Editable;
}

public VerticalPanel getFondo() {
	return Fondo;
}

public void ResetButton()
{
	button.setStyleName("gwt-ButtonIzquierda");	
	Seleccionado=false;
}

public void selectedButton()
{
	button.setStyleName("gwt-ButtonIzquierdaSelect");	
	Seleccionado=true;
}

public ArrayList<ExportObject> Export(ArrayList<ExportObject> entrada)
{
	for (int i = 0; i < Fondo.getWidgetCount(); i++) {
		Widget W=Fondo.getWidget(i);
		if (W instanceof ElementoExportacionTemplate)
			{
			ElementoExportacionTemplate EET=(ElementoExportacionTemplate)W;
			entrada.add(new ExportObjectTemplate(EET.getProfundidad(), EET.getTExt()));
			EET.Export(entrada);
			}
		else if (W instanceof ElementoExportacion)
			{
			ElementoExportacion EE=(ElementoExportacion)W;
			entrada.add(new ExportObject(EE.getAnnotation(), 
										 EE.getImagen().getUrl(),
										 EE.getImagen().getWidth(),
										 EE.getImagen().getHeight(),
										 EE.getAnnotation().getUserName(),
										 EE.getAnnotation().getCreatedDate().toGMTString()));
			}
	}
return entrada;
}

public String getTExt()
{
	return Template.getName();
}
}
