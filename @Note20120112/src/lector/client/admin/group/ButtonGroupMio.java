package lector.client.admin.group;

import lector.share.model.UserApp;

import com.google.gwt.user.client.ui.Button;

public class ButtonGroupMio extends Button {

	
	private UserApp Usuario;
	public ButtonGroupMio(UserApp userApp) {
		super();
		Usuario=userApp;
	}
	
	public UserApp getUsuario() {
		return Usuario;
	}
	

}
