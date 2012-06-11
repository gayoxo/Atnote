package lector.client.admin.export.template;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "template_category")
public class TemplateCategory implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private ArrayList<Long> subCategories = new ArrayList<Long>();
	@Basic
	private ArrayList<Long> annotationsIds = new ArrayList<Long>();
	private Long fatherId;
	private Long templateId;
	private Integer order = 0;

	public TemplateCategory() {
		subCategories = new ArrayList<Long>();
		annotationsIds = new ArrayList<Long>();
	}

	public TemplateCategory(String name, ArrayList<Long> subCategories,
			ArrayList<Long> annotationsIds, Long fatherId, Long templateId) {
		super();
		this.name = name;
		this.subCategories = subCategories;
		this.annotationsIds = annotationsIds;
		this.fatherId = fatherId;
		this.templateId = templateId;
	}

	public ArrayList<Long> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(ArrayList<Long> subCategories) {
		this.subCategories = subCategories;
	}

	public ArrayList<Long> getAnnotationsIds() {
		return annotationsIds;
	}

	public void setAnnotationsIds(ArrayList<Long> annotationsIds) {
		this.annotationsIds = annotationsIds;
	}

	public Long getFatherId() {
		return fatherId;
	}

	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
