package lector.client.reader.filter.advance;

import com.google.gwt.user.client.ui.Button;

public class ButtonUser extends Button {

	private Long ID;
	
	public ButtonUser(String email,Long iD) {
		super(email);
		ID=iD;
	}

	public Long getID() {
		return ID;
	}
	
	public void setID(Long iD) {
		ID = iD;
	}
}

