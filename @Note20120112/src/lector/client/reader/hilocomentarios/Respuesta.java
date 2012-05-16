package lector.client.reader.hilocomentarios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.login.ActualUser;
import lector.client.reader.MainEntryPoint;
import lector.client.reader.SelectorPanel;
import lector.client.reader.TextSelector;
import lector.client.reader.annotthread.AnnotationThread;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class Respuesta extends Composite {

	
//	private RichTextArea richTextArea = new RichTextArea();
    private AnnotationThread annotation;
    private VerticalPanel verticalPanel = new VerticalPanel();
    private Button button_1 = new Button("+");
//    private RichTextArea richTextAreaBoton = new RichTextArea();
//    private SelectorPanel SE;
    private Button button;
    private final MenuBar menuBar = new MenuBar(false);
    private MenuItem mntmNewItem;
    private MenuItem mntmNewItem_1;
    private final MenuItemSeparator separator = new MenuItemSeparator();
    private MenuItem mntmNewItem_2;
    private ArrayList<TextSelector> TextSelectores;
    private ArrayList<SelectorPanel> SE;
    static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
    private ScrollPanel richTextArea2 = new ScrollPanel();
    private HTMLPanel panel;
    private DecoratorPanel decoratorPanel_1;
    
    
	public Respuesta(AnnotationThread annotationin, ArrayList<TextSelector> Selectoresin) {
		 annotation = annotationin;
		 TextSelectores=Selectoresin;
	        SimplePanel decoratorPanel = new SimplePanel();
	        decoratorPanel.setHeight("");
	        initWidget(decoratorPanel);

	        decoratorPanel.setWidget(verticalPanel);

	        final HorizontalPanel horizontalPanel = new HorizontalPanel();
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
					
					for(SelectorPanel SEE: SE)
					{
 
		            		 SEE.hide();
					}
				}
			});
	        button.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaOver");
					SE=new ArrayList<SelectorPanel>();
					for(TextSelector Select: TextSelectores)
					{

		            		 SelectorPanel SEE = new SelectorPanel(Select.getX().intValue(),
		            				 Select.getY().intValue(),
		                             MainEntryPoint.getOriginalBook().getAbsoluteLeft(), MainEntryPoint.getOriginalBook().getAbsoluteTop(),
		                             Select.getWidth().intValue(),
		                             Select.getHeight().intValue());
		            		 SEE.show();
		            		 SE.add(SEE);
					}
				}
			});

//	        richTextAreaBoton.addClickHandler(new ClickHandler() {
	//
//	            public void onClick(ClickEvent event) {
//	                TextComentEdit TCE = new TextComentEdit(annotation,SE);
//	                TCE.center();
	//
//	            }
//	        });
	//
//	        richTextAreaBoton.addMouseOutHandler(new MouseOutHandler() {
	//
//	            public void onMouseOut(MouseOutEvent event) {
//	            	if (!Estado){
//	            	if (SE != null) {
//	                    SE.hide();
//	                }
//	            	}
//	            }
//	        });
	//
//	        richTextAreaBoton.addMouseOverHandler(new MouseOverHandler() {
	//
//	            public void onMouseOver(MouseOverEvent event) {
//	            	if (!Estado){
//	            	if (SE != null) {
//	            					SE.hide();
//	            					}
//	                SE = new SelectorPanel(annotation.getTextSelector().getX().intValue(),
//	                        annotation.getTextSelector().getY().intValue(),
//	                        Imagen.getAbsoluteLeft(), Imagen.getAbsoluteTop(),
//	                        annotation.getTextSelector().getWidth().intValue(),
//	                        annotation.getTextSelector().getHeight().intValue());
//	                 SE.show();
//	                }
//	            }
//	        });
	//
