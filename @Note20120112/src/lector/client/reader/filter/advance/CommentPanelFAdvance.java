package lector.client.reader.filter.advance;

import lector.client.reader.Annotation;
import lector.client.reader.SelectorPanel;
import lector.client.reader.hilocomentarios.ReplyDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class CommentPanelFAdvance extends Composite {

   // private RichTextArea richTextArea = new RichTextArea();
    private Annotation annotation;
    private VerticalPanel verticalPanel = new VerticalPanel();
    private Button button_1 = new Button("+");
    private Image Imagen;
    private Button button;
    private final MenuBar menuBar = new MenuBar(false);
//	private MenuItem mntmNewItem;
	private MenuItem mntmNewItem_2;
	private FocusPanel richTextArea2 = new FocusPanel();
	private ScrollPanel scrollPanel = new ScrollPanel();
	private HorizontalPanel Ocultador = new HorizontalPanel();
	private HTMLPanel panel = new HTMLPanel("New HTML");
    

    public CommentPanelFAdvance(Annotation annotationin, Image originalBook,String width) {

        annotation = annotationin;
        Imagen = originalBook;
        SimplePanel decoratorPanel = new SimplePanel();
        decoratorPanel.setSize("100%", "");
        initWidget(decoratorPanel);

        decoratorPanel.setWidget(verticalPanel);
        verticalPanel.setWidth("100%");

        final HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        verticalPanel.add(horizontalPanel);
        horizontalPanel.setWidth("");
        
      

        String Showbutton= annotation.getComment().toString();
        Showbutton=Showbutton.replaceAll("\\<.*?\\>","");
        
        if (Showbutton.length()>20){
        	Showbutton=Showbutton.substring(0,20);
        	Showbutton=Showbutton+" ...";
        }else{
       	 while (Showbutton.length()<24) Showbutton=Showbutton+" ";
        }
//        button.addMouseOutHandler(new MouseOutHandler() {
//			public void onMouseOut(MouseOutEvent event) {
//				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierda");
//			}
//		});
//        button.addMouseOverHandler(new MouseOverHandler() {
//			public void onMouseOver(MouseOverEvent event) {
//				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaOver");
//			}
//		});
//        button.addMouseDownHandler(new MouseDownHandler() {
//			public void onMouseDown(MouseDownEvent event) {
//				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaPush");
//			}
//		});


        button = new Button(Showbutton);
        horizontalPanel.add(button);
        button.setHTML(Showbutton);
        button.setEnabled(true);
        button.setSize("400px", "42px");
        button.setStyleName("gwt-ButtonIzquierda");
        button_1.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                if (button_1.getText().contentEquals("+")) {
                    //verticalPanel.add(richTextArea);
                	Ocultador.setVisible(true);
                    menuBar.setVisible(true);
                    if (panel.getOffsetHeight()>174)
	                    {
                    	 scrollPanel.setHeight("174px");
	                    //	Window.alert("Tamaño reducido");
	                    }
                  //  button.setVisible(true);
//                    richTextAreaBoton.setVisible(false);
                    button_1.setText("-");
                } else {
                    // verticalPanel.remove(richTextArea);
                	Ocultador.setVisible(false);
                   // button.setVisible(false);
//                    richTextAreaBoton.setVisible(true);
//                    richTextAreaBoton.setSize("254px", "38px");
                    button_1.setText("+");
                    menuBar.setVisible(false);
//                    horizontalPanel.clear();
//                    horizontalPanel.add(button);
////                    horizontalPanel.add(richTextAreaBoton);
//                    horizontalPanel.add(button_1);

                }
            }
        });
        horizontalPanel.add(button_1);
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
        button_1.setSize("47px", "42px");

        

//        richTextArea.setHTML(annotation.getComment().toString());
//        richTextArea.setSize("99%", "174px");
//        verticalPanel.add(richTextArea);
//        richTextArea.setEnabled(false);
//        richTextArea.setVisible(false);
        
        richTextArea2 = new FocusPanel();
    	scrollPanel = new ScrollPanel();
    	Ocultador = new HorizontalPanel();
    	panel = new HTMLPanel(annotation.getComment().toString());
    	
    	
        richTextArea2.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
            	  VisualBookPanelFilterAdvance TCE = new VisualBookPanelFilterAdvance(annotation,Imagen);
                  TCE.show();
            }
        });
 Ocultador.setBorderWidth(1);
 
 verticalPanel.add(Ocultador);
 Ocultador.setSize("100%", "100%");
 Ocultador.add(richTextArea2);
 richTextArea2.setSize("100%", "100%");
 Ocultador.setVisible(false);
 
 richTextArea2.setWidget(scrollPanel);
 scrollPanel.setSize(width, "100%");
 
 scrollPanel.setWidget(panel);
 panel.setSize("100%", "100%");

 verticalPanel.add(menuBar);
        
        
        
//        mntmNewItem_1 = new MenuItem("New item", false, new Command() {
//        	public void execute() {
//        		Window.alert("Borrar");
//        	}
//        });
        menuBar.setVisible(false);
//        mntmNewItem_1.setEnabled(false);
//        mntmNewItem_1.setHTML("Delete");
        
        
        
        
        mntmNewItem_2 = new MenuItem("New item", false, (Command) null);
        menuBar.addItem(mntmNewItem_2);
       
        

//tocado        
       mntmNewItem_2.setText(annotation.getUserName() + " --- " +DateTimeFormat.getShortDateFormat().format(annotation.getCreatedDate()));
//        richTextArea.setVisible(false);



    }
    

}
