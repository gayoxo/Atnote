package lector.client.search;


import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.login.ActualUser;
import lector.client.reader.Book;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.TextBox;

public class VisorSearcher extends PopupPanel {

	public String BookId="8topAAAAYAAJ";
	public VisorSearcher Yo;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	public Book Entrada;
	
	public VisorSearcher(String Bookin,Book entrada) {
		super(false);
		BookId=Bookin;
		Yo=this;
		Entrada=entrada;
		String[] Booksplit=BookId.split("&");
		BookId=Booksplit[0];
		setGlassEnabled(true);
		SimplePanel simplePanel = new SimplePanel();
		setWidget(simplePanel);
		//simplePanel.setSize("100%", "100%");
		simplePanel.setSize("846px", "608px");
		
		FlowPanel verticalPanel = new FlowPanel();
		simplePanel.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);
		
		MenuItem mntmClose = new MenuItem("Close", false, new Command() {
			public void execute() {
			Yo.hide();
			}
		});
		menuBar.addItem(mntmClose);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuItem mntmSave = new MenuItem("Add to My Books", false, new Command() {
			public void execute() {
				ActualUser.getUser().getBookIds().add(Entrada.getTitle()+" "+Constants.BREAKER+" "+Entrada.getId());
				bookReaderServiceHolder.saveUser(ActualUser.getUser(),
						new AsyncCallback<Boolean>() {

							public void onFailure(Throwable caught) {
								Window.alert("Sorry, the book could not be saved, try again later");
								Controlador.change2BookAdminstrator();
								Yo.hide();
							}

							public void onSuccess(Boolean result) {
								Controlador.change2BookAdminstrator();
								Yo.hide();
							}
						});
			}
		});
		menuBar.addItem(mntmSave);
		
		String Direccion=BookId +"&printsec=frontcover&output=embed";
		
		TextBox textBox = new TextBox();
		textBox.setVisibleLength(180);
		textBox.setReadOnly(true);
		textBox.setText(Direccion);
		verticalPanel.add(textBox);
		textBox.setWidth("839px");
		Frame frame = new Frame(Direccion);
		verticalPanel.add(frame);
		frame.setSize("842px", "556px");
		
	}



}
