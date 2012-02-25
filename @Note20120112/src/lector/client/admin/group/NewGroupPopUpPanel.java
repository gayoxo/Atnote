package lector.client.admin.group;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.login.GroupApp;
import lector.client.reader.LoadingPanel;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class NewGroupPopUpPanel extends PopupPanel {

	private TextBox textBox;
	private Groupadministration Father;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);

	public NewGroupPopUpPanel(Groupadministration Fatherin) {
		super(true);
		Father=Fatherin;
		
		FlowPanel flowPanel = new FlowPanel();
		setWidget(flowPanel);
		flowPanel.setSize("100%", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		flowPanel.add(menuBar);
		
		MenuItem mntmNewItem = new MenuItem("New item", false, new Command() {
			public void execute() {
				hide();
			}
		});
		mntmNewItem.setHTML("Cancel");
		menuBar.addItem(mntmNewItem);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(10);
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flowPanel.add(verticalPanel);
		verticalPanel.setSize("291px", "91px");
		
		Label label = new Label("Type the name of the new group ");
		verticalPanel.add(label);
		label.setSize("305px", "26px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel);
		
		textBox = new TextBox();
		textBox.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode()) generateNewGroup();
			}
		});
		horizontalPanel.add(textBox);
		textBox.setWidth("231px");
		
		Button btnNewButton = new Button("Create");
		btnNewButton.setStyleName("gwt-ButtonCenter");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				generateNewGroup();
			}
		});
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
		horizontalPanel.add(btnNewButton);
		btnNewButton.setSize("100%", "100%");
	}

	protected void generateNewGroup() {
		String Grupo=textBox.getText();
		if (!Grupo.isEmpty()||Grupo.length()<2)
				{
			LoadingPanel.getInstance().center();
			LoadingPanel.getInstance().setLabelTexto("Saving...");
				bookReaderServiceHolder.saveNewGroup(new GroupApp(Grupo), new AsyncCallback<Boolean>() {
					
					public void onSuccess(Boolean result) {
						LoadingPanel.getInstance().hide();
					if (!result)
						{
						Father.refreshGroups();
						hide();
						}
					else Window.alert("The group that you are trying to save exists already");
						
					}
					
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
					Window.alert("The group could not be saved, try again later");
						
					}
				});
				}
		else Window.alert("The name of the grupo need to be at less two characters and can`t be empty");
		
		
	}

}
