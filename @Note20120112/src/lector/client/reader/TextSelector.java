package lector.client.reader;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "text_selector")
public class TextSelector implements Serializable, IsSerializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;
    private Long x;
    private Long y;
    private Long width;
    private Long height;


    public TextSelector() {
    }

    public TextSelector(Long x, Long y, Long width, Long height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    public String getTextCoordinates(){
    return ("(" + x + "," + y + ")");

    }
}
