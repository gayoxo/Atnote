package lector.client.admin.export.admin;

import com.google.gwt.user.client.ui.VerticalPanel;

import lector.client.admin.export.template.Template;
import lector.client.admin.export.template.TemplateCategory;

public class ElementoLlamada {

	private RepresentacionTemplateCategory T;
	private VerticalPanel Contenedor;
	
	public ElementoLlamada(RepresentacionTemplateCategory t, VerticalPanel contenedor) {
		T=t;
		Contenedor=contenedor;
	}

	public RepresentacionTemplateCategory getT() {
		return T;
	}

	public void setT(RepresentacionTemplateCategory t) {
		T = t;
	}

	public VerticalPanel getContenedor() {
		return Contenedor;
	}

	public void setContenedor(VerticalPanel contenedor) {
		Contenedor = contenedor;
	}
	
	
	
}
