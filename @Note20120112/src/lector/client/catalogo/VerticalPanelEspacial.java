package lector.client.catalogo;

import com.google.gwt.user.client.ui.VerticalPanel;

public class VerticalPanelEspacial extends VerticalPanel {

	private ElementKey EK;

	public VerticalPanelEspacial(ElementKey eK) {
		super();
		EK=eK;
	}
	
	public ElementKey getEK() {
		return EK;
	}
}
