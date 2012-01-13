package lector.client.admin.catalog;

import lector.client.admin.tagstypes.EditorTagsAndTypes;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Controlador;

import lector.client.reader.LoadingPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

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
		btnNewButton.setWidth("100%");
		
		Button btnNewButton_1 = new Button("Delete");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto("Deleting...");
				bookReaderServiceHolder.deleteCatalog(BLan.getCatalog().getId(), new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
						LoadingPanel.getInstance().hide();
						Father.refresh();
						hide();
					}
					
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
					Window.alert("The Catalog could not be deleted");
						
					}
				});
				
				
			}
		});
		verticalPanel.add(btnNewButton_1);
		btnNewButton_1.setWidth("100%");
		
		Button btnNewButton_2 = new Button("Edit");
		btnNewButton_2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditorTagsAndTypes.setCatalogo(BLan.getCatalog());
				Controlador.change2EditorTagsAndTypes();
				hide();
			}
		});
		verticalPanel.add(btnNewButton_2);
		btnNewButton_2.setWidth("100%");
		
		Button btnNewButton_3 = new Button("Change Visibility");
		btnNewButton_3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Change_Visivility Nuevo=new Change_Visivility(BLan.getCatalog(), Father);
				Nuevo.center();
			}
		});
		verticalPanel.add(btnNewButton_3);
	}

}
