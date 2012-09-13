package lector.client.reader;

import java.io.Serializable;

import lector.share.model.Annotation;

public class ExportObject implements Serializable {

	private Annotation annotation;
	private String imageURL;
	private int width;
	private int height;
	private String authorName;
	private String date;

	public ExportObject() {
		super();
	}

	public ExportObject(Annotation annotation, String imageURL, int width, int height) {
		super();
		this.annotation = annotation;
		this.imageURL = imageURL;
		this.width = width;
		this.height = height;
	}

	public ExportObject(Annotation annotation, String imageURL, int width,
			int height, String authorName, String date) {
		super();
		this.annotation = annotation;
		this.imageURL = imageURL;
		this.width = width;
		this.height = height;
		this.authorName = authorName;
		this.date = date;
	}

	public Annotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
