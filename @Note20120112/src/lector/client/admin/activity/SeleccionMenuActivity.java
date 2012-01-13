package lector.client.admin.activity;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.reader.LoadingPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class SeleccionMenuActivity extends PopupPanel {

	private BottonActivity BLan;
	private NewAdminActivities Father;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	
	public SeleccionMenuActivity(BottonActivity BL, NewAdminActivities Fatherin) {
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
				bookReaderServiceHolder.deleteReadingActivity(BLan.getReadingActivity().getId(), new AsyncCallback<Void>() {
					
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
				EditorActivity EA=new EditorActivity(BLan.getReadingActivity());
				EA.center();
				EA.setModal(true);
				hide();
			}
		});
		verticalPanel.add(btnNewButton_2);
		btnNewButton_2.setWidth("100%");
	}

}
