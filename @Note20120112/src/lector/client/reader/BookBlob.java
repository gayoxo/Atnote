package lector.client.reader;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book_blob")
public class BookBlob implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String pagesCount;
	private String publishedYear;
	private String title;
	private String author;
	@Basic
	private ArrayList<String> webLinks = new ArrayList<String>();
	private Long userApp;

	public Long getUserApp() {
		return userApp;
	}

	public void setUserApp(Long userApp) {
		this.userApp = userApp;
	}

	public BookBlob() {
		webLinks = new ArrayList<String>();
	}

	public BookBlob(String pagesCount, String publishedYear, String title,
			String author, ArrayList<String> webLinks, Long userApp) {
		this();
		this.pagesCount = pagesCount;
		this.publishedYear = publishedYear;
		this.title = title;
		this.author = author;
		this.webLinks = webLinks;
		this.userApp = userApp;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPagesCount() {
		return pagesCount;
	}

	public void setPagesCount(String pagesCount) {
		this.pagesCount = pagesCount;
	}

	public String getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(String publishedYear) {
		this.publishedYear = publishedYear;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String> getWebLinks() {
		return webLinks;
	}

	public void setWebLinks(ArrayList<String> webLinks) {
		this.webLinks = webLinks;
	}

}
