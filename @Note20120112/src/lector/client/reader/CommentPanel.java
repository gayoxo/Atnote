package lector.client.reader;



import java.text.SimpleDateFormat;
import java.util.ArrayList;

import lector.client.controler.Constants;
import lector.client.reader.hilocomentarios.ReplyDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
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
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.SimplePanel;

public class CommentPanel extends Composite {

    private RichTextArea richTextArea = new RichTextArea();
    private Annotation annotation;
    private VerticalPanel verticalPanel = new VerticalPanel();
    private Button button_1 = new Button("+");
 //   private RichTextArea richTextAreaBoton = new RichTextArea();
    private ArrayList<SelectorPanel> SE;
    private final MenuBar menuBar = new MenuBar(false);
    private MenuItem mntmNewItem;
  //  private MenuItem mntmNewItem_1;
    private final MenuItemSeparator separator = new MenuItemSeparator();
    private MenuItem mntmNewItem_2;
    private Image Imagen;
    private Button button;
    private static boolean Estado;
    

    public CommentPanel(Annotation annotationin, Image originalBook) {

        annotation = annotationin;
        Imagen = originalBook;
        SimplePanel decoratorPanel = new SimplePanel();
        decoratorPanel.setHeight("");
        initWidget(decoratorPanel);

        decoratorPanel.setWidget(verticalPanel);

        final HorizontalPanel horizontalPanel = new HorizontalPanel();
        verticalPanel.add(horizontalPanel);
        horizontalPanel.setHeight("28px");

        String Showbutton= annotation.getComment().toString();
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
        button.addMouseOutHandler(new MouseOutHandler() {

            public void onMouseOut(MouseOutEvent event) {
                if (SE != null) {
                	for (SelectorPanel SP : SE) {
                		SP.hide();
					}
                }
            }
        });
        button.addMouseOverHandler(new MouseOverHandler() {

            public void onMouseOver(MouseOverEvent event) {
            	MainEntryPoint.hidePopUpSelector();
				MainEntryPoint.hideDENSelector();
            	 if (SE != null) {
                 	for (SelectorPanel SP : SE) {
                 		SP.hide();
 					}
                 }
            	 SE=new ArrayList<SelectorPanel>();
            	 for (TextSelector TS : annotation.getTextSelectors()) {
            		 SelectorPanel SEE = new SelectorPanel(TS.getX().intValue(),
            				 TS.getY().intValue(),
                             Imagen.getAbsoluteLeft(), Imagen.getAbsoluteTop(),
                             TS.getWidth().intValue(),
                             TS.getHeight().intValue());
                     if (!Estado) SEE.show();
                     SE.add(SEE);
				}
               
            }
        });
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

        horizontalPanel.add(button);
//        button.setEnabled(true);
//        button.setVisible(false);
//        button.setSize("254px", "42px");

        richTextArea.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
            	MainEntryPoint.hidePopUpSelector();
				MainEntryPoint.hideDENSelector();
                if (annotation.isEditable())
                	{
                	TextComentEdit TCE = new TextComentEdit(annotation,SE);
                	TCE.center();
                	}
                else {
                	TextComentNoEdit TCE= new TextComentNoEdit(annotation, SE);
                	TCE.center();
                }
                

            }
        });

        richTextArea.addMouseOutHandler(new MouseOutHandler() {

            public void onMouseOut(MouseOutEvent event) {
            	if (!Estado){
            		 if (SE != null) {
                      	for (SelectorPanel SP : SE) {
                      		SP.hide();
      					}
                      }
            	}
            }
        });

        richTextArea.addMouseOverHandler(new MouseOverHandler() {

            public void onMouseOver(MouseOverEvent event) {
            	if (!Estado){
            		 if (SE != null) {
                      	for (SelectorPanel SP : SE) {
                      		SP.hide();
      					}
                      }
            		 SE=new ArrayList<SelectorPanel>();
                	 for (TextSelector TS : annotation.getTextSelectors()) {
                		 SelectorPanel SEE = new SelectorPanel(TS.getX().intValue(),
                				 TS.getY().intValue(),
                                 Imagen.getAbsoluteLeft(), Imagen.getAbsoluteTop(),
                                 TS.getWidth().intValue(),
                                 TS.getHeight().intValue());
                         if (!Estado) SEE.show();
                         SE.add(SEE);
    				}
                }
            }
        });
//
//        richTextAreaBoton.setHTML(annotation.getComment().toString());
//        richTextAreaBoton.setSize("254px", "38px");
//        horizontalPanel.add(richTextAreaBoton);
//        richTextAreaBoton.setEnabled(false);
//        richTextAreaBoton.setVisible(true);


        button_1.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                if (button_1.getText().contentEquals("+")) {
                    //verticalPanel.add(richTextArea);
                    richTextArea.setVisible(true);
                    menuBar.setVisible(true);
                  //  button.setVisible(true);
//                    richTextAreaBoton.setVisible(false);
                    button_1.setText("-");
                } else {
                    // verticalPanel.remove(richTextArea);
                    richTextArea.setVisible(false);
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

        richTextArea.setHTML(annotation.getComment().toString());
        richTextArea.setHeight("174px");
        verticalPanel.add(richTextArea);
        richTextArea.setEnabled(false);
        
        verticalPanel.add(menuBar);
        
        mntmNewItem = new MenuItem("New item", false, new Command() {
        	public void execute() {
        	ReplyDialog RD=new ReplyDialog(Constants.THREADFATHERNULLID, annotation.getId());
        	RD.center();
        	}
        });
        mntmNewItem.setHTML("Reply");
        menuBar.addItem(mntmNewItem);
        
//        mntmNewItem_1 = new MenuItem("New item", false, new Command() {
//        	public void execute() {
//        		Window.alert("Borrar");
//        	}
//        });
        menuBar.setVisible(false);
//        mntmNewItem_1.setEnabled(false);
//        mntmNewItem_1.setHTML("Delete");
//        menuBar.addItem(mntmNewItem_1);
        
        menuBar.addSeparator(separator);
        
        mntmNewItem_2 = new MenuItem("New item", false, (Command) null);
        mntmNewItem_2.setStyleName("gwt-MenuItemPanel");
        menuBar.addItem(mntmNewItem_2);
       
        

//tocado        
       mntmNewItem_2.setText(annotation.getUserName() + " --- " +DateTimeFormat.getShortDateFormat().format(annotation.getCreatedDate()));
        richTextArea.setVisible(false);





    }
    
    public static void setEstado(boolean estado) {
		Estado = estado;
	}
    
    public void hideSelectorPanel() {
		for (SelectorPanel SP : SE) {
			SP.hide();
		}
    	
	}
}
