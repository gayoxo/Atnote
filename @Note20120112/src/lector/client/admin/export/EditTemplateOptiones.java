package lector.client.admin.export;

import lector.client.admin.export.template.Template;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.reader.LoadingPanel;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class EditTemplateOptiones extends PopupPanel {

	private Template T;
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	private NewAdminTemplate YO;
	
	public EditTemplateOptiones(Template t, NewAdminTemplate yo) {
		super(true);
		setGlassEnabled(true);
		T=t;
		YO=yo;
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		setWidget(dockLayoutPanel);
		dockLayoutPanel.setSize("221px", "139px");
		
		MenuBar menuBar = new MenuBar(false);
		dockLayoutPanel.addNorth(menuBar, 2.2);
		
		MenuItem mntmClose = new MenuItem("Close", false, new Command() {
			public void execute() {
				hide();
			}
		});
		menuBar.addItem(mntmClose);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		dockLayoutPanel.add(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		CheckBox checkBox = new CheckBox(InformationConstants.ORDER_EDITABLE);
		verticalPanel.add(checkBox);
		checkBox.setValue(T.isModifyable());
		
		Button btnNewButton = new Button("Save");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto("Loading...");
				exportServiceHolder.saveTemplate(T, new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
						LoadingPanel.getInstance().hide();	
						hide();
						YO.refresh();
					}
					
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();		
						Window.alert(ErrorConstants.ERROR_UPDATING_TEMPLATE);
					}
				});
				
			}
		});
		verticalPanel.add(btnNewButton);
	}

}
