package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import lector.client.controler.Constants;

@Entity
@Table(name = "reading_activity")
public class ReadingActivity implements Serializable, IsSerializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String languageName;
	private String name;
	private String bookId;
	private Long groupId;
	private Long professorId;
	private Long catalogId;
	private Long openCatalogId;
	private String Visualizacion;
	private Long TemplateId;
	private Boolean TemplateLibre;

	public ReadingActivity() {
	}

	public ReadingActivity(String languageName, String bookId, Long groupId,
			Long professorId, Long catalogId,String visualization ) {
		this.languageName = languageName;
		this.bookId = bookId;
		this.groupId = groupId;
		this.professorId = professorId;
		this.catalogId = catalogId;
		this.TemplateLibre=true;
		this.Visualizacion=visualization;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getOpenCatalogId() {
		return openCatalogId;
	}
	
	public void setOpenCatalogId(Long openCatalogId) {
		this.openCatalogId = openCatalogId;
	}

	public String getVisualizacion() {
		return Visualizacion;
	}

	public void setVisualizacion(String visualizacion) {
		Visualizacion = visualizacion;
	}

	public Long getTemplateId() {
		return TemplateId;
	}

	public void setTemplateId(Long templateId) {
		TemplateId = templateId;
	}

	public Boolean getTemplateLibre() {
		return TemplateLibre;
	}

	public void setTemplateLibre(Boolean templateLibre) {
		TemplateLibre = templateLibre;
	}
	
	
}
