package lector.client.catalogo.grafo;

import java.util.ArrayList;

import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.service.AnnotationSchema;

public class Elemento {

	private Tipo tipo;
	private Entity E;
	private String name;
	private String Coordenadas;
	private static ArrayList<AnnotationSchema> Lista;
	private static Long Catalogo;
	
	public Elemento(String Entrada) {
		
		String[] corte=Entrada.split("\"");
		generatipo(corte[3]);
		if (name!=null)
			findentidad(name);
		generacoordenadas(corte[10]);
		
	}

	private void findentidad(String name2) {
		boolean encontrado=false;
		int i=0;
		while ((!encontrado)&&(i<Lista.size()))
		{
			if (name2.equals(Lista.get(i).getId().toString()))
			{
			encontrado=true;	
			AnnotationSchema A=Lista.get(i);
				//TODO Necesito que la gerarquia contenga el tipo!!!!!
			if (A.getFile())
				{
				E=new File(A.getName(), A.getId(), Catalogo);
				}
			else
			{
				E=new Folder(A.getName(), A.getId(), Catalogo);
			}
			}
			i++;
		}
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
	
	

	public static void setLista(ArrayList<AnnotationSchema> lista) {
		Lista = lista;
	}
	
	public static void setCatalogo(Long catalogo) {
		Catalogo = catalogo;
	}
}
