package lector.client.catalogo;

import lector.client.catalogo.client.Entity;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class BotonesStackPanelMio extends Button {
	
	
	protected VerticalPanel Actual;
	protected Entity Entidad;
	protected String Text;
	
	public BotonesStackPanelMio(String HTML) {
		super(HTML);
		Text=HTML;
	}
	


	public abstract BotonesStackPanelMio Clone();
	
	
	
	public void setActual(VerticalPanel actual) {
		if (Actual!=null)Actual.remove(this);
		Actual = actual;
		Actual.add(this);
	}
	
	public VerticalPanel getActual() {
		return Actual;
	}
	
	public Entity getEntidad() {
		return Entidad;
	}
	
	public void setEntidad(Entity entidad) {
		Entidad = entidad;
	}
	
	public void setIcon(String S,String Text)
	{
		this.Text=Text;
		setHTML("<img src=\""+ S +"\">"+ Text);
	}
	
	@Override
	public String getHTML() {
		
		return Text;
	}

	@Override
	public String getText() {
	
		return Text;
	}
	
	
}
