package lector.client.admin.tagstypes;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.catalogo.Finder;
import lector.client.catalogo.client.Entity;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class FolderClickOptions extends PopupPanel {

	private Finder Finder;
	private BotonesStackPanelAdministracionMio Butonpulse;
	private Entity Entidad;

	public FolderClickOptions(Finder finder,BotonesStackPanelAdministracionMio ButtonEntrada,Entity entity) {
		super(true);
		Finder=finder;
		Butonpulse=ButtonEntrada;
		Entidad=entity;
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setSpacing(5);
		setWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		Button btnNewButton = new Button("Select");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Butonpulse.Swap();
				hide();
			}
		});
		horizontalPanel.add(btnNewButton);
		
		Button btnNewButton_1 = new Button("Enter");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				Finder.add2Pad(Entidad);
	        	Finder.RefrescaLosDatos();
	        	hide();
			}
		});
		horizontalPanel.add(btnNewButton_1);
	}

}
