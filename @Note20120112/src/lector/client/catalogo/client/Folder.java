package lector.client.catalogo.client;

import java.io.Serializable;
import java.util.ArrayList;

public class Folder extends Entity implements Serializable {

    public Folder() {
        super("null", null, null);
    }

    public Folder(String Namein, Long ID, Long catalogId) {
        super(Namein, ID, catalogId);
    }

    public Folder(String Namein, Long ID, ArrayList<Entity> Lista, Long catalogId) {
        super(Namein, ID, Lista, catalogId);
    }

    @Override
    public boolean isFolder() {
        return true;
    }

    @Override
    public boolean isType() {
        return false;
    }

    public void addSon(Entity entity) throws FileException {
        entity.getFathers().add(this);
        Sons.add(entity);

    }

    public void removeSon(Entity entity) throws FileException {
        Sons.remove(entity);
    }

    public ArrayList<Entity> getSons() throws FileException {
        return Sons;
    }

    public void setSons(ArrayList<Entity> sons) throws FileException {
        if (sons == null) {
            sons = new ArrayList<Entity>();
        }
        for (Entity entity : sons) {
            entity.getFathers().add(this);
        }
        Sons = sons;
    }

    @Override
    public Long getCatalogId() {
        return super.getCatalogId();
    }

    @Override
    public void setCatalogId(Long catalogId) {
        super.setCatalogId(catalogId);
    }
}
