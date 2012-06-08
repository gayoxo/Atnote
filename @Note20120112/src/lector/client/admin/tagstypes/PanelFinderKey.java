package lector.client.admin.tagstypes;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.FinderKeys;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.server.Catalogo;
import lector.client.controler.ErrorConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class PanelFinderKey extends Composite {
	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private HorizontalPanel horizontalPanel;
	
	public PanelFinderKey(Long long1) {
		
		horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		bookReaderServiceHolder.loadCatalogById(long1, new AsyncCallback<Catalogo>() {
			
			public void onSuccess(Catalogo result) {
				FinderKeys FK=new FinderKeys();
				FK.setCatalogo(Catalog.cloneCatalogo(result));
				horizontalPanel.add(FK);
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert(ErrorConstants.ERROR_RETRIVING_CATALOG);
				
			}
		});
	}

}
