package lector.client.reader;

import java.util.ArrayList;

import lector.client.login.ActualUser;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class AceptWindow extends PopupPanel {

	public AceptWindow() {
		super(false);
		setModal(true);
		setGlassEnabled(true);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(3);
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		Label lblNewLabel = new Label(ActualUser.getLanguage().getDOYOUFilterOUT());
		verticalPanel.add(lblNewLabel);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(5);
		verticalPanel.add(horizontalPanel);
		
		Button btnNewButton = new Button(ActualUser.getLanguage().getAcceptFilter());
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ArrayList<Long> filtro= new ArrayList<Long>();
				filtro.add(Long.MIN_VALUE);
				MainEntryPoint.setFiltroTypes(filtro);
				hide();
			}
		});
		horizontalPanel.add(btnNewButton);
		
		Button btnNewButton_1 = new Button(ActualUser.getLanguage().getCancel());
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		horizontalPanel.add(btnNewButton_1);
		
	
	}
}
