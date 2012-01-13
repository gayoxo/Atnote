package lector.client.admin.activity.buttons;

import lector.client.login.GroupApp;

import com.google.gwt.user.client.ui.Button;

public class Botongroups extends Button {
 
	
	private GroupApp grupo;
	
	public Botongroups(GroupApp grupoin) {
		super(grupoin.getName());
		grupo=grupoin;
		
	}
	
	
	public GroupApp getGrupo() {
		return grupo;
	}
	
	public void setGrupo(GroupApp grupo) {
		this.grupo = grupo;
	}
}
