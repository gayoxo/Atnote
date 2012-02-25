package lector.client.reader;

import lector.client.catalogo.Finder;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;
import lector.client.login.ActualUser;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class PopUpNewOSelect extends PopupPanel {

	private Catalog Catalogo;
	private Entity Entity;
	private Finder finder;
	
	public PopUpNewOSelect(Catalog CatalogoIn,Entity EntityIn, Finder finderin) {
		super(false);
		setModal(true);
		Catalogo=CatalogoIn;
		Entity=EntityIn;
		finder=finderin;
		setGlassEnabled(true);
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(5);
		setWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		Button btnNewButton = new Button(ActualUser.getLanguage().getNew());
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				SelectorNewBetweenTypeAndFolder SBFF=new SelectorNewBetweenTypeAndFolder(finder);
				SBFF.center();
				SBFF.setModal(true);
				hide();
			}
		});
		horizontalPanel.add(btnNewButton);
		
		Button btnNewButton_1 = new Button(ActualUser.getLanguage().getFromExist());
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PopUpFinderSelectorExistAnnotation PAFSE=new PopUpFinderSelectorExistAnnotation(Catalogo,Entity,finder);
				PAFSE.center();
				PAFSE.setModal(true);
				hide();
			}
		});
		horizontalPanel.add(btnNewButton_1);
	}

}
