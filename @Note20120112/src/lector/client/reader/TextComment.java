package lector.client.reader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.server.FileDB;
import lector.client.controler.Constants;
import lector.client.language.Language;
import lector.client.login.ActualUser;
import com.google.appengine.api.datastore.Text;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TextComment extends DialogBox {

	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private ArrayList<TextSelector> textSelector;
	private MenuItem mntmGuardar;
	private MenuItem mntmClear;
	private MenuItem mntmCancelar;
//	private RichTextArea richTextArea;
//	private HorizontalPanel horizontalPanel;
//	private HorizontalPanel horizontalPanel_2;
//	private Button btnNewButton_1;
//	private Label lblNewLabel;
//	private Label TypoLabel;
//	private SelectorTypePopUpAnnotacion finder;
//	private HorizontalPanel horizontalPanel_3;
//	private Label label;
//	private ListBox listBox;
//	private CheckBox chckbxNewCheckBox;
//	private HorizontalPanel horizontalPanel_4;
//	private VerticalPanel verticalPanel_3;
	private Language ActualLang;
	private Annotation annotation;
	private PanelTextComent PanelTexto;
	
	
	public TextComment(ArrayList<TextSelector> textSelectorin, Book book) {

		super(false);
		setAnimationEnabled(true);
		CommentPanel.setEstado(true);
		Date now = new Date();
		if ((ActualUser.getUser().getName()!=null)&&(!ActualUser.getUser().getName().isEmpty()))
			 setHTML(ActualUser.getUser().getName() + "  -  " + now.toGMTString());
			else  setHTML(ActualUser.getUser().getEmail() + "  -  " + now.toGMTString());
		setSize("100%", "100%");
		final Book bookRef = book;
		this.textSelector = textSelectorin;
		ActualLang=ActualUser.getLanguage();
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("883px", "329px");
		MenuBar menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);

		mntmGuardar = new MenuItem(ActualLang.getSave(), false, new Command() {

			public void execute() {
				
				if (moreThanone()) {
					//
					ArrayList<Long> ListaASalvar=new ArrayList<Long>();
					for (int i = 0; i < PanelTexto.getPenelBotonesTipo().getWidgetCount(); i++) {
						ListaASalvar.add(((ButtonTipo)PanelTexto.getPenelBotonesTipo().getWidget(i)).getEntidad().getID());
					}
					Text comment = new Text(PanelTexto.getRichTextArea().getHTML());
					LoadingPanel.getInstance().setLabelTexto(ActualLang.getSaving());
					LoadingPanel.getInstance().center();

					annotation = new Annotation(bookRef.getId(),
							MainEntryPoint.getCurrentPageNumber(),
							textSelector, comment);
					annotation.setUserId(ActualUser.getUser().getId());
					
					if ((ActualUser.getUser().getName()!=null)&&(!ActualUser.getUser().getName().isEmpty()))
						annotation.setUserName(ActualUser.getUser().getName());
						else annotation.setUserName(ActualUser.getUser().getEmail());
					annotation.setReadingActivity(ActualUser.getReadingactivity().getId());
					
					
					
					bookReaderServiceHolder.getFilesByIds(ListaASalvar,new AsyncCallback<ArrayList<FileDB>>()
							{

								public void onFailure(Throwable caught) {
									Window.alert(ActualLang.getE_loading()+" File");
								}

								public void onSuccess(ArrayList<FileDB> result) {
									LoadingPanel.getInstance().hide();
									ArrayList<Long> L= new ArrayList<Long>();
									boolean IsInCatalog = false;
									Long CataPrincipal=ActualUser.getCatalogo().getId();
									for (int i = 0; i < result.size(); i++) {
										if (CataPrincipal.equals(result.get(i).getCatalogId())) IsInCatalog=true;
										L.add(result.get(i).getId());
									}
									annotation.setFileIds(L);
									annotation.setVisibility(false);
									if (PanelTexto.getComboBox().getItemText(
											PanelTexto.getComboBox().getSelectedIndex()).equals(
											Constants.ANNOTATION_PUBLIC)) {
										annotation.setVisibility(true);
										annotation
												.setUpdatability(PanelTexto.getChckbxNewCheckBox()
														.getValue());
									}
										if (IsInCatalog){
											saveAnnotacion(annotation);
											hide();
										}
										else{
											//Window.alert("Error aqui");
											Window.alert(ActualLang.getE_Need_to_select_a_type()+ActualUser.getCatalogo().getCatalogName()+" : " + ActualLang.getSetTypes());
										}
										
									
								}
									
								
							});
					
							} else {
								Window.alert(ActualLang.getE_Need_to_select_a_type()+ActualUser.getCatalogo().getCatalogName()+" : " + ActualLang.getSetTypes()+"("+(PanelTexto.getPenelBotonesTipo().getWidgetCount())+")");
								LoadingPanel.getInstance().hide();
							}

					

			}

			private boolean moreThanone() {
				return ((PanelTexto.getPenelBotonesTipo().getWidgetCount())>0);
			}

			private void saveAnnotacion(Annotation annotation) {
				AsyncCallback<Long> callback = new AsyncCallback<Long>() {

					public void onFailure(Throwable caught) {
						Window.alert(ActualLang.getE_saving()+ " Annotation");
						MainEntryPoint.hidePopUpSelector();
						LoadingPanel.getInstance().hide();
					}

					public void onSuccess(Long result) {
						LoadingPanel.getInstance().hide();
						MainEntryPoint.hidePopUpSelector();
						MainEntryPoint.refreshP();
					}
				};
				
				bookReaderServiceHolder
						.saveAnnotation(annotation, callback);
				CommentPanel.setEstado(false);
				
			}

		});

		menuBar.addItem(mntmGuardar);

		mntmClear = new MenuItem(ActualLang.getClearAnot(), false, new Command() {

			public void execute() {
				PanelTexto.getRichTextArea().setText("");
			}
		});

		menuBar.addItem(mntmClear);

		mntmCancelar = new MenuItem(ActualLang.getCancel(), false, new Command() {

			public void execute() {
				CommentPanel.setEstado(false);
				hide();
				MainEntryPoint.hidePopUpSelector();
			}
		});

		menuBar.addItem(mntmCancelar);

		PanelTexto=new PanelTextComent(ActualLang);
		verticalPanel.add(PanelTexto);
		PanelTexto.setSize("100%", "100%");

	}

	

	
}
