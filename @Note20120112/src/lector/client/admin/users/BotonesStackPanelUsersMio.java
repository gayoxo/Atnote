package lector.client.admin.users;

import com.google.gwt.user.client.ui.VerticalPanel;

import lector.client.catalogo.BotonesStackPanelMio;

public class BotonesStackPanelUsersMio extends BotonesStackPanelMio {




	public BotonesStackPanelUsersMio(String HTML, VerticalPanel verticalPanel) {
		super(HTML);
		super.setActual(verticalPanel);
	}

	@Override
	public BotonesStackPanelMio Clone() {
		BotonesStackPanelUsersMio New=new BotonesStackPanelUsersMio(getHTML(), super.getActual());	
		New.setActual(getActual());
		return New;
	}

}
