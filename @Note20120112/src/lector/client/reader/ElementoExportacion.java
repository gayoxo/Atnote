package lector.client.reader;

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

public class ElementoExportacion extends Composite{

	private Annotation Annotation;
	private Image Imagen;
	private Button button;
	private Button button_1;
	private Button UP;
	private Button Down;
	private VerticalPanel VerticalPanel;
	private ElementoExportacion yo;
	
	public ElementoExportacion(Annotation annotation, Image imagen) {
		Annotation=annotation;
		Imagen=imagen;
		yo=this;
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		 final HorizontalPanel horizontalPanel = new HorizontalPanel();
		 horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		 horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	        verticalPanel.add(horizontalPanel);
	        horizontalPanel.setHeight("28px");

	        String Showbutton= annotation.getComment().toString();
	       
	        Showbutton=Showbutton.replaceAll("\\<.*?\\>","");
	        if (Showbutton.length()>20){
	        	Showbutton=Showbutton.substring(0,20);
	        	Showbutton=Showbutton+" ...";
	        }  
		 button = new Button(Showbutton);
	       button.setHTML(Showbutton);
	        horizontalPanel.add(button);
	        button.setEnabled(true);
	        button.setVisible(true);
	        button.setSize("254px", "30px");
	        button.setStyleName("gwt-ButtonIzquierda");
	        button.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
				}
			});
	        button.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaOver");
				}
			});
	        button.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaPush");
				}
			});
	        button.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
				}
			});

	        horizontalPanel.add(button);


	        button_1 = new Button("X");
	        button_1.setStyleName("gwt-ButtonDerecha");
	        button_1.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
				}
			});
	        button_1.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonDerechaOver");
				}
			});
	        button_1.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonDerechaPush");
				}
			});
	        button_1.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
				}
			});
	        horizontalPanel.add(button_1);
	        button_1.setSize("52px", "30px");
	        
	        Down = new Button("DOWN");
	        horizontalPanel.add(Down);
	        Down.setWidth("106px");
	        Down.setStyleName("gwt-ButtonIzquierda");
	        Down.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
				}
			});
	        Down.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaOver");
				}
			});
	        Down.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaPush");
				}
			});
	        Down.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
				}
			});
	        
	        UP = new Button("UP");
	        horizontalPanel.add(UP);
	        UP.setWidth("63px");
	        UP.setStyleName("gwt-ButtonDerecha");
	        UP.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
				}
			});
	        UP.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonDerechaOver");
				}
			});
	        UP.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonDerechaPush");
				}
			});
	        UP.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
				}
			});
	}
	
	public void addCliker(VerticalPanel verticalPanel) {
		
		VerticalPanel=verticalPanel;
		button_1.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
//        		VerticalPanel.remove(yo);
        		yo.removeFromParent();
        	}
        });
		
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
	
	public Annotation getAnnotation() {
		return Annotation;
	}
	
	public Image getImagen() {
		return Imagen;
	}

}