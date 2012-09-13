package lector.client.login;

import lector.client.catalogo.client.Catalog;
import lector.client.reader.Book;
import lector.share.model.Language;
import lector.share.model.ReadingActivity;
import lector.share.model.UserApp;

public class ActualUser {

	private static UserApp User;
	private static Book book;
	private static Language language;
	private static Catalog catalogo;
	private static Catalog openCatalog;
	private static ReadingActivity readingactivity;

	public static UserApp getUser() {
		return User;
	}

	public static void setUser(UserApp user) {
		User = user;

	
	}

	public static String getRol() {
		return User.getProfile();
	}

	
	public static Book getBook() {
		return book;
	}
	
	public static void setBook(Book book) {
		ActualUser.book = book;
	}
	
	public static Catalog getCatalogo() {
		return catalogo;
	}
	
	public static Language getLanguage() {
		return language;
	}
	
	public static void setCatalogo(Catalog catalogo) {
		ActualUser.catalogo = catalogo;
	}
	
	public static void setLanguage(Language language) {
		ActualUser.language = language;
	}
	
	public static ReadingActivity getReadingactivity() {
		return readingactivity;
	}
	
	public static void setReadingactivity(ReadingActivity readingactivity) {
		ActualUser.readingactivity = readingactivity;
	}
	
	public static Catalog getOpenCatalog() {
		return openCatalog;
	}
	
	public static void setOpenCatalog(Catalog openCatalog) {
		ActualUser.openCatalog = openCatalog;
	}
	
}
