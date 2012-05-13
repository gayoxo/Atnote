package lector.client.reader;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.File;
import lector.client.catalogo.server.FileDB;
import lector.client.controler.Constants;
import lector.client.language.Language;
import lector.client.login.ActualUser;
import lector.client.reader.PanelTextComent.CatalogTipo;

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

public class TextComentEdit extends DialogBox {

	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private MenuItem mntmGuardar;
	private MenuItem mntmClear;
	private MenuItem mntmCancelar;
	private Annotation annotation;
	private MenuItemSeparator separator;
	private MenuItem mntmDeleteAnnootation;
	private Language ActualLang;
	private PanelTextComent PanelTexto;
	private ArrayList<Long> Antiguos;
	private ArrayList<Long> PilaABorrar;
	private ArrayList<Long> ListaASalvar;
	private ArrayList<Long> Resultado;
	private ArrayList<SelectorPanel> SE;

	public TextComentEdit(Annotation E, ArrayList<SelectorPanel> sE) {
		
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

		mntmGuardar = new MenuItem(ActualLang.getSave(), false, new Command() {


			public void execute() {
				annotation.setComment(new Text(PanelTexto.getRichTextArea().getHTML()));
				annotation.setVisibility(false);
				if (PanelTexto.getComboBox().getItemText(
						PanelTexto.getComboBox().getSelectedIndex()).equals(
						Constants.ANNOTATION_PUBLIC)) {
					annotation.setVisibility(true);
					annotation
							.setUpdatability(PanelTexto.getChckbxNewCheckBox()
									.getValue());
				}
				LoadingPanel.getInstance().setLabelTexto(ActualLang.getSaving());
				LoadingPanel.getInstance().center();

				if (!moreThanone()) Window.alert(ActualLang.getE_Need_to_select_a_type()+ActualUser.getCatalogo().getCatalogName()+" : " + ActualLang.getSetTypes()+"("+(PanelTexto.getPenelBotonesTipo().getWidgetCount())+")"); 	
				else {
					ListaASalvar=new ArrayList<Long>();
					for (int i = 0; i < PanelTexto.getPenelBotonesTipo().getWidgetCount(); i++) {
						ListaASalvar.add(((ButtonTipo)PanelTexto.getPenelBotonesTipo().getWidget(i)).getEntidad().getID());
					}
					checkAndSave();
					

					

			}
			}

			private void checkAndSave() {
				calcular_a_borrar(ListaASalvar);
				LoadingPanel.getInstance().setLabelTexto(ActualLang.getLoading());
				LoadingPanel.getInstance().center();
				bookReaderServiceHolder.getFilesByIds(ListaASalvar,
						new AsyncCallback<ArrayList<FileDB>>() {

							public void onFailure(Throwable caught) {
								LoadingPanel.getInstance().hide();
							}

							public void onSuccess(ArrayList<FileDB> result) {
								LoadingPanel.getInstance().hide();
								Resultado=new ArrayList<Long>();
								boolean IsInCatalog = false;
								Long CataPrincipal=ActualUser.getCatalogo().getId();
								for (int i = 0; i < result.size(); i++) {
									if (CataPrincipal.equals(result.get(i).getCatalogId())) IsInCatalog=true;
									Resultado.add(result.get(i).getId());
								}
								if (IsInCatalog){
									
									borrarTipoDeAnnotacion();
							
								}
								else {
									Window.alert(ActualLang.getE_Need_to_select_a_type()+ActualUser.getCatalogo().getCatalogName()+" : " + ActualLang.getSetTypes());
								}
								
								
							}
						});
				
				
			}

			private void borrarTipoDeAnnotacion() {
				if (!PilaABorrar.isEmpty())
					{
					Long L=PilaABorrar.get(0);
					PilaABorrar.remove(0);
					LoadingPanel.getInstance().setLabelTexto(ActualLang.getDeleting());
					LoadingPanel.getInstance().center();
					bookReaderServiceHolder.removeFileFromAnnotation(annotation.getId(), L, new AsyncCallback<Void>() {
						
						public void onSuccess(Void result) {
							borrarTipoDeAnnotacion();
							LoadingPanel.getInstance().hide();
							
						}
						
						public void onFailure(Throwable caught) {
							Window.alert(ActualUser.getLanguage().getE_deleting());
							hide();
							for (SelectorPanel SP : SE) {
								SP.hide();
							}
							CommentPanel.setEstado(false);
							LoadingPanel.getInstance().hide();
						}
					});
					}
				else SalvadoGeneral();
				
			}

			private void SalvadoGeneral() {
				LoadingPanel.getInstance().setLabelTexto(ActualLang.getSaving());
				LoadingPanel.getInstance().center();
				
								annotation.setFileIds(Resultado);
								annotation.setVisibility(false);
								annotation.setReadingActivity(ActualUser.getReadingactivity().getId());
								if (PanelTexto.getComboBox().getItemText(
										PanelTexto.getComboBox().getSelectedIndex()).equals(
										Constants.ANNOTATION_PUBLIC)) {
									annotation.setVisibility(true);
									annotation
											.setUpdatability(PanelTexto.getChckbxNewCheckBox()
													.getValue());
								}

								saveAnnotacion();
								hide();
								for (SelectorPanel SP : SE) {
									SP.hide();
								}
								CommentPanel.setEstado(false);
								LoadingPanel.getInstance().hide();

				
			}

			private void calcular_a_borrar(ArrayList<Long> listaASalvar) {
				PilaABorrar=new ArrayList<Long>();
				for (int i = 0; i < Antiguos.size(); i++) {
					Long L = Antiguos.get(i);
					boolean found=false;
					for (int j = 0; j < listaASalvar.size(); j++) {
						if (L==listaASalvar.get(j)) 
						{
						found=true;
						break;
						}	
					}
					if (!found) PilaABorrar.add(L);
				}
				
			}

			private boolean moreThanone() {
				return ((PanelTexto.getPenelBotonesTipo().getWidgetCount())>0);
			}

			private void saveAnnotacion() {

				AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

					public void onFailure(Throwable caught) {
						Window.alert(ActualLang.getE_loading() + "Annotation Updated");
						LoadingPanel.getInstance().hide();
					}

					public void onSuccess(Boolean result) {

						AsyncCallback<Long> callback2 = new AsyncCallback<Long>() {

							public void onSuccess(Long result) {
								LoadingPanel.getInstance().hide();
								MainEntryPoint.refreshP();

							}

							public void onFailure(Throwable caught) {
								Window.alert(ActualLang.getE_saving() + "Annotation");

							}
						};
						if (!result)
							if (Window
									.confirm(ActualLang.getW_Newer_version_of_anotation()))
								bookReaderServiceHolder.saveAnnotation(
										annotation, callback2);
							else {
								LoadingPanel.getInstance().hide();
								MainEntryPoint.refreshP();
							}
						else {
							LoadingPanel.getInstance().hide();
							MainEntryPoint.refreshP();
						}
					}
				};
				bookReaderServiceHolder.isRecentToSave(annotation, callback);

			}

		});

