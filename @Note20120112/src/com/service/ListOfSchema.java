package com.service;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lector.client.service.AnnotationSchema;

@Entity
@XmlRootElement
public class ListOfSchema implements Serializable {

    private List<AnnotationSchema> annotationSchemas;

	public ListOfSchema() {
		this.annotationSchemas = new ArrayList<AnnotationSchema>();
	}

	
	public ListOfSchema(List<AnnotationSchema> annotationSchemas) {
		this();
		this.annotationSchemas = annotationSchemas;
	}


	@XmlElement(name = "annotation_schema")
	public List<AnnotationSchema> getAnnotationSchemas() {
		return annotationSchemas;
	}



	public void setAnnotationSchemas(List<AnnotationSchema> annotationSchemas) {
		this.annotationSchemas = annotationSchemas;
	}





}
