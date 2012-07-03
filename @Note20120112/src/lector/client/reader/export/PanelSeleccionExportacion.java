package lector.client.reader.export;

import java.util.ArrayList;
import java.util.Stack;

import lector.client.reader.ExportObject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;

public class PanelSeleccionExportacion extends PopupPanel {

	private ArrayList<ExportObject> pendientes;
	
	public PanelSeleccionExportacion(ArrayList<ExportObject> list) {
		super(false);
		pendientes = list;
		setGlassEnabled(true);
		setAnimationEnabled(true);

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setWidget(verticalPanel);
		verticalPanel.setSize("234px", "130px");

		SimplePanel simplePanel = new SimplePanel();
		verticalPanel.add(simplePanel);
		verticalPanel.setCellVerticalAlignment(simplePanel,
				HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setCellHorizontalAlignment(simplePanel,
				HasHorizontalAlignment.ALIGN_CENTER);

		Label lblNewLabel = new Label("Select Format to export");
		simplePanel.setWidget(lblNewLabel);
		lblNewLabel.setSize("100%", "100%");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel);
		verticalPanel.setCellVerticalAlignment(horizontalPanel,
				HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setWidth("");

		Button btnNewButton = new Button("HTML");
		horizontalPanel.add(btnNewButton);
		
		btnNewButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
				arbitroLlamadasHTML A=new arbitroLlamadasHTML(pendientes);
				A.llamadaBucle();
				hide();
			}
		});

		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		btnNewButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});

		btnNewButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		btnNewButton.setStyleName("gwt-ButtonCenter");

	

		Button btnNewButton_1 = new Button("RTF");
		horizontalPanel.add(btnNewButton_1);

		btnNewButton_1.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
				arbitroLlamadasRTF A=new arbitroLlamadasRTF(pendientes);
				A.llamadaBucle();
				hide();
			}
		});

		btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});

		btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		btnNewButton_1.setStyleName("gwt-ButtonCenter");

	
	
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel.add(verticalPanel_1);

		Button btnNewButton_2 = new Button("Cancel");
		verticalPanel_1.add(btnNewButton_2);

		btnNewButton_2.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
				hide();
			}
		});

		btnNewButton_2.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		btnNewButton_2.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});

		btnNewButton_2.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		btnNewButton_2.setStyleName("gwt-ButtonCenter");

	}

}
