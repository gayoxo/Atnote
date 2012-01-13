package lector.client.catalogo.Tree;

import com.google.gwt.user.client.ui.TreeItem;

import lector.client.catalogo.client.Entity;

public class Node extends TreeItem{

	private Entity Entidad;
	private String Text;
	
	public Node(Entity entitynew) {
		this.Entidad=entitynew;
	}
	
	public Entity getEntidad() {
		return Entidad;
	}
	

	
	public void setEntidad(Entity entidad) {
		Entidad = entidad;
	}
	
	public void setTextNodo(String text) {
		// TODO Auto-generated method stub
		super.setText(text);
	}
	
	
	public void setHTML(String S,String Text) {
		this.Text=Text;
		setHTML("<img src=\""+ S +"\">"+ Text);
	}
	
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return Text;
	}

}
