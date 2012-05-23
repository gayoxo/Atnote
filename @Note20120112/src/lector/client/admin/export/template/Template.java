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
@Table(name = "template")
public class Template implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private boolean modifyable;
	@Basic
	private ArrayList<Long> categories = new ArrayList<Long>();
	private Long userApp;

	public Long getUserApp() {
		return userApp;
	}

	public Template(String name, boolean modifyable,
			ArrayList<Long> categories, Long userApp) {
		this();
		this.name = name;
		this.modifyable = modifyable;
		this.categories = categories;
		this.userApp = userApp;
	}

	public void setUserApp(Long userApp) {
		this.userApp = userApp;
	}

	public Template() {
		categories = new ArrayList<Long>();
		modifyable = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isModifyable() {
		return modifyable;
	}

	public void setModifyable(boolean modifyable) {
		this.modifyable = modifyable;
	}

	public ArrayList<Long> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<Long> categories) {
		this.categories = categories;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
