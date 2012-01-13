package lector.client.admin.langedit;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Controlador;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class SeleccionMenu extends PopupPanel {

	private BottonLang BLan;
	private NewAdminLangs Father;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	
	public SeleccionMenu(BottonLang BL, NewAdminLangs Fatherin) {
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
				bookReaderServiceHolder.deleteLanguage(BLan.getLanguage().getName(), new AsyncCallback<Integer>() {
					
					public void onSuccess(Integer result) {
						Father.refresh();
						hide();
						
					}
					
					public void onFailure(Throwable caught) {
						Window.alert("The language could not be removed");
						
					}
				});
		
			}
		});
		verticalPanel.add(btnNewButton_1);
		btnNewButton_1.setWidth("100%");
		
		Button btnNewButton_2 = new Button("Edit");
		btnNewButton_2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditordeLenguajes.setLenguajeActual(BLan.getLanguage());
				Controlador.change2EditorLenguaje();
				hide();
			}
		});
		verticalPanel.add(btnNewButton_2);
		btnNewButton_2.setWidth("100%");
	}

}
