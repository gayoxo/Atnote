package lector.client.reader;

import lector.client.controler.Controlador;
import lector.client.login.ActualUser;
import lector.client.reader.export.EnvioExportacion;
import lector.client.reader.export.ExportResult;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ScrollPanel;

public class PopUPEXportacion extends PopupPanel {

	private VerticalPanel verticalPanel;
	private static final int Longitud=473;

	public PopUPEXportacion() {
		super(false);
		
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		setWidget(dockLayoutPanel);
		dockLayoutPanel.setSize( Longitud +"px", Window.getClientHeight()-48+"px");
		//dockLayoutPanel.setSize( Longitud +"px", Window.getClientHeight()-48+"px");
		MenuBar menuBar = new MenuBar(false);
		dockLayoutPanel.addNorth(menuBar, 1.9);
		
		MenuItem Close = new MenuItem(ActualUser.getLanguage().getClose(), false, new Command() {
			public void execute() {
				hide();
			}
		});
		Close.setHTML("Close");
		menuBar.addItem(Close);
		
		MenuItem mntmNewItem_1 = new MenuItem("Export", false, new Command() {
			public void execute() {
				if (verticalPanel.getWidgetCount()>0)
				{
				Controlador.change2ExportResult();
				for (Widget widget : verticalPanel) {
					ElementoExportacion EE=(ElementoExportacion)widget;
					EnvioExportacion EnEx=new EnvioExportacion(EE.getAnnotation(), EE.getImagen());
					ExportResult.addResult(EnEx);
				}
					
				}
			}
		});
		//TODO Anadir a Lenguaje
		menuBar.addItem(mntmNewItem_1);
		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize( Longitud +"px", Window.getClientHeight()-48+"px");
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
