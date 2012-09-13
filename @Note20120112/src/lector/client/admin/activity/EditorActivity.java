package lector.client.admin.activity;

import java.util.ArrayList;

import lector.client.admin.activity.buttons.BotonTemplates;
import lector.client.admin.activity.buttons.Botonbooks;
import lector.client.admin.activity.buttons.Botoncatalogo;
import lector.client.admin.activity.buttons.Botongroups;
import lector.client.admin.activity.buttons.Botonlanguage;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.Catalog;
import lector.client.controler.Constants;
import lector.client.controler.ErrorConstants;
import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;
import lector.share.model.Catalogo;
import lector.share.model.GroupApp;
import lector.share.model.Language;
import lector.share.model.ReadingActivity;
import lector.share.model.Template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;

public class EditorActivity extends PopupPanel {

	private ReadingActivity ActualActivity;
	private Catalog SelectedCatalog = null;
	private Catalog SelectedCatalogOld = null;
	private Catalog SelectedCatalogPublic = null;
	private Catalog SelectedCatalogOldPublic = null;
	private String SelectedBookOld = null;
	private Template Template =null;
	private Language SelectedLanguage = null;
	private GroupApp SelectedGroup = null;
	private String SelectedBook = null;
	private EditorActivity Yo;
	private Label LanguageLabel;
	private Label CatalogLabel;
	private Label OpenCatalogLabel;
	private Label BooksLabel;
	private Label GroupsLabel;
	private VerticalPanel CatalogPanel;
	private VerticalPanel LanguagePanel;
	private VerticalPanel BooksPanel;
	private VerticalPanel GroupsPanel;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	private CheckBox CheckBoxFree;
	private Label BlankTemplateBox;
	private Label TemplateLabel;
	private ListBox comboBox;
	private VerticalPanel TemplatePanel;
	private Label VisualizacionLabel;
	private Image image;

