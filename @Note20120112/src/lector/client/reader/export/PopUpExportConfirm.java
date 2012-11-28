package lector.client.reader.export;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Label;

public class PopUpExportConfirm extends PopupPanel {
	
	private static String SUBMIT_BUTTON="Submit";
	private static String INFO_LABEL="Press Submit Button to Generate the Export Object";
	private FormPanel formPanel;
	
	public PopUpExportConfirm(FormPanel formPanel) {
		super(true);
		this.formPanel=formPanel;
		VerticalPanel verticalpanel = new VerticalPanel();
		setWidget(verticalpanel);
		setGlassEnabled(true);
		setAnimationEnabled(true);
		
//		simplePanel.setSize("100%", "100%");
//		verticalpanel.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-100+"px");
		verticalpanel.add(formPanel);
		
		Button btnNewButton = new Button("submit");
		btnNewButton.setText(PopUpExportConfirm.SUBMIT_BUTTON);
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PopUpExportConfirm.this.formPanel.submit();
				hide();
			}
		});
		btnNewButton
		.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenter");

			}
		});

btnNewButton
		.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(
					MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

btnNewButton
		.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(
					MouseOutEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenter");
			}
		});

btnNewButton
		.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(
					MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

btnNewButton.setStyleName("gwt-ButtonCenter");
		Label lblNewLabel = new Label(PopUpExportConfirm.INFO_LABEL);
		verticalpanel.add(lblNewLabel);
		lblNewLabel.setWidth("449px");
		verticalpanel.add(btnNewButton);
	}

}
