package lector.client.catalogo.client;

import java.io.Serializable;
import java.util.ArrayList;

public class File extends Entity implements Serializable {

    private ArrayList<String> tagsIds;
    private ArrayList<Long> annotationsIds;

    public File() {
        super("null", null, null);
    }

    public File(String Namein, Long ID, Long catalogId) {
        super(Namein, ID, catalogId);
        this.tagsIds = new ArrayList<String>();
    }

    public File(String Namein, Long ID, ArrayList<Entity> Lista, Long catalogId) {
        super(Namein, ID, Lista, catalogId);

    }

    @Override
    public boolean isFolder() {
        return false;
    }

    @Override
    public boolean isType() {
        return true;
    }

    public void addSon(Entity entity) throws FileException {
        throw new FileException("You Try to add a son to a Type");
    }

    public void removeSon(Entity entity) throws FileException {
        throw new FileException("You Try to add a son to a Type");
    }

    public ArrayList<Entity> getSons() throws FileException {
        throw new FileException("You Try to get the sons of a Type");
    }

    public void setSons(ArrayList<Entity> sons) throws FileException {
        throw new FileException("You Try to set the sons of a Type");
    }

    @Override
    public Long getCatalogId() {
        return super.getCatalogId();
    }

    @Override
    public void setCatalogId(Long catalogId) {
        super.setCatalogId(catalogId);
    }

    public ArrayList<Long> getAnnotationsIds() {
        return annotationsIds;
    }

    public void setAnnotationsIds(ArrayList<Long> annotationsIds) {
        this.annotationsIds = annotationsIds;
    }

    public ArrayList<String> getTagsIds() {
        return tagsIds;
    }

    public void setTagsIds(ArrayList<String> tagsIds) {
        this.tagsIds = tagsIds;
    }
}
