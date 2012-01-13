package lector.client.admin.tagstypes;

import java.util.ArrayList;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.reader.IlegalFolderFusionException;
import lector.client.reader.LoadingPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class MergeSelector extends PopupPanel {

	private ListBox comboBox;
	private ArrayList<BotonesStackPanelAdministracionMio> ListaCombo;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private PopupPanel Yo;
	private static Catalog catalog;

	public MergeSelector(
			ArrayList<BotonesStackPanelAdministracionMio> ListaComboIn) {
		super(true);
		this.ListaCombo = ListaComboIn;
		Yo = this;
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setWidget(verticalPanel);
		verticalPanel.setSize("235px", "136px");

		Label lblNewLabel = new Label("Select the destination of the Merge");
		verticalPanel.add(lblNewLabel);

		comboBox = new ListBox();
		comboBox.setSelectedIndex(1);
		verticalPanel.add(comboBox);
		comboBox.setWidth("149px");

		Button btnNewButton = new Button("Select");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Yo.hide();
				String Destino = comboBox.getItemText(comboBox
						.getSelectedIndex());
				int counter = 0;
				boolean found = false;
				while (counter < ListaCombo.size() && !found) {
					found = ListaCombo.get(counter).getText().equals(Destino);
					counter++;
				}
				counter--;
				Entity DestinoEn = ListaCombo.get(counter).getEntidad();
				for (BotonesStackPanelAdministracionMio texto : ListaCombo) {
					if (!(texto.getText().equals(Destino))) {
						UnirTypos(texto.getEntidad(), DestinoEn);

					}
				}

			}

			private void UnirTypos(Entity entity, Entity destinoEn) {
				AsyncCallback<Void> callback = new AsyncCallback<Void>() {

					public void onFailure(Throwable caught) {
					
						if (caught instanceof IlegalFolderFusionException) {
							Window.alert(((IlegalFolderFusionException) caught)
									.getErrorMessage());
						} else {
							Window.alert("Error in Merge");
						}
						// lo añadi
						LoadingPanel.getInstance().hide();
					//	Yo.hide();
					}

					public void onSuccess(Void result) {
						LoadingPanel.getInstance().hide();
						EditorTagsAndTypes.LoadBasicTypes();
					}
				};
				AsyncCallback<Integer> callback2 = new AsyncCallback<Integer>() {

					public void onSuccess(Integer result) {
						LoadingPanel.getInstance().hide();
						EditorTagsAndTypes.LoadBasicTypes();

					}

					public void onFailure(Throwable caught) {
						Window.alert("Error in Merge");

					}
				};

				LoadingPanel.getInstance().setLabelTexto("Saving...");
				LoadingPanel.getInstance().center();
				if ((entity instanceof Folder) && (destinoEn instanceof Folder))
					bookReaderServiceHolder.fusionFolder(entity.getID(),
							destinoEn.getID(), callback);
				else if ((entity instanceof File)
						&& (destinoEn instanceof File))
					bookReaderServiceHolder.fusionFiles(entity.getID(),
							destinoEn.getID(), callback2);
				else
					Window.alert("The elements you're trying to merge are differents");
			}

		});

		verticalPanel.add(btnNewButton);

		for (BotonesStackPanelAdministracionMio texto : ListaCombo) {
			comboBox.addItem(texto.getText());
		}
	}

	public static void setCatalog(Catalog catalog) {
		MergeSelector.catalog = catalog;
	}

	public static Catalog getCatalog() {
		return catalog;
	}

}
