package lector.client.catalogo.client;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Entity implements Serializable {

    protected ArrayList<Entity> Sons;
    private String Name;
    private Entity Father;
    private Long ID;
    private Long catalogId;

    public Entity(String Namein, Long ID, Long catalogId) {
        Sons = new ArrayList<Entity>();
        Name = Namein;
        Father = null;
        this.ID = ID;
        this.catalogId = catalogId;
    }

    public Entity(String Namein, Long ID, ArrayList<Entity> Sonsin, Long catalogId) {
        this(Namein, ID, catalogId);
        if (Sonsin != null) {
            Sons = Sonsin;
        }
    }

    public abstract void addSon(Entity entity) throws FileException;

    public abstract void removeSon(Entity entity) throws FileException;

    public abstract ArrayList<Entity> getSons() throws FileException;

    public abstract void setSons(ArrayList<Entity> sons) throws FileException;

    public Entity getFather() {
        return Father;
    }

    public void setFather(Entity father) {
        Father = father;
    }

    public boolean isRootFather() {
        return Father == null;
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
        if ((Father != null) || (Father == FutureFather)) {
            return false;
        }
        int contador = 0;
        while (!Resultado && (contador < Sons.size())) {
            Resultado = (FutureFather == Sons.get(contador)) || Sons.get(contador).isSon(FutureFather);
        }
        return Resultado;
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
}
