package com.service;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import lector.client.reader.Annotation;

@Entity
@XmlRootElement
public class BookSimp implements Serializable {

	private static final long serialVersionUID = 1L;
	private String author;
    private String id;
    private String pagesCount;
    private String publishedYear;
    private String title;
    private String tbURL;
    private String imagesPath;
    private String url;
    private int annotationsCount = 0;


    public BookSimp() {
    }

    public BookSimp(String author, String id, String pagesCount, String publishedYear, String title, String tbURL, String url) {
        this.author = author;
        this.id = id;
        this.pagesCount = pagesCount;
        this.publishedYear = publishedYear;
        this.title = title;
        this.tbURL = tbURL;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAnnotationsCount() {
        return annotationsCount;
    }

    public void setAnnotationsCount(int annotationsCount) {
        this.annotationsCount = annotationsCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
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

    public String getTbURL() {
        return tbURL;
    }

    public void setTbURL(String tbURL) {
        this.tbURL = tbURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    
}
