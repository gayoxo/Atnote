package lector.client.login.bookselec;

import lector.share.model.ReadingActivity;

import com.google.gwt.user.client.ui.Button;

public class ButtonActivityReader extends Button {
private ReadingActivity RA;


public ButtonActivityReader(ReadingActivity Rain) {
	super(Rain.getName());
	this.RA=Rain;
	
}

public void setRA(ReadingActivity rA) {
	RA = rA;
}

public ReadingActivity getRA() {
	return RA;
}
}
