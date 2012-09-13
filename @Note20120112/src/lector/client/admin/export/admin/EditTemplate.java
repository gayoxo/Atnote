package lector.client.admin.export.admin;

import java.util.Stack;

import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.reader.LoadingPanel;
import lector.share.model.Template;
import lector.share.model.TemplateCategory;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class EditTemplate implements EntryPoint {

	
	
	
	private static ScrollPanel scrollPanel;
	private static Template T;
	private static PanelGestionTemplate PGT;
	private EditTemplate YO;
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);

	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		
		YO=this;
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		rootPanel.add(dockLayoutPanel, 0, 0);
		dockLayoutPanel.setSize("100%", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		dockLayoutPanel.addNorth(menuBar, 2);
		
		MenuItem mntmNewItem = new MenuItem("New item", false, (Command) null);
		mntmNewItem.setHTML("Template Administration");
		mntmNewItem.setEnabled(false);
		menuBar.addItem(mntmNewItem);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuItem mntmBack = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2AdminTemplate();
			}
		});
		menuBar.addItem(mntmBack);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		dockLayoutPanel.add(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel();
		horizontalPanel.add(splitLayoutPanel);
		splitLayoutPanel.setSize("100%", "100%");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		splitLayoutPanel.addWest(verticalPanel, 300.0);
		verticalPanel.setWidth("100%");
		
		Button CreateNew = new Button("Create");
		verticalPanel.add(CreateNew);
		CreateNew.setWidth("100%");
		
		CreateNew.setStyleName("gwt-ButtonCenter");
		CreateNew.setSize("100%", "100%");
		CreateNew.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				TemplateCategory TC=PGT.getActualNode();
				PanelNewTemplateCategory PNTC=new PanelNewTemplateCategory(TC,YO);
				PNTC.center();
				
			}
		});

		CreateNew.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		
		CreateNew.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
		});
		
		CreateNew.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});
		
		Button Delete = new Button("Delete");
		verticalPanel.add(Delete);
		Delete.setWidth("100%");
		
		Delete.setStyleName("gwt-ButtonCenter");
		Delete.setSize("100%", "100%");
		Delete.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				
			}
		});

		Delete.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		
		Delete.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
		});
		
		Delete.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});
		
		Delete.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				RepresentacionTemplateCategory TC=PanelGestionTemplate.getActual();
				RepresentacionTemplateCategory TCP=TC.getFather();
				if (TCP!=null)
				{
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto(InformationConstants.SAVING);
					exportServiceHolder.deleteTemplateCategory(TC.getT().getId(), new AsyncCallback<Void>() {

						public void onFailure(Throwable caught) {
							LoadingPanel.getInstance().hide();
							Window.alert(ErrorConstants.ERROR_DELETING_TEMPLATE_CATEGORY);
							
						}

						public void onSuccess(Void result) {
							
							LoadingPanel.getInstance().hide();
							PGT.refresh();
						}
					});
						
					
					
				}else
				{
					Window.alert(ErrorConstants.ERROR_THIS_IS_A_TEMPLATE_DELETE);	
				}
				
			}
		});
		
		Button Rename = new Button("Rename");
		verticalPanel.add(Rename);
		Rename.setWidth("100%");
		
		Rename.setStyleName("gwt-ButtonCenter");
		Rename.setSize("100%", "100%");
		Rename.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				
			}
		});

		Rename.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		
		Rename.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
		});
		
		Rename.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
			
	});
		Rename.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				RepresentacionTemplateCategory TC=PanelGestionTemplate.getActual();
				NewPanelRename NPR=new NewPanelRename(TC);
				NPR.center();
				
				
			}
		});
		
		scrollPanel = new ScrollPanel();
		splitLayoutPanel.add(scrollPanel);
		scrollPanel.setSize("100%", "100%");
		
	}
	
	
	public static void setTemplate(Template t) {
		T = t;
		PGT=new PanelGestionTemplate(T);
		scrollPanel.add(PGT);
	}


	public void refresh() {
		PGT.refresh();
		
	}
	
	public static PanelGestionTemplate getPGT() {
		return PGT;
	}
}
