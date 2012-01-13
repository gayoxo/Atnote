package lector.client.catalogo;

import lector.client.catalogo.client.Entity;

import com.google.gwt.user.client.ui.Button;

public class ButtonNavigator extends Button {

	Entity elemento;
	
	public ButtonNavigator(String name) {
		super(name);
	}

	public void setElemento(Entity elemento) {
		this.elemento = elemento;
	}
	
	public Entity getElemento() {
		return elemento;
	}
}
