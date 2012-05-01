package lector.client.login;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import lector.client.controler.Constants;
import lector.client.reader.filter.FilterConfig;

@Entity
@Table(name = "user_app")
@XmlRootElement
public class UserApp implements Serializable, IsSerializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String lastName;
	private String DNI;
	private String email;
	private String profile;
	@Basic
	private ArrayList<Long> groupIds;
	@Basic
	private ArrayList<String> bookIds; // SOLO PARA PROFESORES.
	// DATOS DE GOOGLE.
	@Transient
	private boolean loggedIn = false;
	@Transient
	private String loginUrl;
	@Transient
	private String logoutUrl;
	@Transient
	private boolean isAuthenticated = true;

	@Basic
	@OneToOne(cascade = CascadeType.ALL)
	private FilterConfig filterConfig;

	public UserApp() {
		this.groupIds = new ArrayList<Long>();
	}

	public UserApp(String email, String profile) {
		this();
		this.email = email;
		this.profile = profile;
		if (profile.equals(Constants.PROFESSOR)) {
			this.bookIds = new ArrayList<String>();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public ArrayList<String> getBookIds() {
		return bookIds;
	}

	public void setBookIds(ArrayList<String> bookIds) {
		this.bookIds = bookIds;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Long> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(ArrayList<Long> groupIds) {
		this.groupIds = groupIds;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String DNI) {
		this.DNI = DNI;
	}

	public boolean isIsAuthenticated() {
		return isAuthenticated;
	}

	public void setIsAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	@Override
	public String toString() {
		return "name:" + name + " lastName:" + lastName;
	}

}
