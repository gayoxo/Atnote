package lector.client.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.tools.ant.taskdefs.condition.IsFileSelected;

@Entity
@XmlRootElement
public class AnnotationSchema implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Boolean file;
	@Basic
	private List<Long> sons;

	public AnnotationSchema() {
	}

	public AnnotationSchema(Long id, String name, List<Long> sons,Boolean Folder) {
		super();
		this.id = id;
		this.name = name;
		this.sons = sons;
		file=!Folder;
		
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
	@XmlElement(name = "sons")
	public List<Long> getSons() {
		return sons;
	}

	public void setSons(List<Long> sons) {
		this.sons = sons;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

//	{"annotationSchema":[{"id":"209","name":"gg","sons":["210","211","213"]},{"id":"212","name":"yyy","sons":["213","210"]},{"id":"211","name":"ffft","sons":"212"}]}

	@Override
	public String toString() {
		String output= "{\"id\":\"" + id + "\",\"name\":\"" + name + "\",\"sons\":";
		if(sons.size()> 1){
			output += "[";
		}
		for(int i =0; i < sons.size();i++){
			output += "\"" + sons.get(i);
			if( (i + 1) == sons.size()){
				output += "\"";
			}else{
				output += "\",";
			}
		}
		if(sons.size() > 1){
			output += "]";
		}
		output += "}";
		return output;
	}
	
	public Boolean getFile() {
		return file;
	}

}
