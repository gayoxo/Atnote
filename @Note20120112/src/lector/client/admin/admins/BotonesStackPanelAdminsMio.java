package lector.client.admin.admins;

import com.google.gwt.user.client.ui.VerticalPanel;

import lector.client.catalogo.BotonesStackPanelMio;

public class BotonesStackPanelAdminsMio extends BotonesStackPanelMio {




	public BotonesStackPanelAdminsMio(String HTML, VerticalPanel verticalPanel) {
		super(HTML);
		super.setActual(verticalPanel);
	}

	@Override
	public BotonesStackPanelMio Clone() {
		BotonesStackPanelAdminsMio New=new BotonesStackPanelAdminsMio(getHTML(), super.getActual());	
		New.setActual(getActual());
		return New;
	}

}
