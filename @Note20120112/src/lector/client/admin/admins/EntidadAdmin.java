package lector.client.admin.admins;

import java.util.ArrayList;

import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.FileException;

public class EntidadAdmin extends Entity {

	public EntidadAdmin(String Namein, Long ID) {
		super(Namein, ID, ID);
	}

	@Override
	public void addSon(Entity entity) throws FileException {

	}

	@Override
	public void removeSon(Entity entity) throws FileException {

	}

	@Override
	public ArrayList<Entity> getSons() throws FileException {
		return new ArrayList<Entity>();
	}

	@Override
	public void setSons(ArrayList<Entity> sons) throws FileException {

	}

	@Override
	public boolean isFolder() {
		return false;
	}

	@Override
	public boolean isType() {
		return false;
	}

}
