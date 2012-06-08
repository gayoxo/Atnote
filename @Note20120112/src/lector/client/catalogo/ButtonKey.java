package lector.client.catalogo;

import com.google.gwt.user.client.ui.Button;

public class ButtonKey extends Button {

	private ElementKey padreKey;
	
	public ButtonKey(String string, ElementKey elementKey) {
		super(string);
		padreKey=elementKey;
	}

	public ElementKey getPadreKey() {
		return padreKey;
	}

	public void setPadreKey(ElementKey padreKey) {
		this.padreKey = padreKey;
	}
	


}
