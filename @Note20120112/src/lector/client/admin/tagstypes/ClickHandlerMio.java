package lector.client.admin.tagstypes;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.catalogo.BotonesStackPanelMio;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class ClickHandlerMio implements ClickHandler {



		public void onClick(ClickEvent event) {
			BotonesStackPanelAdministracionMio BS = ((BotonesStackPanelAdministracionMio) event
					.getSource());
			BS.Swap();

		}
		
		public void onClickMan(BotonesStackPanelMio event) {
			BotonesStackPanelAdministracionMio BS = (BotonesStackPanelAdministracionMio) event;
			BS.Swap();

		}
		


}
