package lector.client.admin.catalog;

import lector.client.catalogo.client.Catalog;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BottonCatalog extends Button {

	private Catalog catalog;
	private VerticalPanel Actual;
	private VerticalPanel Normal;
	private VerticalPanel Selected;
	
	public BottonCatalog(VerticalPanel Normalin,VerticalPanel Selectedin,Catalog catalog) {
		super(catalog.getCatalogName());
		if (catalog.isPrivate()) setHTML("<b>*"+catalog.getCatalogName()+"</b>");
		
		this.catalog=catalog;
		Normal=Normalin;
		Selected=Selectedin;
		Actual=Normal;
		Actual.add(this);
	}
	
	public Catalog getCatalog() {
		return catalog;
	}
	
	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}
	
	
	public void swap()
	{
		if (Actual==Normal)
			{
			Actual.remove(this);
			Actual=Selected;
			Selected.add(this);
			}
		else if (Actual==Selected)
			{
			Selected.remove(this);
			Actual=Normal;
			Normal.add(this);
			}
	}
	
}
