package lector.client.admin.tagstypes;

import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class SelectBetweenFileOrFolderInNew extends PopupPanel {

	public enum whatsthenew {Type,TypeCategory }
	private ListBox comboBox;
	private Entity Father;
	private static Catalog catalog;
	
	public SelectBetweenFileOrFolderInNew(Entity father) {
		super(false);
		setGlassEnabled(true);
		setModal(true);
		Father=father;
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setWidget(verticalPanel);
		verticalPanel.setSize("215px", "127px");
		
		Label lblNewLabel = new Label("Select Between \"Type Category\" or  \"Type\" to create the new object");
		lblNewLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(lblNewLabel);
		
		comboBox = new ListBox();
		comboBox.addItem("Type Category");
		comboBox.addItem("Type");	
		verticalPanel.add(comboBox);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(5);
		verticalPanel.add(horizontalPanel);
		
		Button btnNewButton = new Button("Select");
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
				if (comboBox.getItemText(comboBox.getSelectedIndex()).equals("Type")){
					NewTyperAdmin.setCatalog(catalog);
					NewTyperAdmin NTA = new NewTyperAdmin(Father, whatsthenew.Type);
					NTA.center();
					NTA.setModal(true);	
					hide();
				}
				else {
					NewTyperAdmin.setCatalog(catalog);
					NewTyperAdmin NTA = new NewTyperAdmin(Father, whatsthenew.TypeCategory);			
					NTA.center();
					NTA.setModal(true);
					hide();
				}
			}
		});
		horizontalPanel.add(btnNewButton);
		
		Button btnNewButton_1 = new Button("Cancel");
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
				hide();
			}
		});
		horizontalPanel.add(btnNewButton_1);
	}

	public static void setCatalog(Catalog catalog) {
		SelectBetweenFileOrFolderInNew.catalog = catalog;
	}
	
	public static Catalog getCatalog() {
		return catalog;
	}
}
