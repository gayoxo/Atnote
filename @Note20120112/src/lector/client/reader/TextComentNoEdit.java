package lector.client.reader;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.File;
import lector.client.controler.Constants;
import lector.client.login.ActualUser;
import lector.client.reader.PanelTextComent.CatalogTipo;
import lector.share.model.Annotation;
import lector.share.model.FileDB;
import lector.share.model.Language;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class TextComentNoEdit extends DialogBox {

	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private MenuItem mntmCancelar;
	private Annotation annotation;
	private MenuItemSeparator separator;
	private Language ActualLang;
	private ArrayList<Long> Antiguos;
	private ArrayList<SelectorPanel> SE;
	//private TextArea AnotArea;
	private HorizontalPanel horizontalPanel;
	private HorizontalPanel horizontalPanel_1;
	private HTMLPanel AnotAreaHTML;
	private ScrollPanel scrollPanel;

	public TextComentNoEdit(Annotation E, ArrayList<SelectorPanel> sE) {
		
		super(false);
		setAnimationEnabled(true);
		annotation = E;
		SE=sE;
		setHTML(annotation.getUserName()+ "  -  " + annotation.getCreatedDate().toGMTString());
		CommentPanel.setEstado(true);
		ActualLang = ActualUser.getLanguage();
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("883px", "337px");

		MenuBar menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);

		mntmCancelar = new MenuItem(ActualLang.getClose(), false,
				new Command() {

					public void execute() {
						CommentPanel.setEstado(false);
						hide();
						for (SelectorPanel SP : SE) {
							SP.hide();
						}
					}
				});

		menuBar.addItem(mntmCancelar);

		separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		scrollPanel = new ScrollPanel();
		verticalPanel.add(scrollPanel);
		scrollPanel.setSize("99%", "263px");
		
//		AnotArea = new TextArea();
//		AnotArea.setReadOnly(true);
//		verticalPanel.add(AnotArea);
//		AnotArea.setSize("99%", "263px");
//		AnotArea.setText(annotation.getComment().toString());
		
		AnotAreaHTML = new HTMLPanel(annotation.getComment().toString());
		scrollPanel.setWidget(AnotAreaHTML);
		AnotAreaHTML.setSize("100%", "100%");

		
		horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setSpacing(5);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSize("", "40px");
		
		horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_1.setSpacing(3);
		horizontalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setSize("", "100%");
		

		bookReaderServiceHolder.getFilesByIds(annotation.getFileIds(),
				new AsyncCallback<ArrayList<FileDB>>() {

					public void onFailure(Throwable caught) {

					}

					public void onSuccess(ArrayList<FileDB> result) {
						Antiguos=new ArrayList<Long>();
						for (int i = 0; i < result.size(); i++) {
						FileDB resulttmp=result.get(i);
						Antiguos.add(resulttmp.getId());
						File F=new File(resulttmp.getName(), resulttmp.getId(), resulttmp.getCatalogId());
						F.setFathers(null);
						if (F.getCatalogId().equals(ActualUser.getReadingactivity().getCatalogId()))
						{
							ButtonTipo B=new ButtonTipo(F,CatalogTipo.Catalog1.getTexto(),horizontalPanel_1);
							B.addClickHandler(new ClickHandler() {
								
								public void onClick(ClickEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
									
								}
							});
						
				        	B.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
								}
							});
							

				        	B.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
							}
						});
							

				        	B.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									
									((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
								
							}
						});

				        	B.setStyleName("gwt-ButtonCenter");
							
							horizontalPanel_1.add(B);
						}
							else{
								ButtonTipo B=new ButtonTipo(F,CatalogTipo.Catalog2.getTexto(),horizontalPanel_1);
								
								B.addClickHandler(new ClickHandler() {
									
									public void onClick(ClickEvent event) {
										((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
										
									}
								});
							
					        	B.addMouseDownHandler(new MouseDownHandler() {
									public void onMouseDown(MouseDownEvent event) {
										((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
									}
								});
								

					        	B.addMouseOutHandler(new MouseOutHandler() {
									public void onMouseOut(MouseOutEvent event) {
										((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
								}
							});
								

					        	B.addMouseOverHandler(new MouseOverHandler() {
									public void onMouseOver(MouseOverEvent event) {
										
										((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
									
								}
							});

					        	B.setStyleName("gwt-ButtonCenter");
								
								horizontalPanel_1.add(B);
							}
						}
					}
				});




	

//		if (!annotation.isEditable()) {
//			mntmGuardar.setVisible(false);
//			mntmDeleteAnnootation.setVisible(false);
//			PanelTexto.getRichTextArea().setEnabled(false);
//			PanelTexto.getComboBox().setVisible(false);
//			PanelTexto.getToolbar().setVisible(false);
//			PanelTexto.getChckbxNewCheckBox().setVisible(false);
//			PanelTexto.getBotonSelectType().setVisible(false);
//			PanelTexto.getLabelPrivPub().setVisible(false);
//			PanelTexto.getBotonSelectTypePublic().setVisible(false);
//			PanelTexto.getRichTextArea().setEnabled(false);
//			mntmClear.setVisible(false);
//
//		}
		

	}
	
	

}
