package lector.client.browser;

import lector.client.reader.Annotation;
import lector.client.reader.SelectorPanel;

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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class CommentPanelBrowser extends Composite {

    private RichTextArea richTextArea = new RichTextArea();
    private Annotation annotation;
    private VerticalPanel verticalPanel = new VerticalPanel();
    private Button button_1 = new Button("+");
    private Image Imagen;
    private Button button;
    private final VerticalPanel verticalPanel_1 = new VerticalPanel();
    private final MenuBar menuBar = new MenuBar(false);
	private MenuItem mntmNewItem_2;
    

    public CommentPanelBrowser(Annotation annotationin, Image originalBook) {

    	 annotation = annotationin;
         Imagen = originalBook;
         DecoratorPanel decoratorPanel = new DecoratorPanel();
         decoratorPanel.setHeight("38px");
         initWidget(decoratorPanel);

         decoratorPanel.setWidget(verticalPanel);

         final HorizontalPanel horizontalPanel = new HorizontalPanel();
         verticalPanel.add(horizontalPanel);
         horizontalPanel.setHeight("28px");
         
         
        horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        verticalPanel.add(horizontalPanel);
        horizontalPanel.setWidth("100%");
        horizontalPanel.add(verticalPanel_1);
        verticalPanel_1.setSize("100%", "100%");

        String Showbutton= annotation.getComment().toString();
        if (Showbutton.length()>20){
        	Showbutton=Showbutton.substring(0,20);
        	Showbutton=Showbutton+" ...";
        }
        button = new Button(Showbutton);
        button.setHTML(Showbutton);
        verticalPanel_1.add(button);
        button.setText(annotation.getUserName());
        button.setEnabled(true);
        button.setVisible(false);
        button.setSize("100%", "42px");


        button_1.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
            	 if (button_1.getText().contentEquals("+")) {
                     //verticalPanel.add(richTextArea);
                     richTextArea.setVisible(true);
                     menuBar.setVisible(true);
                   //  button.setVisible(true);
//                     richTextAreaBoton.setVisible(false);
                     button_1.setText("-");
                 } else {
                     // verticalPanel.remove(richTextArea);
                     richTextArea.setVisible(false);
                    // button.setVisible(false);
//                     richTextAreaBoton.setVisible(true);
//                     richTextAreaBoton.setSize("254px", "38px");
                     button_1.setText("+");
                     menuBar.setVisible(false);
//                     horizontalPanel.clear();
//                     horizontalPanel.add(button);
////                     horizontalPanel.add(richTextAreaBoton);
//                     horizontalPanel.add(button_1);

                 }
            }
        });
        horizontalPanel.add(button_1);
        button_1.setSize("100%", "42px");

        richTextArea.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
            	  VisualBookPanel TCE = new VisualBookPanel(annotation,Imagen);
                  TCE.show();
            }
        });

        richTextArea.setHTML(annotation.getComment().toString());
        richTextArea.setSize("99%", "174px");
        verticalPanel.add(richTextArea);
        richTextArea.setEnabled(false);
        richTextArea.setVisible(false);

verticalPanel.add(menuBar);
        
 
        
//        mntmNewItem_1 = new MenuItem("New item", false, new Command() {
//        	public void execute() {
//        		Window.alert("Borrar");
//        	}
//        });
        menuBar.setVisible(false);
//        mntmNewItem_1.setEnabled(false);
//        mntmNewItem_1.setHTML("Delete");
//        menuBar.addItem(mntmNewItem_1);
        
        
        mntmNewItem_2 = new MenuItem("New item", false, (Command) null);
        menuBar.addItem(mntmNewItem_2);




    }
    

}
