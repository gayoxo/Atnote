package lector.client.login;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Controlador;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class UserEdition implements EntryPoint {

	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private TextBox NameText;
	private TextBox ApellidosText;
	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		
		MenuBar menuBar = new MenuBar(false);
		rootPanel.add(menuBar);
		
		MenuItem CloseBoton = new MenuItem("Close", false, new Command() {
			public void execute() {
				Controlador.change2MyActivities();
			}
		});
		menuBar.addItem(CloseBoton);
		
		SimplePanel simplePanel = new SimplePanel();
		rootPanel.add(simplePanel,0,25);
		simplePanel.setSize("100%", "100%");
		
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		simplePanel.setWidget(dockLayoutPanel);
		dockLayoutPanel.setSize("100%", "100%");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		dockLayoutPanel.addWest(verticalPanel, 32.2);
		verticalPanel.setSize("100%", "100%");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setWidth("100%");
		
		VerticalPanel PanelCampos = new VerticalPanel();
		PanelCampos.setSpacing(10);
		horizontalPanel_1.add(PanelCampos);
		PanelCampos.setWidth("100%");
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		PanelCampos.add(horizontalPanel_2);
		horizontalPanel_2.setWidth("100%");
		
		Label lblNewLabel = new Label("Nombre");
		horizontalPanel_2.add(lblNewLabel);
		
		
		NameText = new TextBox();
		NameText.setMaxLength(25);
		NameText.setVisibleLength(25);
		horizontalPanel_2.add(NameText);
		NameText.setWidth("90%");
		
		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		horizontalPanel_3.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		PanelCampos.add(horizontalPanel_3);
		horizontalPanel_3.setWidth("100%");
		
		Label lblNewLabel_1 = new Label("Apellidos");
		horizontalPanel_3.add(lblNewLabel_1);
		
		ApellidosText = new TextBox();
		ApellidosText.setVisibleLength(25);
		ApellidosText.setMaxLength(120);
		horizontalPanel_3.add(ApellidosText);
		ApellidosText.setWidth("90%");
		
		HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
		horizontalPanel_4.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_4.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		PanelCampos.add(horizontalPanel_4);
		horizontalPanel_4.setWidth("100%");
		
		Button btnNewButton = new Button("Save");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				UserApp AU=ActualUser.getUser();
				AU.setName(NameText.getText());
				AU.setLastName(ApellidosText.getText());
				bookReaderServiceHolder.saveUser(AU, new AsyncCallback<Boolean>() {
					
					public void onSuccess(Boolean result) {
						bookReaderServiceHolder.loadUserById(ActualUser.getUser().getId(), new AsyncCallback<UserApp>() {
							
							public void onSuccess(UserApp result) {
								ActualUser.setUser(result);
								
							}
							
							public void onFailure(Throwable caught) {
								Window.alert("I can reload the update User, if you want to show the new userInformation please reload the page");
								
							}
						});
						
					}
					
					public void onFailure(Throwable caught) {
						Window.alert("Error updating User.");
						
					}
				});
			}
		});
		horizontalPanel_4.add(btnNewButton);
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		dockLayoutPanel.add(verticalPanel_1);
		verticalPanel_1.setSize("100%", "100%");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_1.add(horizontalPanel);
		
		Image image = new Image("Logo.jpg");
		horizontalPanel.add(image);
	}

}
