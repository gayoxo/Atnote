package lector.client.admin.tagstypes;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Grid;
import lector.client.admin.tagstypes.SelectBetweenFileOrFolderInNew.whatsthenew;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.FileException;
import lector.client.catalogo.client.Folder;
import lector.client.catalogo.client.FolderException;
import lector.client.controler.Constants;
import lector.client.reader.LoadingPanel;

public class NewTyperAdmin extends PopupPanel {

	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private PopupPanel Yo;
	private Entity Father;
	private String WN;
	private whatsthenew WNCopy;
	private static Catalog catalog;

	public NewTyperAdmin(Entity father, whatsthenew WNin) {
		super(false);
		setGlassEnabled(true);
		WNCopy = WNin;
		if (WNin == whatsthenew.TypeCategory)
			WN = "Type Category";
		else
			WN = "Type";
		Father = father;
		String Titulo = "Write the new " + WN;
		Yo = this;
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");

		Label lblNewLabel = new Label(Titulo);
		lblNewLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(lblNewLabel);

		Grid grid = new Grid(1, 2);
		grid.setCellSpacing(2);
		grid.setCellPadding(2);
		verticalPanel.add(grid);
		grid.setSize("159px", "56px");

		Label lblType = new Label(WN);
		grid.setWidget(0, 0, lblType);

		final TextBox textBox = new TextBox();
		textBox.setVisibleLength(30);
		textBox.setMaxLength(100);
		grid.setWidget(0, 1, textBox);

		Grid grid_1 = new Grid(1, 2);
		verticalPanel.add(grid_1);

		Button btnNewButton = new Button("Save");
		btnNewButton.setSize("100%", "100%");
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});
		btnNewButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		btnNewButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");
			}
		});
		btnNewButton.setStyleName("gwt-ButtonCenter");
		grid_1.setWidget(0, 0, btnNewButton);
		btnNewButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (!textBox.getText().isEmpty()) {
					// Window.alert("Generar Tag de Tipo Revisando si existe, si ya existe se recupera");
					AsyncCallback<Long> callback = new AsyncCallback<Long>() {

						public void onFailure(Throwable caught) {
							if (caught instanceof FileException) {
								Window.alert(((FileException) caught)   // Se ataja cuando se guarda un file con un nombre que ya existe.
										.getMessage());
							} else if (caught instanceof FolderException) {
								Window.alert(((FolderException) caught)   // Se ataja cuando se guarda un folder con un nombre que ya existe en su mismo nivel.
										.getMessage());
							} else {
								Window.alert("The file could not be saved");
							}

							LoadingPanel.getInstance().hide();
							Yo.hide();

						}

						public void onSuccess(Long result) {
							LoadingPanel.getInstance().hide();
							EditorTagsAndTypes.LoadBasicTypes();
							Yo.hide();
						}
					};
					LoadingPanel.getInstance().setLabelTexto("Saving...");
					LoadingPanel.getInstance().center();
					if (WNCopy == whatsthenew.Type) {
						File F = new File(textBox.getText(), null, catalog
								.getId());
						/*
						 * if (Father == null) {
						 * bookReaderServiceHolder.saveFile(F,
						 * Constants.CATALOGID, callback); } else {
						 */
						bookReaderServiceHolder.saveFile(F, Father.getID(),
								callback);
						// }

					} else {
						Folder F = new Folder(textBox.getText(), null, catalog
								.getId());
						/*
						 * if (Father == null)
						 * bookReaderServiceHolder.saveFolder(F,
						 * Constants.CATALOGID, callback); else
						 */
						bookReaderServiceHolder.saveFolder(F, Father.getID(),
								callback);

					}
					Yo.hide();
				}
				// Generado = new TypeAnotation(textBox.getText()); // o el
				// antiguo
				// Window.alert("La Anotacion sera de Tipo :" +
				// Generado.getName());
				//
				// Salvar_Anotacio();
				// Yo.hide();

				else {
					Window.alert("The new type is empty");
					NewTyperAdmin NT = new NewTyperAdmin(Father, WNCopy);
					NT.isModal();
					NT.center();
					Yo.hide();
				}
			}

		});
		verticalPanel.setCellHorizontalAlignment(btnNewButton,
				HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellVerticalAlignment(btnNewButton,
				HasVerticalAlignment.ALIGN_MIDDLE);

		Button CancelButton = new Button("Cancel");
		CancelButton.setSize("100%", "100%");
		CancelButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});
		CancelButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		CancelButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");
			}
		});
		CancelButton.setStyleName("gwt-ButtonCenter");
		grid_1.setWidget(0, 1, CancelButton);
		CancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Yo.hide();
				// Window.alert("Esconder");
			}
		});
	}

	public static void setCatalog(Catalog catalog) {
		NewTyperAdmin.catalog = catalog;
	}

	public static Catalog getCatalog() {
		return catalog;
	}

}
