package lector.client.reader;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class PopUpFicha extends PopupPanel {

	public PopUpFicha(Composite A) {
		super(true);
		setAnimationEnabled(true);
		SimplePanel SP=new SimplePanel();
		add(SP);
		SP.add(A);
	}
}
