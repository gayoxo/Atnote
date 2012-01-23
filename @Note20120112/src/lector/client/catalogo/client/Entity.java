package lector.client.catalogo.client;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Entity implements Serializable {

	protected ArrayList<Entity> Sons;
	private String Name;
	private ArrayList<Entity> fathers;
	private Long ID;
	private Long catalogId;
	private Entity actualFather;

	public Entity(String Namein, Long ID, Long catalogId) {
		Sons = new ArrayList<Entity>();
		Name = Namein;
		fathers = new ArrayList<Entity>();
		this.ID = ID;
		this.catalogId = catalogId;
	}

	public Entity(String Namein, Long ID, ArrayList<Entity> Sonsin,
			Long catalogId) {
		this(Namein, ID, catalogId);
		if (Sonsin != null) {
			Sons = Sonsin;
		}
	}

	public abstract void addSon(Entity entity) throws FileException;

	public abstract void removeSon(Entity entity) throws FileException;

	public abstract ArrayList<Entity> getSons() throws FileException;

	public abstract void setSons(ArrayList<Entity> sons) throws FileException;

	public ArrayList<Entity> getFathers() {
		return fathers;
	}

	public void setFathers(ArrayList<Entity> fathers) {
		this.fathers = fathers;
	}

	public boolean isRootFather() {
		return fathers.isEmpty();
	}

	public abstract boolean isFolder();

	public abstract boolean isType();

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public boolean isSon(Entity FutureFather) {
		boolean Resultado = (FutureFather == this);
		if ((actualFather != null) || isInFathers(FutureFather)) {
			return false;
		}
		int contador = 0;
		while (!Resultado && (contador < Sons.size())) {
			Resultado = (FutureFather == Sons.get(contador))
					|| Sons.get(contador).isSon(FutureFather);
		}
		return Resultado;
	}

	private boolean isInFathers(Entity futureFather) {
		for (Entity father : fathers) {
			if(father.equals(futureFather)){
				return true;
			}
		}
		return false;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long ID) {
		this.ID = ID;
	}

	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}

	public Entity getActualFather() {
		return actualFather;
	}

	public void setActualFather(Entity actualFather) {
		this.actualFather = actualFather;
	}

}
