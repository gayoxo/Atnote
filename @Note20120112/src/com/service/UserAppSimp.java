package com.service;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "user_app")
@XmlRootElement
public class UserAppSimp implements Serializable, IsSerializable {

	private String email;
	private String profile;
	private String name;
	private String lastName;
	private String DNI;

	public UserAppSimp() {

	}

	public UserAppSimp(Long id, String email, String profile, String name,
			String lastName, String dNI) {
		super();
		this.email = email;
		this.profile = profile;
		this.name = name;
		this.lastName = lastName;
		this.DNI = dNI;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

}
