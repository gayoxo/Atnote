package lector.client.reader;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.admin.tagstypes.ClickHandlerMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.BotonesStackPanelMio;
import lector.client.catalogo.Finder;
import lector.client.catalogo.client.Entity;
import lector.client.controler.Constants;
import lector.client.login.ActualUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClickHandlerMioSelectorExist extends ClickHandlerMio implements
		ClickHandler {
	
	private PopUpFinderSelectorExistAnnotation popUpFinderSelectorExistAnnotation;
	private Finder finderrefresh;
	private Entity father;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	
	public ClickHandlerMioSelectorExist(PopUpFinderSelectorExistAnnotation popUpFinderSelectorExistAnnotation, Finder finderrefresh,
			Entity father) {
		 this.popUpFinderSelectorExistAnnotation=popUpFinderSelectorExistAnnotation;
		this.finderrefresh=finderrefresh;
		this.father=father;
	}

	@Override
	public void onClickMan(BotonesStackPanelMio event) {
	
				Entity E=((BotonesStackPanelAdministracionMio)event).getEntidad();
				AsyncCallback<Void> LLamada=new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
//						finderrefresh.RefrescaLosDatos();
//						//	scrollPanel.setWidget(finder);
//						finderrefresh.setSize("100%","100%");
						finderrefresh.RefrescaLosDatos();
						SelectorTypePopUpAnnotacion.RestoreFinderButtonActio();
						//	scrollPanel.setWidget(finder);
						//finderrefresh.setSize("100%","100%");
						popUpFinderSelectorExistAnnotation.hide();
						
					}
					
					public void onFailure(Throwable caught) {
						Window.alert(ActualUser.getLanguage().getE_Saving());
						
					}
				};
				if (father==null)
				bookReaderServiceHolder.addFather(E.getID(), Constants.CATALOGID, LLamada);
				else 
					bookReaderServiceHolder.addFather(E.getID(), father.getID(), LLamada);
				
			}

}
