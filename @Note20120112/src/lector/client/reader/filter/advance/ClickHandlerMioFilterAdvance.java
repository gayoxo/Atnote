package lector.client.reader.filter.advance;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.admin.tagstypes.ClickHandlerMio;
import lector.client.browser.BotonesStackPanelBrowser;
import lector.client.catalogo.BotonesStackPanelMio;

import com.google.gwt.event.dom.client.ClickHandler;

public class ClickHandlerMioFilterAdvance extends ClickHandlerMio implements
		ClickHandler {

	
	
	public void onClickMan(BotonesStackPanelMio event) {
		Rule R=FilterAdvance.getActualRule();
		if (R!=null)
			{
			BotonesStackPanelBrowser BSPB=(BotonesStackPanelBrowser) event;
			AssertRule A= new AssertRule((BSPB).getEntidad().getName(),
					R.getRulePanel(),
					(BSPB).getEntidad().getID(),
					Tiposids.Tipo);
			R.addAssertRule(A);
			}

	}
	
}
