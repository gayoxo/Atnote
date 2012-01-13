package lector.client.reader;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class LoadingPanel extends PopupPanel {

    private static LoadingPanel MyPanelStatic;
	private Label LabelText;

    ;

    private LoadingPanel() {
        super(false);
        setGlassEnabled(true);
        SimplePanel SP = new SimplePanel();
        add(SP);
        super.setGlassEnabled(true);
        SP.setSize("230px", "109px");
        
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        verticalPanel.setSpacing(6);
        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        SP.setWidget(verticalPanel);
        verticalPanel.setSize("100%", "100%");
        
        Image image = new Image("Loader.gif");
        verticalPanel.add(image);
        image.setSize("77px", "76px");
        
        LabelText = new Label("Cargando...");
        verticalPanel.add(LabelText);

    }

    public static LoadingPanel getInstance() {
        if (MyPanelStatic == null) {
            MyPanelStatic = new LoadingPanel();
        }
        return MyPanelStatic;
    }

    public void setLabelTexto(String labelText) {
        LabelText.setText(labelText);
    }
}
