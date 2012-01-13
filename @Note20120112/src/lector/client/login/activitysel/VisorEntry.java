package lector.client.login.activitysel;

import lector.client.controler.Controlador;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItemSeparator;

public class VisorEntry implements EntryPoint {

	
	private String Book;


	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		rootPanel.setSize("100%", "100%");
				
				MenuBar menuBar = new MenuBar(false);
				RootMenu.add(menuBar);
				
				MenuItem mntmBookReader = new MenuItem("Book Reader ", false, (Command) null);
				mntmBookReader.setEnabled(false);
				menuBar.addItem(mntmBookReader);
				
				MenuItemSeparator separator = new MenuItemSeparator();
				menuBar.addSeparator(separator);
				
				MenuItem mntmBack = new MenuItem("Back", false, new Command() {
					public void execute() {
						Controlador.change2Administrator();
					}
				});
				menuBar.addItem(mntmBack);
				
				SimplePanel simplePanel = new SimplePanel();
				rootPanel.add(simplePanel);
				simplePanel.setSize("100%", "100%");
				String[] Booksplit=Book.split("&");
				Book=Booksplit[0];

				
				Frame F=new Frame(Book+"&printsec=frontcover&output=reader");
				F.setWidth("100%");
				F.setHeight("100%");
				simplePanel.add(F);
		
	}
	
	
	public void setBook(String book) {
		Book = book;
	}
	
	public String getBook() {
		return Book;
	}

}
