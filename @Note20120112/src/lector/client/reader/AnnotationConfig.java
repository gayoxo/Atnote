package lector.client.reader;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import lector.client.controler.Constants;

@Entity
@Table(name = "annotation_config")
public class AnnotationConfig implements Serializable, IsSerializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;
    private String visibility;
    private String updatability;
    private String type;
    @Basic
    private ArrayList<Long> visibilityGroupIds;
    @Basic
    private ArrayList<Long> updatableGroupIds;

    public AnnotationConfig() {
        this.visibilityGroupIds = new ArrayList<Long>();
        this.updatableGroupIds = new ArrayList<Long>();
        this.visibility = Constants.ANNOTATION_PRIVATE;
        this.updatability = Constants.ANNOTATION_PRIVATE;
    }

    public AnnotationConfig(String visibility, String updatability, String type) {
        this();
        this.visibility = visibility;
        this.updatability = updatability;
        this.type = type;
    }

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdatability() {
        return updatability;
    }

    public void setUpdatability(String updatability) {
        this.updatability = updatability;
    }

    public ArrayList<Long> getUpdatableGroupIds() {
        return updatableGroupIds;
    }

    public void setUpdatableGroupIds(ArrayList<Long> updatableGroupIds) {
        this.updatableGroupIds = updatableGroupIds;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public ArrayList<Long> getVisibilityGroupIds() {
        return visibilityGroupIds;
    }

    public void setVisibilityGroupIds(ArrayList<Long> visibilityGroupIds) {
        this.visibilityGroupIds = visibilityGroupIds;
    }
}
