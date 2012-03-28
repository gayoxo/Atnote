package lector.client.admin.tagstypes;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.BotonesStackPanelMio;
import lector.client.catalogo.client.Entity;
import lector.client.controler.Constants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClickHandlerMioSeleccion extends ClickHandlerMio implements
		ClickHandler {
	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private PopUpFinderSelectorExist PFSE;
	
	
	public ClickHandlerMioSeleccion(PopUpFinderSelectorExist PFSEin) {
		PFSE=PFSEin;
	}
	
	public void onClick(ClickEvent event) {
		Entity E=((BotonesStackPanelAdministracionMio)event.getSource()).getEntidad();
		AsyncCallback<Void> LLamada=new AsyncCallback<Void>() {
			
			public void onSuccess(Void result) {
				EditorTagsAndTypes.LoadBasicTypes();
				PFSE.hide();
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Error in copy in folder");
				
			}
		};
		
			bookReaderServiceHolder.addFather(E.getID(), PFSE.getFather().getID(), LLamada);

	}
	
	public void onClickMan(BotonesStackPanelMio event) {
		Entity BS = ((BotonesStackPanelAdministracionMio) event).getEntidad();
AsyncCallback<Void> LLamada=new AsyncCallback<Void>() {
			
			public void onSuccess(Void result) {
				EditorTagsAndTypes.LoadBasicTypes();
				PFSE.hide();
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Error in copy in folder");
				
			}
		};
		if (PFSE.getFather()==null)
		bookReaderServiceHolder.addFather(BS.getID(), Constants.CATALOGID, LLamada);
		else 
			bookReaderServiceHolder.addFather(BS.getID(), PFSE.getFather().getID(), LLamada);

	}

}
