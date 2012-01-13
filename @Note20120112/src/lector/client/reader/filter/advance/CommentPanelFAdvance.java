package lector.client.reader.filter.advance;

import lector.client.reader.Annotation;
import lector.client.reader.SelectorPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
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

public class CommentPanelFAdvance extends Composite {

    private RichTextArea richTextArea = new RichTextArea();
    private Annotation annotation;
    private VerticalPanel verticalPanel = new VerticalPanel();
    private Button button_1 = new Button("+");
    private RichTextArea richTextAreaBoton = new RichTextArea();
    private Image Imagen;
    private Button button;
    private final VerticalPanel verticalPanel_1 = new VerticalPanel();
    

    public CommentPanelFAdvance(Annotation annotationin, Image originalBook) {

        annotation = annotationin;
        Imagen = originalBook;
        DecoratorPanel decoratorPanel = new DecoratorPanel();
        decoratorPanel.setSize("100%", "38px");
        initWidget(decoratorPanel);

        decoratorPanel.setWidget(verticalPanel);
        verticalPanel.setWidth("100%");

        final HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        verticalPanel.add(horizontalPanel);
        horizontalPanel.setWidth("100%");
        horizontalPanel.add(verticalPanel_1);
        verticalPanel_1.setSize("100%", "100%");

        button = new Button(annotation.getUserName());
        verticalPanel_1.add(button);
        button.setText(annotation.getUserName());
        button.setEnabled(true);
        button.setVisible(false);
        button.setSize("100%", "42px");
        verticalPanel_1.add(richTextAreaBoton);

        richTextAreaBoton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                VisualBookPanelFilterAdvance TCE = new VisualBookPanelFilterAdvance(annotation,Imagen);
                TCE.show();

            }
        });


        richTextAreaBoton.setHTML(annotation.getComment().toString());
        richTextAreaBoton.setSize("100%", "38px");
        richTextAreaBoton.setEnabled(false);
        richTextAreaBoton.setVisible(true);


        button_1.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                if (button_1.getText().contentEquals("+")) {
                    //verticalPanel.add(richTextArea);
                    richTextArea.setVisible(true);
                    button.setSize("100%", "42px");
                    button.setVisible(true);
                    richTextAreaBoton.setVisible(false);
                    button_1.setText("-");
                } else {
                    // verticalPanel.remove(richTextArea);
                    richTextArea.setVisible(false);
                    button.setVisible(false);
                    richTextAreaBoton.setVisible(true);
                    richTextAreaBoton.setSize("100%", "38px");
                    button_1.setText("+");
                    horizontalPanel.clear();
                    horizontalPanel.add(button);
                    horizontalPanel.add(richTextAreaBoton);
                    horizontalPanel.add(button_1);

                }
            }
        });
        horizontalPanel.add(button_1);
        button_1.setSize("100%", "42px");

        richTextArea.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
            	  VisualBookPanelFilterAdvance TCE = new VisualBookPanelFilterAdvance(annotation,Imagen);
                  TCE.show();
            }
        });

        richTextArea.setHTML(annotation.getComment().toString());
        richTextArea.setSize("99%", "174px");
        verticalPanel.add(richTextArea);
        richTextArea.setEnabled(false);
        richTextArea.setVisible(false);





    }
    

}
