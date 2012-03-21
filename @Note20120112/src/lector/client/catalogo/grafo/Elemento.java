package lector.client.catalogo.grafo;

import lector.client.catalogo.client.Entity;

public class Elemento {

	private Tipo tipo;
	private Entity E;
	private String name;
	private String Coordenadas;
	
	public Elemento(String Entrada) {
		
		String[] corte=Entrada.split("\"");
		generatipo(corte[3]);
		if (name!=null)
			findentidad(name);
		generacoordenadas(corte[10]);
		
	}

	private void findentidad(String name2) {
		E=null;
		
	}

	private void generacoordenadas(String string) {
		String[] corte=string.split("\\[");
		corte=corte[1].split("\\]");
		Coordenadas=corte[0];
	}

	private void generatipo(String string) {
		Integer i=null;
		try {
			i=Integer.parseInt(string);
		} catch (Exception e) {
			// nada
		}
		
		if (i!=null)
		{
		name=Integer.toString(i);
		tipo=Tipo.Type;
		}
		else 
		{
		name=null;
		tipo=Tipo.Edge;
		}
		
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Entity getE() {
		return E;
	}

	public void setE(Entity e) {
		E = e;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoordenadas() {
		return Coordenadas;
	}

	public void setCoordenadas(String coordenadas) {
		Coordenadas = coordenadas;
	}
	
	
	
}
