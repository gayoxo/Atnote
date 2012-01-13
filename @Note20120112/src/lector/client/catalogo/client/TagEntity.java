package lector.client.catalogo.client;

import java.util.ArrayList;

public class TagEntity extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TagEntity(String Namein, Long ID,Long IDpadre) {
		super(Namein, ID, IDpadre);
	}

	@Override
	public void addSon(Entity entity) throws FileException {
		

	}

	@Override
	public void removeSon(Entity entity) throws FileException {


	}

	@Override
	public ArrayList<Entity> getSons() throws FileException {

		return null;
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
