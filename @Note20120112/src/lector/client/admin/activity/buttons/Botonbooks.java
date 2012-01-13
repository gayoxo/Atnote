package lector.client.admin.activity.buttons;

import com.google.gwt.user.client.ui.Button;

public class Botonbooks extends Button {
 
	
	private String book;
	
	public Botonbooks(String bookin) {
		super(bookin);
		book=bookin;
		
	}
	
	
	public String getBook() {
		return book;
	}
	
	public void setBook(String book) {
		this.book = book;
	}
	
}
