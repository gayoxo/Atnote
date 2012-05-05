package lector.client.reader.filter.advance;

import java.util.ArrayList;

import lector.client.controler.Controlador;
import lector.client.login.ActualUser;
import lector.client.reader.Annotation;
import lector.client.reader.MainEntryPoint;
import lector.client.reader.SelectorPanel;
import lector.client.reader.TextSelector;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

public class VisualBookPanelFilterAdvance extends DialogBox {

	private Image image;
	 private Annotation annotation;
	private ArrayList<SelectorPanel> SE;
	private VisualBookPanelFilterAdvance Yo;


	/**
	 * @wbp.parser.constructor
	 */
	public VisualBookPanelFilterAdvance(Annotation annotationin,Image imagein) {
		super(false);
		
		Yo=this;
		
		
		annotation=annotationin;
		image = imagein;
		DockPanel SP=new DockPanel();

		setHTML(ActualUser.getBook().getTitle() + "    -     "+ActualUser.getLanguage().getPage() +": " + annotation.getPageNumber());
		
		
		setWidget(SP);
		
setWidget(SP);
		
		MenuBar menuBar = new MenuBar(false);
		SP.add(menuBar, DockPanel.NORTH);
		
		MenuItem mntmNewItem = new MenuItem("New item", false, new Command() {
			public void execute() {
				Yo.hide();
			}
		});
		mntmNewItem.setHTML(ActualUser.getLanguage().getCloseFA());
		menuBar.addItem(mntmNewItem);
		
		MenuItem mntmNewItem_1 = new MenuItem("New item", false, new Command() {
			public void execute() {
				Yo.hide();
				MainEntryPoint.setCurrentPageNumber(annotation.getPageNumber());
				MainEntryPoint.setFiltroAnotPar(FilterAsyncSystem.getFiltroResidual());
				Controlador.change2Reader();
			}
		});
		mntmNewItem_1.setHTML(ActualUser.getLanguage().getGO_To_PageFA());
		menuBar.addItem(mntmNewItem_1);
		

		MenuItem mntmShowSelection = new MenuItem(ActualUser.getLanguage().getComment_AreaFA(), false, new Command() {
			public void execute() {
				SE=new ArrayList<SelectorPanel>();
				for (TextSelector TS : annotation.getTextSelectors()) {
					
					SelectorPanel SEE = new SelectorPanel(TS.getX().intValue(),
							TS.getY().intValue(),
			                image.getAbsoluteLeft(), image.getAbsoluteTop(),
			                TS.getWidth().intValue(),
			                TS.getHeight().intValue());
			        SEE.show();
			        SE.add(SEE);
				}
				
			}
		});
		//menuBar.addItem(mntmShowSelection);
		
		SP.add(image, DockPanel.SOUTH);
		
		image.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				Image I = (Image) event.getSource();
				float He = I.getHeight();
				float Wi = I.getWidth();
				float prop = He / 830;
				float Winew = (Wi / prop);
				image.setSize(Winew + "px", "830px");
				// Window.alert("Altura: " + He + "Ancho: " + Wi );
			}
		});
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	
	@Override
	public void center() {
		super.center();
		SE=new ArrayList<SelectorPanel>();
		for (TextSelector TS : annotation.getTextSelectors()) {
			SelectorPanel SEE = new SelectorPanel(TS.getX().intValue(),
					TS.getY().intValue(),
	                image.getAbsoluteLeft(), image.getAbsoluteTop(),
	                TS.getWidth().intValue(),
	                TS.getHeight().intValue());
	        SEE.show();
	        SE.add(SEE);
		}
	}
	
	@Override
	public void hide() {
		for (SelectorPanel SP : SE) {
			SP.hide();

		}
		super.hide();

	}
	
	@Override
	public void show() {
		super.show();
		SE=new ArrayList<SelectorPanel>();
		for (TextSelector TS : annotation.getTextSelectors()) {
			SelectorPanel SEE = new SelectorPanel(TS.getX().intValue(),
					TS.getY().intValue(),
	                image.getAbsoluteLeft(), image.getAbsoluteTop(),
	                TS.getWidth().intValue(),
	                TS.getHeight().intValue());
	        SEE.show();
	        SE.add(SEE);
		}
	}
	
	
	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}

}