		menuBar.addItem(mntmGuardar);

		mntmClear = new MenuItem(ActualLang.getClearAnot(), false,
				new Command() {

					public void execute() {
						PanelTexto.getRichTextArea().setText("");
					}
				});

		menuBar.addItem(mntmClear);

		mntmCancelar = new MenuItem(ActualLang.getCancel(), false,
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

		mntmDeleteAnnootation = new MenuItem(ActualLang.getDeleteAnnotation(), false,
				new Command() {

					public void execute() {

						LoadingPanel.getInstance().setLabelTexto(ActualLang.getDeleting());
						LoadingPanel.getInstance().center();
						AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

							public void onFailure(Throwable caught) {
								Window.alert(ActualLang.getE_deleting()+ " Annotation");
								LoadingPanel.getInstance().hide();
							}

							public void onSuccess(Integer result) {

								LoadingPanel.getInstance().hide();
								MainEntryPoint.refreshP();
							}
						};
						bookReaderServiceHolder.deleteAnnotation(annotation,
								callback);
						CommentPanel.setEstado(false);
						hide();
						for (SelectorPanel SP : SE) {
							SP.hide();
						}
					}
				});
		menuBar.addItem(mntmDeleteAnnootation);

		
		PanelTexto= new PanelTextComent(ActualLang);
		verticalPanel.add(PanelTexto);
		PanelTexto.setSize("100%", "100%");
		
		PanelTexto.getRichTextArea().setHTML(annotation.getComment().toString());
		

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
							ButtonTipo B=new ButtonTipo(F,CatalogTipo.Catalog1.getTexto(),PanelTexto.getPenelBotonesTipo());
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
//							if (annotation.isEditable()) {
								B.addClickHandler(new ClickHandler() {
								
								public void onClick(ClickEvent event) {
									ButtonTipo Yo=(ButtonTipo)event.getSource();
									Yo.getPertenezco().remove(Yo);
									
								}
								});
//							}
							PanelTexto.getPenelBotonesTipo().add(B);
						}
							else{
								ButtonTipo B=new ButtonTipo(F,CatalogTipo.Catalog2.getTexto(),PanelTexto.getPenelBotonesTipo());
//								if (annotation.isEditable()) {
								
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
									B.addClickHandler(new ClickHandler() {

										public void onClick(ClickEvent event) {
											ButtonTipo Yo = (ButtonTipo) event
													.getSource();
											Yo.getPertenezco().remove(Yo);

										}
									});
//								}
								PanelTexto.getPenelBotonesTipo().add(B);
							}
						}
					}
				});



		if (annotation.getUpdatability())
			PanelTexto.getChckbxNewCheckBox().setValue(true);
		else
			PanelTexto.getChckbxNewCheckBox().setValue(false);

		if (!annotation.getVisibility()) {
			PanelTexto.getComboBox().setSelectedIndex(1);
			PanelTexto.getChckbxNewCheckBox().setVisible(false);
		} else if (annotation.getVisibility()) {
			PanelTexto.getComboBox().setSelectedIndex(0);
			PanelTexto.getChckbxNewCheckBox().setVisible(true);
		} 

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
