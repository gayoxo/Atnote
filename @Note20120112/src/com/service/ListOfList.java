package com.service;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ListOfList implements Serializable {

	private List<ListOfSchema> listOfList;

	public ListOfList() {
		this.listOfList = new ArrayList<ListOfSchema>();
	}

	public ListOfList(List<ListOfSchema> listOfList) {
		this();
		this.listOfList = listOfList;
	}

	@XmlElement(name = "list_of_schemas")
	public List<ListOfSchema> getListOfList() {
		return listOfList;
	}

	public void setListOfList(List<ListOfSchema> listOfList) {
		this.listOfList = listOfList;
	}

}
