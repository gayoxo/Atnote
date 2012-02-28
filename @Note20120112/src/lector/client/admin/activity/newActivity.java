package lector.client.admin.activity;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class newActivity extends PopupPanel {

	private PopupPanel Me;
	private NewAdminActivities Father;
	private TextBox textBox;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	public newActivity(NewAdminActivities Fatherin) {
		super(true);
		this.Father = Fatherin;
		Me = this;
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(3);
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");

		Label label = new Label("Insert the name for the new Activity");
		verticalPanel.add(label);
		label.setSize("100%", "100%");

		textBox = new TextBox();
		verticalPanel.add(textBox);
		textBox.setWidth("98%");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setSpacing(4);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");

		Button btnNewButton = new Button("Create");
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

				String S = textBox.getText();
				if (S.isEmpty() || S.length() < 2)
					Window.alert("The Name can be more lenght or equal than two");
				else {
					ReadingActivity A = new ReadingActivity();
					A.setName(S);
					A.setProfessorId(ActualUser.getUser().getId());
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto("Saving...");
					bookReaderServiceHolder.saveReadingActivity(A,
							new AsyncCallback<Void>() {

								public void onFailure(Throwable caught) {
									Window.alert("The Activity could not be saved");
									LoadingPanel.getInstance().hide();
								}

								public void onSuccess(Void result) {
									LoadingPanel.getInstance().hide();
									Father.refresh();
									Me.hide();
									
								}
							});
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
				Me.hide();
			}
		});
		horizontalPanel.add(btnNewButton_1);
		super.setGlassEnabled(true);
	}

}
