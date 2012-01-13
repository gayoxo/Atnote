package lector.client.catalogo.server;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import javax.persistence.Table;

@Entity
@Table(name = "entry")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Entry implements Serializable, IsSerializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long fatherId;
    private String name;
    private Long catalogId;

    public Entry() {
    }

    public Entry(Long fatherId, String name) {
        this.fatherId = fatherId;
        this.name = name;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }
    
	private String uppercaseName;
	@PrePersist
	@PreUpdate
	public void prePersist() {
		if (name != null) {
			uppercaseName = name.toUpperCase();
		} else {
			uppercaseName = null;
		}
	}
}
