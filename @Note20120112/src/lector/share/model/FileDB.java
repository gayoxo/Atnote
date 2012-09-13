package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Basic;
import javax.persistence.Entity;

import javax.persistence.Table;

@Entity
@Table(name = "file")
public class FileDB extends Entry implements Serializable, IsSerializable {

	@Basic
	private ArrayList<Long> annotationsIds;

	public FileDB() {
		super();
		this.annotationsIds = new ArrayList<Long>();
	}

	public FileDB(String name) {
		super(name);
	}

	public FileDB(ArrayList<Long> fathers, String name) {
		super(fathers, name);
	}

	@Override
	public Long getCatalogId() {
		return super.getCatalogId();
	}

	@Override
	public void setCatalogId(Long catalogId) {
		super.setCatalogId(catalogId);
	}

	public ArrayList<Long> getAnnotationsIds() {
		return annotationsIds;
	}

	public void setAnnotationsIds(ArrayList<Long> annotationsIds) {
		this.annotationsIds = annotationsIds;
	}

}
