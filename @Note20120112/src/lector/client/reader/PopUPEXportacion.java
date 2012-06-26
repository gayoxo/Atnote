package lector.client.reader;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.book.reader.ImageService;
import lector.client.book.reader.ImageServiceAsync;
import lector.client.controler.Controlador;
import lector.client.login.ActualUser;
import lector.client.reader.export.EnvioExportacion;
import lector.client.reader.export.ExportResult;
import lector.client.reader.export.PanelSeleccionExportacion;
import lector.client.reader.export.arbitroLlamadas;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ScrollPanel;

public class PopUPEXportacion extends PopupPanel {

	private VerticalPanel verticalPanel;
	private static final int Longitud = 473;
	static ImageServiceAsync imageServiceHolder = GWT
			.create(ImageService.class);

	public PopUPEXportacion() {
		super(false);

		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		setWidget(dockLayoutPanel);
		dockLayoutPanel.setSize(Longitud + "px", Window.getClientHeight() - 48
				+ "px");
		// dockLayoutPanel.setSize( Longitud +"px",
		// Window.getClientHeight()-48+"px");
		MenuBar menuBar = new MenuBar(false);
		dockLayoutPanel.addNorth(menuBar, 1.9);

		MenuItem Close = new MenuItem(ActualUser.getLanguage().getClose(),
				false, new Command() {
					public void execute() {
						hide();
					}
				});
		Close.setHTML("Close");
		menuBar.addItem(Close);

		MenuItem mntmNewItem_1 = new MenuItem("Export", false, new Command() {
			public void execute() {
				if (verticalPanel.getWidgetCount() > 0) {
					ArrayList<ExportObject> list = new ArrayList<ExportObject>();
					// Controlador.change2ExportResult();
					for (Widget widget : verticalPanel) {
						ElementoExportacion EE = (ElementoExportacion) widget;
						list.add(new ExportObject(EE.getAnnotation(), EE
								.getImagen().getUrl(),EE.getImagen().getWidth(),EE.getImagen().getHeight(),EE.getAnnotation().getUserName(),EE.getAnnotation().getCreatedDate().toGMTString()));
						// EnvioExportacion EnEx=new
						// EnvioExportacion(EE.getAnnotation(), EE.getImagen());
						// ExportResult.addResult(EnEx);
					}
					PanelSeleccionExportacion PSE=new PanelSeleccionExportacion(list);
					PSE.center();
					
										
					/*imageServiceHolder.loadHTMLStringForExport(list,
							new AsyncCallback<String>() {

								public void onSuccess(String result) {
									FormPanel formPanel = new FormPanel();
									formPanel
											.setEncoding(FormPanel.ENCODING_URLENCODED);
									formPanel.setMethod(FormPanel.METHOD_POST);
									TextArea textArea = new TextArea();
									textArea.setText(result);
									textArea.setName("html");
									formPanel.add(textArea);
									formPanel
											.setAction("../rs/AtNote/html/produce");
									formPanel.submit();

								}

								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}
							});
*/
				}
			}
		});
		// TODO Anadir a Lenguaje
		menuBar.addItem(mntmNewItem_1);

		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize(Longitud + "px", Window.getClientHeight() - 48
				+ "px");
		dockLayoutPanel.add(scrollPanel);

		verticalPanel = new VerticalPanel();
		scrollPanel.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");

	}

	public static int getLongitud() {
		return Longitud;
	}

	public void addAlement(ElementoExportacion elementoExportacion) {
		verticalPanel.add(elementoExportacion);
		elementoExportacion.addCliker(verticalPanel);
	}
}