//	        richTextAreaBoton.setHTML(annotation.getComment().toString());
//	        richTextAreaBoton.setSize("254px", "38px");
//	        horizontalPanel.add(richTextAreaBoton);
//	        richTextAreaBoton.setEnabled(false);
//	        richTextAreaBoton.setVisible(true);


	        button_1.addClickHandler(new ClickHandler() {

	            public void onClick(ClickEvent event) {
	                if (button_1.getText().contentEquals("+")) {
	                    //verticalPanel.add(richTextArea);
	                	decoratorPanel_1.setVisible(true);
	                    menuBar.setVisible(true);
	                    if (panel.getOffsetHeight()>174)
	                    {
	                    	richTextArea2.setHeight("174px");
	                    //	Window.alert("Tamaño reducido");
	                    }
	                  //  button.setVisible(true);
//	                    richTextAreaBoton.setVisible(false);
	                    button_1.setText("-");
	                } else {
	                    // verticalPanel.remove(richTextArea);
	                	decoratorPanel_1.setVisible(false);
	                   // button.setVisible(false);
//	                    richTextAreaBoton.setVisible(true);
//	                    richTextAreaBoton.setSize("254px", "38px");
	                    button_1.setText("+");
	                    menuBar.setVisible(false);
//	                    horizontalPanel.clear();
//	                    horizontalPanel.add(button);
////	                    horizontalPanel.add(richTextAreaBoton);
//	                    horizontalPanel.add(button_1);

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
	        button_1.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
				}
			});
	        button_1.setSize("52px", "30px");
	        
	        decoratorPanel_1 = new DecoratorPanel();
	        verticalPanel.add(decoratorPanel_1);
	        decoratorPanel_1.setWidth("");
	        decoratorPanel_1.setVisible(false);
//	        richTextArea.setHTML(annotation.getComment().toString());
//	        richTextArea.setHeight("174px");
//	        verticalPanel.add(richTextArea);
//	        richTextArea.setEnabled(false);
	        
	        
	        richTextArea2 = new ScrollPanel();
	        decoratorPanel_1.setWidget(richTextArea2);
	        panel = new HTMLPanel(annotation.getComment().toString());
	        richTextArea2.setSize("296px", "100%");
	        
	        
	        richTextArea2.setWidget(panel);
	        panel.setSize("100%", "100%");
	        decoratorPanel_1.setVisible(false);
	        
	        verticalPanel.add(menuBar);
	        menuBar.setWidth("306px");
	        
	        mntmNewItem = new MenuItem("New item", false, new Command() {
	        	public void execute() {
	        	ReplyDialog RD=new ReplyDialog(annotation.getId(), annotation.getAnnotationId());
	        	RD.center();
	        	}
	        });
	        mntmNewItem.setHTML("Reply");
	        menuBar.addItem(mntmNewItem);
	        
	        mntmNewItem_1 = new MenuItem("New item", false, new Command() {
	        	public void execute() {
	        		bookReaderServiceHolder.deleteAnnotationThread(annotation.getId(), new AsyncCallback<Void>() {
						
						public void onSuccess(Void result) {
							MainEntryPoint.refreshP();
							
						}
						
						public void onFailure(Throwable caught) {
							Window.alert(ActualUser.getLanguage().getE_DeleteReply());
							//TODO Error
							
						}
					});
	        	}
	        });
	        menuBar.setVisible(false);
	       if (!ActualUser.getUser().getId().equals(annotation.getUserId()))  mntmNewItem_1.setEnabled(false);
	        mntmNewItem_1.setHTML("Delete");
	        menuBar.addItem(mntmNewItem_1);
	        
	        menuBar.addSeparator(separator);
	        
	        mntmNewItem_2 = new MenuItem("New item", false, (Command) null);
	        mntmNewItem_2.setStyleName("gwt-MenuItemPanel");
	        menuBar.addItem(mntmNewItem_2);
	       
	        
	      
	       mntmNewItem_2.setText(annotation.getUserName() + " --- " +DateTimeFormat.getShortDateFormat().format(annotation.getCreatedDate()));





	    }
	    
	    
//	    public void hideSelectorPanel() {
//			SE.hide();
//		}

}
