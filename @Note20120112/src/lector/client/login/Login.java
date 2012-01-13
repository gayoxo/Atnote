package lector.client.login;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class Login implements EntryPoint {

	private TextBox textBox;
	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private Button btnNewButton;

	public void onModuleLoad() {

		RootPanel rootPanel = RootPanel.get("Original");
		rootPanel.setStyleName("Root");

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		rootPanel.add(verticalPanel);
		verticalPanel.setSize("450px", "143px");

		Label lblNewLabel = new Label("Log In");
		lblNewLabel.setStyleName("TituloWelcome");
		verticalPanel.add(lblNewLabel);
		lblNewLabel.setHeight("40px");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(10);
		verticalPanel.add(horizontalPanel);

		textBox = new TextBox();
		textBox.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode()) {
					btnNewButton.setEnabled(false);
					revisarTextboxAndEnter();
				}
			}

		});
		horizontalPanel.add(textBox);

		btnNewButton = new Button("Enter");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				btnNewButton.setEnabled(false);
				revisarTextboxAndEnter();
			}
		});
		horizontalPanel.add(btnNewButton);
	}

	protected void revisarTextboxAndEnter() {
		String nombre = textBox.getText();


			bookReaderServiceHolder.loginAuxiliar(nombre,
					new AsyncCallback<UserApp>() {

						public void onFailure(Throwable caught) {
							Window.alert("You are not authorized to view this application");
							btnNewButton.setEnabled(true);
						}

						public void onSuccess(UserApp result) {
							ActualUser.setUser(result);
							
							
							
							if (result.getProfile()==null){
								Window.alert("You are not authorized to view this application");
								btnNewButton.setEnabled(true);
							}
							else if (result.getProfile().equals(Constants.STUDENT))
								Controlador.change2MyActivities();
							else if (result.getProfile().equals(
									Constants.PROFESSOR))
								Controlador.change2Administrator();

						}

					});

		


	}


}
