package lector.client.reader;



import java.text.SimpleDateFormat;

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
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;

public class CommentPanel extends Composite {

    private RichTextArea richTextArea = new RichTextArea();
    private Annotation annotation;
    private VerticalPanel verticalPanel = new VerticalPanel();
    private Button button_1 = new Button("+");
 //   private RichTextArea richTextAreaBoton = new RichTextArea();
    private SelectorPanel SE;
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
        DecoratorPanel decoratorPanel = new DecoratorPanel();
        decoratorPanel.setHeight("38px");
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
                    SE.hide();
                }
            }
        });
        button.addMouseOverHandler(new MouseOverHandler() {

            public void onMouseOver(MouseOverEvent event) {
                if (SE != null) {
                    SE.hide();
                }
                SE = new SelectorPanel(annotation.getTextSelector().getX().intValue(),
                        annotation.getTextSelector().getY().intValue(),
                        Imagen.getAbsoluteLeft(), Imagen.getAbsoluteTop(),
                        annotation.getTextSelector().getWidth().intValue(),
                        annotation.getTextSelector().getHeight().intValue());
                if (!Estado) SE.show();
            }
        });
        horizontalPanel.add(button);
//        button.setEnabled(true);
//        button.setVisible(false);
//        button.setSize("254px", "42px");

        richTextArea.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
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
                    SE.hide();
                }
            	}
            }
        });

        richTextArea.addMouseOverHandler(new MouseOverHandler() {

            public void onMouseOver(MouseOverEvent event) {
            	if (!Estado){
            	if (SE != null) {
            					SE.hide();
            					}
                SE = new SelectorPanel(annotation.getTextSelector().getX().intValue(),
                        annotation.getTextSelector().getY().intValue(),
                        Imagen.getAbsoluteLeft(), Imagen.getAbsoluteTop(),
                        annotation.getTextSelector().getWidth().intValue(),
                        annotation.getTextSelector().getHeight().intValue());
                 SE.show();
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
        menuBar.addItem(mntmNewItem_2);
       
        
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        
       mntmNewItem_2.setText(annotation.getUserName() + " --- " +sdf.format(annotation.getCreatedDate()));
        richTextArea.setVisible(false);





    }
    
    public static void setEstado(boolean estado) {
		Estado = estado;
	}
    
    public void hideSelectorPanel() {
		SE.hide();
	}
}
