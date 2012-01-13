package lector.client.reader;

import lector.client.catalogo.client.Entity;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ButtonTipo extends Button {

	private Entity Entidad;
	private HorizontalPanel pertenezco;
	
	public ButtonTipo(Entity act, HorizontalPanel horizontalPanel) {
		super(act.getName());
		Entidad=act;
		pertenezco=horizontalPanel;
	}
	
	public ButtonTipo(Entity act, String texto, HorizontalPanel horizontalPanel) {
		super(texto+act.getName());
		Entidad=act;
		pertenezco=horizontalPanel;
	}

	public void setEntidad(Entity a) {
		Entidad = a;
	}
	
	public Entity getEntidad() {
		return Entidad;
	}
	
	public void setPertenezco(HorizontalPanel pertenezco) {
		this.pertenezco = pertenezco;
	}
	
	public HorizontalPanel getPertenezco() {
		return pertenezco;
	}

}
