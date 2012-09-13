package lector.client.reader;

import java.util.ArrayList;

import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.book.reader.ImageService;
import lector.client.book.reader.ImageServiceAsync;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.login.ActualUser;
import lector.client.reader.export.EnvioExportacion;
import lector.client.reader.export.ExportResult;
import lector.client.reader.export.PanelSeleccionExportacion;
import lector.client.reader.export.arbitroLlamadas;
import lector.share.model.Template;
import lector.share.model.TemplateCategory;

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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.sun.java.swing.plaf.windows.resources.windows;

public class PopUPEXportacion extends PopupPanel {

	private VerticalPanel verticalPanel;
	private ListBox comboBox;
	private static final int Longitud = 473;
	static ImageServiceAsync imageServiceHolder = GWT
			.create(ImageService.class);
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	static ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	private Template Asociado;
	private static VerticalPanel Actual;
	private static ElementoExportacionTemplate EET;


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
					for (Widget W : verticalPanel) {
						
						if (W instanceof ElementoExportacionTemplate)
							{
							ElementoExportacionTemplate EET=(ElementoExportacionTemplate)W;
							list.add(new ExportObjectTemplate(EET.getProfundidad(), EET.getTExt()));
							EET.Export(list);
							}
						else if (W instanceof ElementoExportacion)
							{
							ElementoExportacion EE=(ElementoExportacion)W;
							list.add(new ExportObject(EE.getAnnotation(), 
														 EE.getImagen().getUrl(),
														 EE.getImagen().getWidth(),
														 EE.getImagen().getHeight(),
														 EE.getAnnotation().getUserName(),
														 EE.getAnnotation().getCreatedDate().toGMTString()));
							}
						
//						ElementoExportacion EE = (ElementoExportacion) widget;
//						//TODO separar en funcion de la clase
//						list.add(new ExportObject(EE.getAnnotation(), EE
//								.getImagen().getUrl(),EE.getImagen().getWidth(),EE.getImagen().getHeight(),EE.getAnnotation().getUserName(),EE.getAnnotation().getCreatedDate().toGMTString()));
//						// EnvioExportacion EnEx=new
//						// EnvioExportacion(EE.getAnnotation(), EE.getImagen());
//						// ExportResult.addResult(EnEx);
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
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		scrollPanel.setWidget(verticalPanel_1);
		verticalPanel_1.setSize("100%", "100%");
				
				HorizontalPanel horizontalPanel = new HorizontalPanel();
				verticalPanel_1.add(horizontalPanel);
				
				if (ActualUser.getReadingactivity().getTemplateLibre()&&ActualUser.getReadingactivity().getTemplateId()!=null)
				{
					comboBox = new ListBox();
				Long IDTemplate=ActualUser.getReadingactivity().getTemplateId();
				if (ActualUser.getReadingactivity().getTemplateLibre())
					comboBox.addItem("Blank Template");
				
				if (IDTemplate!=null)
					exportServiceHolder.loadTemplateById(IDTemplate, new AsyncCallback<Template>() {
						
						public void onSuccess(Template result) {
							comboBox.addItem(result.getName());
							Asociado=result;
						}
						
						public void onFailure(Throwable caught) {
							
							Window.alert(ErrorConstants.ERROR_RETRIVING_TEMPLATE_MASTER_EXPORT_PANEL);
						}
				});
								comboBox.setSelectedIndex(0);
				comboBox.addChangeHandler(new ChangeHandler() {
					public void onChange(ChangeEvent event) {
						verticalPanel.clear();
						ListBox CB=(ListBox) event.getSource();
						if (CB.getSelectedIndex()!=0)
							LoadTemplate();
						else {
							Actual=verticalPanel;
							if (EET!=null) 
								EET.ResetButton();
						}
					}

				
				});
				horizontalPanel.add(comboBox);
				comboBox.setWidth("245px");
				}
				else if (ActualUser.getReadingactivity().getTemplateId()!=null)
				{
					exportServiceHolder.loadTemplateById(ActualUser.getReadingactivity().getTemplateId(), new AsyncCallback<Template>() {
						
						public void onSuccess(Template result) {
//							Window.alert("Load: " + result.getName());
							Asociado=result;
							verticalPanel.clear();	
							if (EET!=null) 
								EET.ResetButton();
							Actual=verticalPanel;
							LoadTemplate();
						}
						
						public void onFailure(Throwable caught) {
							
							Window.alert(ErrorConstants.ERROR_RETRIVING_TEMPLATE_MASTER_EXPORT_PANEL);
						}
					});
				}
				
				
				verticalPanel = new VerticalPanel();
				verticalPanel_1.add(verticalPanel);
				Actual=verticalPanel;
				if (EET!=null) 
					EET.ResetButton();

	}

	protected void LoadTemplate() {
		if (!Asociado.getCategories().isEmpty())
		exportServiceHolder.getTemplateCategoriesByIds(Asociado.getCategories(), new AsyncCallback<ArrayList<TemplateCategory>>() {
			
			public void onSuccess(ArrayList<TemplateCategory> result) {
				ArrayList<ElementoExportacionTemplate> AEx=new ArrayList<ElementoExportacionTemplate>();
				for (TemplateCategory templateCategory : result) {
					ElementoExportacionTemplate E=new ElementoExportacionTemplate(templateCategory, 1, Asociado.isModificable());
					AEx.add(E);
					Actual.add(E);
					E.addCliker(Actual);
				}
				
				for (ElementoExportacionTemplate elementoExportacionTemplate : AEx) {
					ArbitroLlamadasTemplatesExport.getInstance().addElement(elementoExportacionTemplate);
				}
				
				if (!AEx.isEmpty())
					{
					setActual(AEx.get(0));
					}
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert(ErrorConstants.ERROR_RETRIVING_TEMPLATE_THREAD_REFRESH);
				
			}
		});
		
	}

	public static int getLongitud() {
		return Longitud;
	}

	public void addAlement(ElementoExportacion elementoExportacion) {
		Actual.add(elementoExportacion);
		elementoExportacion.addCliker(Actual);
	}
	
	public static void setActual(ElementoExportacionTemplate actual) {
		if (EET!=null)
			EET.ResetButton();
		EET=actual;
		EET.selectedButton();
		Actual = actual.getFondo();
	}
	

	public void Refresh() {
		verticalPanel.clear();
		if (Asociado!=null&&(comboBox==null||comboBox.getSelectedIndex()!=0))
			LoadTemplate();
		
	}
	

	
}
