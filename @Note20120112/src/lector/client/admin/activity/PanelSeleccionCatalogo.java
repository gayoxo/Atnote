package lector.client.admin.activity;

import lector.client.catalogo.client.Catalog;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class PanelSeleccionCatalogo extends PopupPanel {

	private Catalog Catalogo;
	private Label LPrivate;
	private Label LPublic;
	private EditorActivity Father;
	
	public PanelSeleccionCatalogo(Catalog catalogo, Label catalogLabel,
			Label openCatalogLabel, EditorActivity yo) {
		
		super(true);
		Catalogo=catalogo;
		LPrivate=catalogLabel;
		LPublic=openCatalogLabel;
		Father=yo;
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(4);
		setWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		Button btnNewButton = new Button("Teacher Catalog");
		btnNewButton.setSize("100%", "100%");
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		btnNewButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		btnNewButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			}
		});
		btnNewButton.setStyleName("gwt-ButtonCenter");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LPrivate.setText("Teacher Catalog :"
						+ Catalogo.getCatalogName());
				Father.setSelectedCatalog(Catalogo);
				hide();
			}
		});
		horizontalPanel.add(btnNewButton);
		
		Button btnNewButton_1 = new Button("Open Catalog");
		btnNewButton_1.setSize("100%", "100%");
		btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			}
		});
		btnNewButton_1.setStyleName("gwt-ButtonCenter");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LPublic.setText("Open Catalog :"
						+ Catalogo.getCatalogName());
				Father.setSelectedCatalogPublic(Catalogo);
				hide();
			}
		});
		horizontalPanel.add(btnNewButton_1);
		// TODO Auto-generated constructor stub
	}

}
