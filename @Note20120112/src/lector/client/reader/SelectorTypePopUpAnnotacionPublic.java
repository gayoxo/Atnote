package lector.client.reader;

import lector.client.catalogo.client.Catalog;
import lector.client.controler.Constants;
import lector.client.login.ActualUser;
import lector.client.reader.PanelTextComent.CatalogTipo;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class SelectorTypePopUpAnnotacionPublic extends
		SelectorTypePopUpAnnotacion {

	public SelectorTypePopUpAnnotacionPublic(HorizontalPanel penelBotonesTipo,Catalog Cata, CatalogTipo catalog2) {
		super(penelBotonesTipo,Cata, catalog2);
		setAllowCreate(true);
	}

}
