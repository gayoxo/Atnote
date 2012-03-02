package lector.client.browser;

import com.google.gwt.user.client.ui.VerticalPanel;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.catalogo.BotonesStackPanelMio;
import lector.client.catalogo.Finder;
import lector.client.catalogo.client.Entity;

public class BotonesStackPanelBrowser extends
		BotonesStackPanelAdministracionMio {
	
	public BotonesStackPanelBrowser(String HTML, VerticalPanel Normal,
			VerticalPanel Selected,Finder  F) {
		super(HTML, Normal, Selected,F);

	}
	
	public BotonesStackPanelMio Clone()
	{
		BotonesStackPanelBrowser New=new BotonesStackPanelBrowser(getHTML(), super.getNormal(), super.getSelected(),super.getF());	
	New.setActual(getActual());
	return New;
	}
	

}
