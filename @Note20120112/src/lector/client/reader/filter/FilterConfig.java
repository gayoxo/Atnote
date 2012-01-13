package lector.client.reader.filter;

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

@Entity
@Table(name = "filter_config")
public class FilterConfig implements Serializable, IsSerializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;
    @Basic
    private ArrayList<String> tags;
    @Basic
    private ArrayList<Long> annotationTypes;

    public FilterConfig() {
        this.tags = new ArrayList<String>();
        this.annotationTypes = new ArrayList<Long>();
    }

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public ArrayList<Long> getAnnotationTypes() {
        return annotationTypes;
    }

    public void setAnnotationTypes(ArrayList<Long> annotationTypes) {
        this.annotationTypes = annotationTypes;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
