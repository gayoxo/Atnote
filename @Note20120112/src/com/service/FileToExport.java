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
public class FileToExport implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Long catalogId;

	public FileToExport() {

	}

	public FileToExport(Long id, String name, Long catalogId) {
		super();
		this.id = id;
		this.name = name;
		this.catalogId = catalogId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}

}