	public EditorActivity(ReadingActivity RA) {
		
		super(false);
		SelectedCatalog = null;
		SelectedCatalogOld = null;
		 SelectedLanguage = null;
		 SelectedGroup = null;
		 SelectedBook = null;
		 SelectedCatalogPublic = null;
		SelectedCatalogOldPublic = null;
		SelectedBookOld = null;
		setGlassEnabled(true);
		ActualActivity = RA;
		Yo = this;
		FlowPanel flowPanel = new FlowPanel();
		setWidget(flowPanel);
		flowPanel.setSize("794px", "536px");

		MenuBar menuBar = new MenuBar(false);
		flowPanel.add(menuBar);

		MenuItem mntmNewItem = new MenuItem("Activity Editor :", false,
				(Command) null);
		mntmNewItem.setEnabled(false);
		mntmNewItem.setHTML("Activity Editor : " + ActualActivity.getName());
		menuBar.addItem(mntmNewItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		MenuItem mntmNewItem_1 = new MenuItem("New item", false, new Command() {
			public void execute() {
				
				if (SelectedBook == null || SelectedCatalog == null
						|| SelectedGroup == null || SelectedLanguage == null 
						|| SelectedCatalogPublic == null )
					if (Window
							.confirm("Some attributes are in blank. The activity will be unavailable until you fill all the atributes, do you want to continue?"))
						saveActivity();
					else {
					}
				else
					saveActivity();

			}

			private void saveActivity() {
				if (checkcatalog()){
						if (Window
							.confirm("Are you sure you want to swap the catalogue in the activity?. The annotation associated to the activity will be deleted")) {
						LoadingPanel.getInstance().center();
						LoadingPanel.getInstance().setLabelTexto("Deleting...");
						bookReaderServiceHolder
								.removeReadingActivityFromAnnotations(
										ActualActivity.getId(),
										new AsyncCallback<Integer>() {

											public void onFailure(
													Throwable caught) {
												Window.alert("The annotations could not be deleted");
												LoadingPanel.getInstance()
														.hide();

											}

											public void onSuccess(Integer result) {
												SaveacActivitytoServer();
												LoadingPanel.getInstance()
														.hide();

											}
										});

					}
					}
				else if (checkbook()){
						if (Window
							.confirm("Are you sure you want to swap the book in the activity?. The annotation associated to the activity will be deleted")) {
						LoadingPanel.getInstance().center();
						LoadingPanel.getInstance().setLabelTexto("Deleting...");
						bookReaderServiceHolder
								.removeReadingActivityFromAnnotations(
										ActualActivity.getId(),
										new AsyncCallback<Integer>() {

											public void onFailure(
													Throwable caught) {
												Window.alert("The annotations could not be deleted");
												LoadingPanel.getInstance()
														.hide();

											}

											public void onSuccess(Integer result) {
												SaveacActivitytoServer();
												LoadingPanel.getInstance()
														.hide();

											}
										});

					}
					}
				else		
				{
//					if (SelectedCatalog.getId()==SelectedCatalogPublic.getId()) Window.alert("The open catalog and the teachers catalog can't be the same");
//					else 
						SaveacActivitytoServer();
				}
					

			}

			private boolean checkbook() {
		//		return false;
		 return ((SelectedBook != null) && (SelectedBookOld != null) && !(SelectedBook.equals(SelectedBookOld)));
			}

			private boolean checkcatalog() {
				return (((SelectedCatalogOld != null) && (SelectedCatalog != null) && !(SelectedCatalog.getId().equals(SelectedCatalogOld.getId())))
						|| ((SelectedCatalogOldPublic != null) && (SelectedCatalogPublic != null) && !(SelectedCatalogPublic.getId().equals(SelectedCatalogOldPublic.getId()))));
				
			}

			private void SaveacActivitytoServer() {

				ActualActivity.setBookId(SelectedBook);
				if (SelectedCatalog != null)
					ActualActivity.setCatalogId(SelectedCatalog.getId());
				if (SelectedGroup != null)
					ActualActivity.setGroupId(SelectedGroup.getId());
				if (SelectedLanguage != null)
					ActualActivity.setLanguageName(SelectedLanguage.getName());
				if (SelectedCatalogPublic != null)
					ActualActivity.setOpenCatalogId(SelectedCatalogPublic.getId());
				if (Template != null)
					ActualActivity.setTemplateId(Template.getId());
				ActualActivity.setTemplateLibre(CheckBoxFree.getValue());
				ActualActivity.setVisualizacion(comboBox.getItemText(comboBox.getSelectedIndex()));
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto("Saving...");
				bookReaderServiceHolder.saveReadingActivity(ActualActivity,
						new AsyncCallback<Void>() {

							public void onFailure(Throwable caught) {
								Window.alert("The Activity could not be saved");
								LoadingPanel.getInstance().hide();

							}

							public void onSuccess(Void result) {
								LoadingPanel.getInstance().hide();
								Yo.hide();

							}
						});

			}
		});
		mntmNewItem_1.setHTML("Save");
		menuBar.addItem(mntmNewItem_1);

		MenuItem mntmNewItem_2 = new MenuItem("New item", false, new Command() {
			public void execute() {
				Yo.hide();
			}
		});
		mntmNewItem_2.setHTML("Cancel");
		menuBar.addItem(mntmNewItem_2);

		VerticalSplitPanel verticalSplitPanel = new VerticalSplitPanel();
		flowPanel.add(verticalSplitPanel);
		verticalSplitPanel.setSize("100%", "100%");

		TabPanel tabPanel = new TabPanel();
		verticalSplitPanel.setTopWidget(tabPanel);
		tabPanel.setSize("100%", "100%");

		ScrollPanel LanguageSP = new ScrollPanel();
		tabPanel.add(LanguageSP, "Language", false);
		LanguageSP.setSize("100%", "226px");

		LanguagePanel = new VerticalPanel();
		LanguageSP.setWidget(LanguagePanel);
		LanguagePanel.setSize("100%", "100%");

		ScrollPanel CatalogSP = new ScrollPanel();
		tabPanel.add(CatalogSP, "Catalog", false);
		CatalogSP.setSize("100%", "226px");

		CatalogPanel = new VerticalPanel();
		CatalogSP.setWidget(CatalogPanel);
		CatalogPanel.setSize("100%", "100%");

		ScrollPanel BooksSP = new ScrollPanel();
		tabPanel.add(BooksSP, "Books", false);
		BooksSP.setSize("100%", "226px");

		BooksPanel = new VerticalPanel();
		BooksSP.setWidget(BooksPanel);
		BooksPanel.setSize("100%", "100%");

		ScrollPanel GroupsSP = new ScrollPanel();
		tabPanel.add(GroupsSP, "Groups", false);
		GroupsSP.setSize("100%", "226px");

		GroupsPanel = new VerticalPanel();
		GroupsSP.setWidget(GroupsPanel);
		GroupsPanel.setSize("100%", "100%");
		
		SimplePanel TemplatesSP = new SimplePanel();
		tabPanel.add(TemplatesSP, "Templates", false);
		TemplatesSP.setSize("100%", "226px");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		TemplatesSP.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		ScrollPanel scrollPanel = new ScrollPanel();
		verticalPanel.add(scrollPanel);
		scrollPanel.setSize("100%", "169px");
		
		TemplatePanel = new VerticalPanel();
		scrollPanel.setWidget(TemplatePanel);
		TemplatePanel.setSize("100%", "100%");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSize("100%", "45px");
		
		CheckBoxFree = new CheckBox("Allow blank template");
		CheckBoxFree.setChecked(true);
		CheckBoxFree.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (CheckBoxFree.getValue())
				BlankTemplateBox.setVisible(true);
					else 
					if (Template!=null)
						BlankTemplateBox.setVisible(false);
					else {
						Window.alert(ErrorConstants.TEMPLATES_CAT_BE_EMPTY);
						CheckBoxFree.setValue(true, false);
					}
			}
		});
		horizontalPanel.add(CheckBoxFree);
		
		ScrollPanel VisualizacionSP = new ScrollPanel();
		tabPanel.add(VisualizacionSP, "Visualization", false);
		VisualizacionSP.setSize("100%", "226px");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		VisualizacionSP.setWidget(horizontalPanel_1);
		horizontalPanel_1.setSize("100%", "224px");
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_1.add(verticalPanel_1);
		verticalPanel_1.setSize("225px", "100%");
		
		comboBox = new ListBox();
		comboBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (comboBox.getItemText(comboBox.getSelectedIndex()).equals(Constants.VISUAL_ARBOL))
					{
					VisualizacionLabel.setText("Visualizacion:" + Constants.VISUAL_ARBOL);
					image.setUrl("EditImages/Arbol.jpg");
					}
				else 
				{
					VisualizacionLabel.setText("Visualizacion:" + Constants.VISUAL_KEY);
					image.setUrl("EditImages/Key.jpg");
				}
			}
		});
		comboBox.addItem(Constants.VISUAL_ARBOL);
		comboBox.addItem(Constants.VISUAL_KEY);
		comboBox.setSelectedIndex(0);
		verticalPanel_1.add(comboBox);
		comboBox.setWidth("199px");
		
		VerticalPanel verticalPanel_3 = new VerticalPanel();
		verticalPanel_3.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_1.add(verticalPanel_3);
		verticalPanel_3.setSize("545px", "100%");
		
		//Image image = new Image("EditImages/Key.jpg");
		image = new Image("EditImages/Arbol.jpg");
		verticalPanel_3.add(image);
		image.setSize("503px", "151px");

		VerticalPanel verticalPanel_2 = new VerticalPanel();
		verticalPanel_2.setSpacing(6);
		verticalPanel_2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalSplitPanel.setBottomWidget(verticalPanel_2);
		verticalPanel_2.setSize("100%", "236px");

		LanguageLabel = new Label("Language : ");
		verticalPanel_2.add(LanguageLabel);

		CatalogLabel = new Label("Teacher Catalog :");
		verticalPanel_2.add(CatalogLabel);

		OpenCatalogLabel =new Label("Open Catalog :");
		verticalPanel_2.add(OpenCatalogLabel);
		
		BooksLabel = new Label("Book : ");
		verticalPanel_2.add(BooksLabel);

		GroupsLabel = new Label("Groups : ");
		verticalPanel_2.add(GroupsLabel);
		
		VerticalPanel verticalPanel_4 = new VerticalPanel();
		verticalPanel_2.add(verticalPanel_4);
		
		TemplateLabel = new Label("Template:");
		verticalPanel_4.add(TemplateLabel);
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		verticalPanel_4.add(horizontalPanel_2);
		horizontalPanel_2.setSpacing(3);
		
		SimplePanel simplePanel = new SimplePanel();
		horizontalPanel_2.add(simplePanel);
		simplePanel.setSize("35px", "18px");
		
		BlankTemplateBox = new Label("Blank template Allowed ");
		horizontalPanel_2.add(BlankTemplateBox);
		BlankTemplateBox.setStyleName("gwt-LabelRedactivity");
		
		VisualizacionLabel = new Label("Visualizacion:" + Constants.VISUAL_ARBOL);
		verticalPanel_2.add(VisualizacionLabel);

		generateOldCampsAndPanels();

		tabPanel.selectTab(0);

	}

	private void generateOldCampsAndPanels() {
		if (ActualActivity.getCatalogId() != null) {
			bookReaderServiceHolder.loadCatalogById(
					ActualActivity.getCatalogId(),
					new AsyncCallback<Catalogo>() {

						public void onFailure(Throwable caught) {
							Window.alert("The catalog could not be loaded");
							generalangaugeOld();

						}

						public void onSuccess(Catalogo result) {
							Catalog catalog = new Catalog();
							catalog.setId(result.getId());
							catalog.setPrivate(result.isIsPrivate());
							catalog.setProfessorId(result.getProfessorId());
							catalog.setCatalogName(result.getCatalogName());
							SelectedCatalog = catalog;
							SelectedCatalogOld = catalog;
							CatalogLabel.setText("Teacher Catalog :"
									+ catalog.getCatalogName());
							generalangaugeOld();
						}
					});

		} else
			generalangaugeOld();

	}

	private void generalangaugeOld() {
		if (ActualActivity.getLanguageName() != null) {
			bookReaderServiceHolder.loadLanguageByName(
					ActualActivity.getLanguageName(),
					new AsyncCallback<Language>() {

						public void onFailure(Throwable caught) {
							Window.alert("The Language could not be loaded");
							generabookOld();

						}

						public void onSuccess(Language result) {
							SelectedLanguage = result;
							LanguageLabel.setText("Language : "
									+ result.getName());
							generabookOld();
						}
					});

		} else
			generabookOld();

	}

	private void generabookOld() {
		if (ActualActivity.getBookId() != null) {
			SelectedBook = ActualActivity.getBookId();
			SelectedBookOld=SelectedBook;
			BooksLabel.setText("Book : " + SelectedBook);
			generagroupOld();
		} else
			generagroupOld();

	}

	private void generagroupOld() {
		if (ActualActivity.getGroupId() != null) {
			bookReaderServiceHolder.loadGroupById(ActualActivity.getGroupId(),
					new AsyncCallback<GroupApp>() {

						public void onSuccess(GroupApp result) {
							GroupsLabel.setText("Groups : " + result.getName());
							SelectedGroup = result;
							generatecatalogPublicOld();

						}

						public void onFailure(Throwable caught) {
							Window.alert("I could not refresh the old group in the Activity");
							generatecatalogPublicOld();

						}
					});

		} else
			generatecatalogPublicOld();
		;

	}
	
	private void generatecatalogPublicOld()
	{
		if (ActualActivity.getOpenCatalogId() != null) {
			bookReaderServiceHolder.loadCatalogById(
					ActualActivity.getOpenCatalogId(),
					new AsyncCallback<Catalogo>() {

						public void onFailure(Throwable caught) {
							Window.alert("The catalog could not be loaded");
							Generatepanels();

						}

						public void onSuccess(Catalogo result) {
							Catalog catalog = new Catalog();
							catalog.setId(result.getId());
							catalog.setPrivate(result.isIsPrivate());
							catalog.setProfessorId(result.getProfessorId());
							catalog.setCatalogName(result.getCatalogName());
							SelectedCatalogPublic = catalog;
							SelectedCatalogOldPublic = catalog;
							OpenCatalogLabel.setText("Open Catalog :"
									+ catalog.getCatalogName());
							generateTemplateOld();
						}
					});

		} else
			generateTemplateOld();
	}

	private void generateTemplateOld()
	{
		if (ActualActivity.getTemplateId() != null) {
			
			exportServiceHolder.loadTemplateById(ActualActivity.getTemplateId(), new AsyncCallback<Template>() {
				
				public void onSuccess(Template result) {
					Template=result;
					if (result!=null)
						TemplateLabel.setText("Template: "
							+ result.getName());
					generateBlancTemplateOld();
					
				}
				
				public void onFailure(Throwable caught) {
					Window.alert(ErrorConstants.ERROR_LOADING_TEMPLATE);
					generateBlancTemplateOld();
					
				}
			});

		} else
			generateBlancTemplateOld();
		
	}
	
	private void generateBlancTemplateOld()
	{
		if (ActualActivity.getTemplateLibre() != null) {
			
			if (ActualActivity.getTemplateLibre()){
				BlankTemplateBox.setVisible(true);
				CheckBoxFree.setValue(true, false);
			}
			else{
				CheckBoxFree.setValue(false, false);
				BlankTemplateBox.setVisible(false);
			}
			
			generateFinderOld();


		} else{
			generateFinderOld();
		}
			
		
	}
	private void generateFinderOld()
	{
		if (ActualActivity.getVisualizacion() != null) {
			if (ActualActivity.getVisualizacion().equals(Constants.VISUAL_ARBOL))
					{
					comboBox.setSelectedIndex(0);
					VisualizacionLabel.setText("Visualizacion:" + Constants.VISUAL_ARBOL);
					image.setUrl("EditImages/Arbol.jpg");
				
					
					}
			else 
			{
				comboBox.setSelectedIndex(1);	
				VisualizacionLabel.setText("Visualizacion:" + Constants.VISUAL_KEY);
				image.setUrl("EditImages/Key.jpg");
			}
//			bookReaderServiceHolder.loadCatalogById(
//					ActualActivity.getOpenCatalogId(),
//					new AsyncCallback<Catalogo>() {
//
//						public void onFailure(Throwable caught) {
//							Window.alert("The catalog could not be loaded");
//							Generatepanels();
//
//						}
//
//						public void onSuccess(Catalogo result) {
//							Catalog catalog = new Catalog();
//							catalog.setId(result.getId());
//							catalog.setPrivate(result.isIsPrivate());
//							catalog.setProfessorId(result.getProfessorId());
//							catalog.setCatalogName(result.getCatalogName());
//							SelectedCatalogPublic = catalog;
//							SelectedCatalogOldPublic = catalog;
//							OpenCatalogLabel.setText("Open Catalog :"
//									+ catalog.getCatalogName());
				Generatepanels();
//						}
//					});

		} else
			Generatepanels();
	}
	
	private void Generatepanels() {
		bookReaderServiceHolder.getVisbibleCatalogsByProfessorId(ActualUser
				.getUser().getId(), new AsyncCallback<ArrayList<Catalogo>>() {

			public void onSuccess(ArrayList<Catalogo> result) {
				for (int i = 0; i < result.size()-1; i++) {

					Catalog catalog = Catalog.cloneCatalogo(result.get(i));
					Botoncatalogo BC = new Botoncatalogo(catalog);
					BC.setSize("100%", "100%");
					CatalogPanel.add(BC);
					BC.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							Botoncatalogo BCE = (Botoncatalogo) event
									.getSource();
							PanelSeleccionCatalogo PSC= new PanelSeleccionCatalogo(BCE.getCatalogo(),CatalogLabel,OpenCatalogLabel,Yo);
							PSC.showRelativeTo(BCE);

						}
					});
					BC.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonPush");
						}
					});
					BC.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
						}
					});
					BC.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
						}
					});
					BC.setStyleName("gwt-ButtonTOP");
				}
				if (!result.isEmpty()) {
					Catalog catalog = Catalog.cloneCatalogo(result.get(result.size()-1));
					Botoncatalogo BC = new Botoncatalogo(catalog);
					BC.setSize("100%", "100%");
					CatalogPanel.add(BC);
					BC.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							Botoncatalogo BCE = (Botoncatalogo) event
									.getSource();
							PanelSeleccionCatalogo PSC= new PanelSeleccionCatalogo(BCE.getCatalogo(),CatalogLabel,OpenCatalogLabel,Yo);
							PSC.showRelativeTo(BCE);

						}
					});
					BC.setStyleName("gwt-ButtonBotton");
					BC.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
						}
					});
					BC.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
						}
					});
					BC.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
						}
					});
				}
				GeneratepanelsLang();

			}

			public void onFailure(Throwable caught) {
				Window.alert("I could refresh the Catalogs");
				GeneratepanelsLang();

			}
		});

	}

	private void GeneratepanelsLang() {
		bookReaderServiceHolder
				.getLanguages(new AsyncCallback<ArrayList<Language>>() {

					public void onSuccess(ArrayList<Language> result) {
						for (int i = 0; i < result.size()-1; i++) {
							Botonlanguage BC = new Botonlanguage(result.get(i));
							BC.setSize("100%", "100%");
							LanguagePanel.add(BC);
							BC.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									Botonlanguage BCE = (Botonlanguage) event
											.getSource();
									LanguageLabel.setText("Language : "
											+ BCE.getLanguage().getName());
									SelectedLanguage = BCE.getLanguage();

								}
							});
							BC.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							BC.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							BC.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							BC.setStyleName("gwt-ButtonTOP");

						}
						if(!result.isEmpty()) {
							Botonlanguage BC = new Botonlanguage(result.get(result.size()-1));
							BC.setSize("100%", "100%");
							LanguagePanel.add(BC);
							BC.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									Botonlanguage BCE = (Botonlanguage) event
											.getSource();
									LanguageLabel.setText("Language : "
											+ BCE.getLanguage().getName());
									SelectedLanguage = BCE.getLanguage();

								}
							});
							BC.setStyleName("gwt-ButtonBotton");
							BC.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
								}
							});
							BC.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
								}
							});
							BC.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
								}
							});

						}
						Generatepanelsbooks();

					}

					public void onFailure(Throwable caught) {
						Window.alert("I could refresh the languages");
						Generatepanelsbooks();

					}
				});

	}

	private void Generatepanelsbooks() {

		ArrayList<String> result = ActualUser.getUser().getBookIds();
		for (int i = 0; i < result.size()-1; i++) {
			Botonbooks BC = new Botonbooks(result.get(i));
			BC.setSize("100%", "100%");
			BooksPanel.add(BC);
			BC.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					Botonbooks BCE = (Botonbooks) event.getSource();
					BooksLabel.setText("Book : " + BCE.getBook());
					SelectedBook = BCE.getBook();

				}
			});
			BC.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonPush");
				}
			});
			BC.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
				}
			});
			BC.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
				}
			});
			BC.setStyleName("gwt-ButtonTOP");
		}
		if(!result.isEmpty()) {
			Botonbooks BC = new Botonbooks(result.get(result.size()-1));
			BC.setSize("100%", "100%");
			BooksPanel.add(BC);
			BC.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					Botonbooks BCE = (Botonbooks) event.getSource();
					BooksLabel.setText("Book : " + BCE.getBook());
					SelectedBook = BCE.getBook();

				}
			});
			BC.setStyleName("gwt-ButtonBotton");
			BC.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
				}
			});
			BC.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
				}
			});
			BC.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
				}
			});
		}
		Generatepanelsgroup();

	}

	private void Generatepanelsgroup() {
		bookReaderServiceHolder
				.getGroups(new AsyncCallback<ArrayList<GroupApp>>() {

					public void onSuccess(ArrayList<GroupApp> result) {
						for (int i = 0; i < result.size()-1; i++) {
							Botongroups BC = new Botongroups(result.get(i));
							BC.setSize("100%", "100%");
							GroupsPanel.add(BC);
							BC.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									Botongroups BCE = (Botongroups) event
											.getSource();
									GroupsLabel.setText("Groups : "
											+ BCE.getGrupo().getName());
									SelectedGroup = BCE.getGrupo();
								}
							});
							BC.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							BC.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							BC.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							BC.setStyleName("gwt-ButtonTOP");
						}
						if(!result.isEmpty()) {
							Botongroups BC = new Botongroups(result.get(result.size()-1));
							BC.setSize("100%", "100%");
							GroupsPanel.add(BC);
							BC.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									Botongroups BCE = (Botongroups) event
											.getSource();
									GroupsLabel.setText("Groups : "
											+ BCE.getGrupo().getName());
									SelectedGroup = BCE.getGrupo();
								}
							});
							BC.setStyleName("gwt-ButtonBotton");
							BC.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
								}
							});
							BC.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
								}
							});
							BC.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
								}
							});
						}
						GenerateTemplates();

					}

					public void onFailure(Throwable caught) {
						Window.alert("I could refresh the Groups");
						GenerateTemplates();

					}
				});

	}
	
	private void GenerateTemplates() {

		exportServiceHolder.getTemplates(new AsyncCallback<ArrayList<Template>>() {
			
			public void onSuccess(ArrayList<Template> result) {
				for (int i = 0; i < result.size()-1; i++) {
					BotonTemplates BC = new BotonTemplates(result.get(i));
					BC.setSize("100%", "100%");
					TemplatePanel.add(BC);
					BC.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							BotonTemplates BCE = (BotonTemplates) event
									.getSource();
							Template=BCE.getTemplate();
							TemplateLabel.setText("Template: "
									+ Template.getName());
						}
					});
					BC.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonPush");
						}
					});
					BC.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
						}
					});
					BC.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
						}
					});
					BC.setStyleName("gwt-ButtonTOP");
				}
				if(!result.isEmpty()) {
					BotonTemplates BC = new BotonTemplates(result.get(result.size()-1));
					BC.setSize("100%", "100%");
					TemplatePanel.add(BC);
					BC.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							BotonTemplates BCE = (BotonTemplates) event
									.getSource();
							Template=BCE.getTemplate();
							TemplateLabel.setText("Template: "
									+ Template.getName());
						}
					});
					BC.setStyleName("gwt-ButtonBotton");
					BC.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
						}
					});
					BC.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
						}
					});
					BC.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
						}
					});
				}
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert(ErrorConstants.COULD_NOT_REFRESH_TEMPLATES);
				
			}
		});
	


	}
	
	public Catalog getSelectedCatalog() {
		return SelectedCatalog;
	}
	
	public void setSelectedCatalog(Catalog selectedCatalog) {
		SelectedCatalog = selectedCatalog;
	}
	
	public Catalog getSelectedCatalogPublic() {
		return SelectedCatalogPublic;
	}
	
	public void setSelectedCatalogPublic(Catalog selectedCatalogPublic) {
		SelectedCatalogPublic = selectedCatalogPublic;
	}
	
}
