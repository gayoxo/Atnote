package lector.client.browser;

import lector.client.admin.tagstypes.ClickHandlerMio;
import lector.client.catalogo.BotonesStackPanelMio;
import lector.client.catalogo.Finder;
import lector.client.catalogo.client.Entity;
import lector.client.reader.filter.advance.AssertRule;
import lector.client.reader.filter.advance.FilterAdvance;
import lector.client.reader.filter.advance.Rule;
import lector.client.reader.filter.advance.Tiposids;

import com.google.gwt.event.dom.client.ClickHandler;

public class ClickHandlerMioFilterBrowser extends ClickHandlerMio implements
		ClickHandler {

	
	
	private Finder Finderparent;

	public ClickHandlerMioFilterBrowser(Finder Finder) {
		this.Finderparent=Finder;
	}
	
	
	public void onClickMan(BotonesStackPanelMio event) {

			BotonesStackPanelBrowser BS = (BotonesStackPanelBrowser) event;
			if (!EqualsFinderButton(BS))
				{
				BS.Swap();
				}
			
			if (Browser.getSelectedB().getWidgetCount()==0)Browser.getBtnNewButton().setVisible(false);
			else Browser.getBtnNewButton().setVisible(true);

	}
	
	private boolean EqualsFinderButton(BotonesStackPanelBrowser bS) {
		for (Entity entity : bS.getEntidad().getFathers()) {
			if (Finderparent.getTopPath().getID().equals(entity.getID())) return true;
		}		
		return false;
	}
}
