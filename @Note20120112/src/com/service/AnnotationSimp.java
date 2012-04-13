package com.service;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

import lector.client.reader.TextSelector;

@Entity
@XmlRootElement
@Table(name = "annotation")
public class AnnotationSimp implements Serializable, IsSerializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Boolean visibility = false;
	private Integer pageNumber;
	@Basic
	@OneToOne(cascade = CascadeType.ALL)
	private ArrayList<TextSelector> textSelector;
	@Basic
	private String comment;
	@Basic
	private List<FileToExport> fileIds;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date createdDate;

	public AnnotationSimp() {
	}

	public AnnotationSimp(Long id, Boolean visibility, Integer pageNumber,
			ArrayList<TextSelector> textSelector, String comment,
			List<FileToExport> files, Date createdDate) {
		super();
		this.id = id;
		this.visibility = visibility;
		this.pageNumber = pageNumber;
		this.textSelector = textSelector;
		this.fileIds = files;
		this.comment = comment;
		this.createdDate = createdDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public ArrayList<TextSelector> getTextSelector() {
		return textSelector;
	}

	public void setTextSelector(ArrayList<TextSelector> textSelector) {
		this.textSelector = textSelector;
	}

	public List<FileToExport> getFileIds() {
		return fileIds;
	}

	public void setFileIds(List<FileToExport> fileIds) {
		this.fileIds = fileIds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getVisibility() {
		return visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

}
