package lector.client.admin.activity;

import java.util.ArrayList;

import lector.client.admin.activity.buttons.Botonbooks;
import lector.client.admin.activity.buttons.Botoncatalogo;
import lector.client.admin.activity.buttons.Botongroups;
import lector.client.admin.activity.buttons.Botonlanguage;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.server.Catalogo;
import lector.client.language.Language;
import lector.client.login.ActualUser;
import lector.client.login.GroupApp;
import lector.client.reader.LoadingPanel;

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

public class EditorActivity extends PopupPanel {

	private ReadingActivity ActualActivity;
	private Catalog SelectedCatalog = null;
	private Catalog SelectedCatalogOld = null;
	private Catalog SelectedCatalogPublic = null;
	private Catalog SelectedCatalogOldPublic = null;
	private String SelectedBookOld = null;
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
							Generatepanels();
						}
					});

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

					}

					public void onFailure(Throwable caught) {
						Window.alert("I could refresh the Groups");

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
