package lector.client.login;

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
@Table(name = "group_app")
public class GroupApp implements Serializable, IsSerializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Basic
    private ArrayList<Long> usersIds;
    @Basic
    private ArrayList<String> bookIds; 

    public GroupApp() {
        this.usersIds = new ArrayList<Long>();
        this.bookIds = new ArrayList<String>();
    }

    public GroupApp(String name) {
        this();
        this.name = name;
    }

    public ArrayList<Long> getUsersIds() {
        return usersIds;
    }

    public void setUsersIds(ArrayList<Long> usersIds) {
        this.usersIds = usersIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<String> getBookIds() {
        return bookIds;
    }

    public void setBookIds(ArrayList<String> bookIds) {
        this.bookIds = bookIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
