package lector.client.reader;

import java.io.Serializable;

public class ExportObject implements Serializable {

	private Annotation annotation;
	private String imageURL;
	private int width;
	private int height;

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

}
