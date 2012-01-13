package lector.client.admin.activity.buttons;

import lector.client.catalogo.client.Catalog;

import com.google.gwt.user.client.ui.Button;

public class Botoncatalogo extends Button {
 
	
	private Catalog catalogo;
	public Botoncatalogo(Catalog catalogin) {
		super(catalogin.getCatalogName());
		if (catalogin.isPrivate()) setHTML("<b>*"+catalogin.getCatalogName()+"</b>");
		catalogo=catalogin;
		
	}
	
	
	public Catalog getCatalogo() {
		return catalogo;
	}
	
	public void setCatalogo(Catalog catalogo) {
		this.catalogo = catalogo;
	}
}
