package lector.client.admin.tagstypes;

import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class PopUpNewOSelect extends PopupPanel {

	private Catalog Catalogo;
	private Entity Entity;
	
	public PopUpNewOSelect(Catalog CatalogoIn,Entity EntityIn) {
		super(false);
		setModal(true);
		Catalogo=CatalogoIn;
		Entity=EntityIn;
		setGlassEnabled(true);
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(5);
		setWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		Button btnNewButton = new Button("New");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				SelectBetweenFileOrFolderInNew.setCatalog(Catalogo);
				SelectBetweenFileOrFolderInNew SBFF=new SelectBetweenFileOrFolderInNew(Entity);
				SBFF.center();
				SBFF.setModal(true);
				hide();
			}
		});
		horizontalPanel.add(btnNewButton);
		
		Button btnNewButton_1 = new Button("From Exist");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PopUpFinderSelectorExist PAFSE=new PopUpFinderSelectorExist(Catalogo,Entity);
				PAFSE.center();
				PAFSE.setModal(true);
				hide();
			}
		});
		horizontalPanel.add(btnNewButton_1);
	}

}
