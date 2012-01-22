package lector.client.admin.activity;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Controlador;
import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class NewAdminActivities implements EntryPoint {

	private VerticalPanel Actual;
	private VerticalPanel Selected;
	private NewAdminActivities yo;
	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	public void onModuleLoad() {
		RootPanel RootTXOriginal = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root");
		RootTXOriginal.setStyleName("Root");

		yo = this;

		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		menuBar.setWidth("100%");

		MenuItem menuItem = new MenuItem("Activity", false,
				(Command) null);
		menuItem.setHTML("Activity Administration");
		menuItem.setEnabled(false);
		menuBar.addItem(menuItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		MenuItem mntmNewItem = new MenuItem("New item", false, new Command() {
			public void execute() {
				newActivity NL = new newActivity(yo);
				NL.center();

			}
		});
		mntmNewItem.setHTML("New");
		menuBar.addItem(mntmNewItem);

		MenuItemSeparator separator_2 = new MenuItemSeparator();
		menuBar.addSeparator(separator_2);

		MenuItem mntmBack = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		menuBar.addItem(mntmBack);

		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		RootTXOriginal.add(horizontalSplitPanel, 0, 25);
		horizontalSplitPanel.setSize("100%", "100%");

		Selected = new VerticalPanel();
		Selected.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalSplitPanel.setRightWidget(Selected);
		Selected.setSize("100%", "");

		Actual = new VerticalPanel();
		Actual.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalSplitPanel.setLeftWidget(Actual);
		Actual.setSize("100%", "");

		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
		bookReaderServiceHolder.getReadingActivityByProfessorId(ActualUser
				.getUser().getId(),
				new AsyncCallback<ArrayList<ReadingActivity>>() {

					public void onFailure(Throwable caught) {
						Window.alert("There was an error encounter while retrieving the activities, please try again later");
						LoadingPanel.getInstance().hide();

					}

					public void onSuccess(ArrayList<ReadingActivity> result) {
						LoadingPanel.getInstance().hide();
						ArrayList<ReadingActivity> ActivityMostrar = result;
						for (int i = 0; i < ActivityMostrar.size()-1; i++) {
							
							BottonActivity nue = new BottonActivity(Actual,
									Selected, ActivityMostrar.get(i));
							nue.setWidth("100%");
							nue.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							nue.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							nue.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							nue.setStyleName("gwt-ButtonTOP");
							nue.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									SeleccionMenuActivity panel = new SeleccionMenuActivity(
											(BottonActivity) event.getSource(),
											yo);
									panel.showRelativeTo((BottonActivity) event
											.getSource());
								}
							});

						}
						if (!ActivityMostrar.isEmpty()) {
							
							BottonActivity nue = new BottonActivity(Actual,
									Selected, ActivityMostrar.get(ActivityMostrar.size()-1));
							nue.setWidth("100%");
							nue.setStyleName("gwt-ButtonBotton");
							nue.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
								}
							});
							nue.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
								}
							});
							nue.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
								}
							});
							nue.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									SeleccionMenuActivity panel = new SeleccionMenuActivity(
											(BottonActivity) event.getSource(),
											yo);
									panel.showRelativeTo((BottonActivity) event
											.getSource());
								}
							});

						}
					}
				});

	}

	public void refresh() {
		Actual.clear();
		Selected.clear();
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Saving...");
		bookReaderServiceHolder.getReadingActivityByProfessorId(ActualUser
				.getUser().getId(),
				new AsyncCallback<ArrayList<ReadingActivity>>() {

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert("There was an error encounter while retrieving the activities, please try again later");

					}

					public void onSuccess(ArrayList<ReadingActivity> result) {
						LoadingPanel.getInstance().hide();
						ArrayList<ReadingActivity> ActivityMostrar = result;
for (int i = 0; i < ActivityMostrar.size()-1; i++) {
							
							BottonActivity nue = new BottonActivity(Actual,
									Selected, ActivityMostrar.get(i));
							nue.setWidth("100%");
							nue.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							nue.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							nue.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							nue.setStyleName("gwt-ButtonTOP");
							nue.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									SeleccionMenuActivity panel = new SeleccionMenuActivity(
											(BottonActivity) event.getSource(),
											yo);
									panel.showRelativeTo((BottonActivity) event
											.getSource());
								}
							});

						}
						if (!ActivityMostrar.isEmpty()) {
							
							BottonActivity nue = new BottonActivity(Actual,
									Selected, ActivityMostrar.get(ActivityMostrar.size()-1));
							nue.setWidth("100%");
							nue.setStyleName("gwt-ButtonBotton");
							nue.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
								}
							});
							nue.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
								}
							});
							nue.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
								}
							});
							nue.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									SeleccionMenuActivity panel = new SeleccionMenuActivity(
											(BottonActivity) event.getSource(),
											yo);
									panel.showRelativeTo((BottonActivity) event
											.getSource());
								}
							});

						}

					}
				});

	}
}
