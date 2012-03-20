package com.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

import lector.client.catalogo.server.Entry;
import lector.client.login.UserApp;

@Entity
@XmlRootElement
public class AnnotationToExport implements Serializable {

	@Basic
	private UserAppSimp user;
	@Basic
	private BookSimp book;
	@Basic
	private AnnotationSimp annotationSimp;

	// @Basic
	// @OneToMany
	// private ArrayList<String> metaTree;

	public AnnotationToExport() {

	}

	public AnnotationToExport(UserAppSimp user, BookSimp book,
			AnnotationSimp annotationSimp) {
		super();
		this.user = user;
		this.book = book;
		this.annotationSimp = annotationSimp;
	}

	public UserAppSimp getUser() {
		return user;
	}

	public void setUser(UserAppSimp user) {
		this.user = user;
	}

	public BookSimp getBook() {
		return book;
	}

	public void setBook(BookSimp book) {
		this.book = book;
	}

	public AnnotationSimp getAnnotationSimp() {
		return annotationSimp;
	}

	public void setAnnotationSimp(AnnotationSimp annotationSimp) {
		this.annotationSimp = annotationSimp;
	}

}
