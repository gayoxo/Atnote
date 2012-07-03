package lector.client.admin.catalog;

import lector.client.admin.tagstypes.EditorTagsAndTypes;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Controlador;

import lector.client.reader.LoadingPanel;
import lector.client.controler.InformationConstants;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class SeleccionMenuCatalog extends PopupPanel {

	private BottonCatalog BLan;
	private NewAdminCatalogs Father;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	
	public SeleccionMenuCatalog(BottonCatalog BL, NewAdminCatalogs Fatherin) {
		super(true);
		BLan=BL;
		setSize("100%", "100%");
		Father=Fatherin;
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		Button btnNewButton = new Button("Select");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				BLan.swap();
				hide();
			}
		});
		//verticalPanel.add(btnNewButton);
		btnNewButton.setSize("100%", "100%");
		
		Button btnNewButton_1 = new Button("Delete");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				if (Window
						.confirm(InformationConstants.ARE_YOU_SURE_DELETE_CATALOG
								+ BLan.getCatalog().getCatalogName()))
				{
				hide();
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto("Deleting...");
				bookReaderServiceHolder.deleteCatalog(BLan.getCatalog().getId(), new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
						LoadingPanel.getInstance().hide();
						Father.refresh();
					}
					
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
					Window.alert("The Catalog could not be deleted");
					
						
					}
				});
				
				}
			}
		});
		btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnNewButton_1.setStyleName("gwt-ButtonTOP");
		verticalPanel.add(btnNewButton_1);
		btnNewButton_1.setSize("100%", "100%");
		
		Button btnNewButton_2 = new Button("Edit");
		btnNewButton_2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
				EditorTagsAndTypes.setCatalogo(BLan.getCatalog());
				Controlador.change2EditorTagsAndTypes();
				
			}
		});
		btnNewButton_2.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnNewButton_2.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnNewButton_2.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnNewButton_2.setStyleName("gwt-ButtonTOP");
		verticalPanel.add(btnNewButton_2);
		btnNewButton_2.setSize("100%", "100%");
		
		Button btnNewButton_3 = new Button("Change Visibility");
		btnNewButton_3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
				Change_Visivility Nuevo=new Change_Visivility(BLan.getCatalog(), Father);
				Nuevo.center();
			}
		});
		
		btnNewButton_3.setStyleName("gwt-ButtonBotton");
		btnNewButton_3.setSize("100%", "100%");
		btnNewButton_3.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
			}
		});
		btnNewButton_3.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
			}
		});
		btnNewButton_3.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
			}
		});
		verticalPanel.add(btnNewButton_3);
	}

}
