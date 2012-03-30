package lector.client.admin.bookblob;

import java.util.ArrayList;

import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.login.ActualUser;
import lector.client.login.UserApp;
import lector.client.reader.BookBlob;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.book.reader.ImageServiceAsync;
import lector.client.book.reader.ImageService;

public class BookLoader implements EntryPoint {

	static ImageServiceAsync userImageService = GWT.create(ImageService.class);
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	private TextBox author;
	private TextBox publishedYear;
	private TextBox title;
	private TextBox userApp;
	private int actualFiles;
	private VerticalPanel PanelUploaders;
	private FormPanel form;
	private FileUpload FU;
	private Button submitButton;
	private FileUpload FU1;
	private FileUpload FU2;
	private FileUpload FU3;

	public void onModuleLoad() {

		RootPanel RP = RootPanel.get();
		RootPanel RPMenu = RootPanel.get("Menu");
		RP.setSize("100%", "100%");

		MenuBar menuBar = new MenuBar(false);
		RPMenu.add(menuBar);
		menuBar.setWidth("100%");

		MenuItem mntmNewItem = new MenuItem("Close", false, new Command() {
			
			public void execute() {
				Controlador.change2Administrator();
				
			}
		});
		menuBar.addItem(mntmNewItem);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		RP.add(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");

		form = new FormPanel();
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		horizontalPanel.add(form);
		form.setSize("100%", "100%");

		HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
		form.setWidget(horizontalPanel_4);
		horizontalPanel_4.setSize("100%", "100%");

		VerticalPanel verticalPanel = new VerticalPanel();
		horizontalPanel_4.add(verticalPanel);
		verticalPanel.setSpacing(3);
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		VerticalPanel verticalPanel_2 = new VerticalPanel();
		verticalPanel.add(verticalPanel_2);
		verticalPanel_2.setWidth("200px");

		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanel_2);
		horizontalPanel_2.setWidth("100%");

		Label lblAutor = new Label("Autor:");
		lblAutor.setStyleName("gwt-LabelLoad");
		horizontalPanel_2.add(lblAutor);

		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		horizontalPanel_3.setSpacing(1);
		horizontalPanel_3
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		verticalPanel_2.add(horizontalPanel_3);
		horizontalPanel_3.setWidth("100%");

		author = new TextBox();
		horizontalPanel_3.add(author);
		author.setWidth("393px");
		author.setName(Constants.BLOB_AUTHOR);

		HorizontalPanel horizontalPanel_5 = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanel_5);

		Label lblNewLabel = new Label("Published Year:");
		lblNewLabel.setStyleName("gwt-LabelLoad");
		horizontalPanel_5.add(lblNewLabel);

		HorizontalPanel horizontalPanel_6 = new HorizontalPanel();
		horizontalPanel_6
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		verticalPanel_2.add(horizontalPanel_6);
		horizontalPanel_6.setWidth("100%");
		publishedYear = new TextBox();
		horizontalPanel_6.add(publishedYear);
		publishedYear.setWidth("393px");
		publishedYear.setName(Constants.BLOB_PUBLISHED_YEAR);

		HorizontalPanel horizontalPanel_8 = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanel_8);

		Label lblNewLabel_1 = new Label("Title:");
		lblNewLabel_1.setStyleName("gwt-LabelLoad");
		horizontalPanel_8.add(lblNewLabel_1);

		HorizontalPanel horizontalPanel_7 = new HorizontalPanel();
		horizontalPanel_7
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		verticalPanel_2.add(horizontalPanel_7);
		horizontalPanel_7.setWidth("100%");
		title = new TextBox();
		horizontalPanel_7.add(title);
		title.setWidth("393px");
		title.setName(Constants.BLOB_TITLE);
		
				userApp = new TextBox();
				verticalPanel_2.add(userApp);
				userApp.setWidth("393px");
				userApp.setText(ActualUser.getUser().getId().toString());
				userApp.setReadOnly(true);
				userApp.setVisible(false);
				userApp.setName(Constants.BLOB_UPLOADER);

		VerticalPanel verticalPanel_3 = new VerticalPanel();
		verticalPanel_2.add(verticalPanel_3);
		verticalPanel_3.setSize("100%", "100%");

		PanelUploaders = new VerticalPanel();
		verticalPanel_3.add(PanelUploaders);
		PanelUploaders.setWidth("100%");



		FU = new FileUpload();
		FU.setName("1");
		PanelUploaders.add(FU);
		FU.setWidth("100%");

		FU1 = new FileUpload();
		FU1.setName("2");
		PanelUploaders.add(FU1);
		FU1.setWidth("100%");

		FU2 = new FileUpload();
		FU2.setName("3");
		PanelUploaders.add(FU2);
		FU2.setWidth("100%");

		FU3 = new FileUpload();
		FU3.setName("4");
		PanelUploaders.add(FU3);
		FU3.setSize("100%", "100%");

		SimplePanel simplePanel = new SimplePanel();
		verticalPanel_3.add(simplePanel);
		simplePanel.setSize("100%", "100%");

		Button btnNewButton = new Button("+");
		simplePanel.setWidget(btnNewButton);
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FileUpload fileUploadMove = new FileUpload();
				actualFiles++;
				fileUploadMove.setName(Integer.toString(actualFiles));
				PanelUploaders.add(fileUploadMove);
				fileUploadMove.setSize("100%", "100%");

			}
		});
		btnNewButton.setSize("100%", "100%");
		btnNewButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				
			}
		});
	
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		

		btnNewButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
	});
		

		btnNewButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});

		btnNewButton.setStyleName("gwt-ButtonCenter");
		
		submitButton = new Button("loading...");
		submitButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ArrayList<Widget> Listanueva=new ArrayList<Widget>();
				ArrayList<Widget> ListaIncompatibles=new ArrayList<Widget>();
				for (Widget widget : PanelUploaders) {
					FileUpload T=(FileUpload)widget;
					if (!T.getFilename().isEmpty())
						{
						//Compativilidad
						Listanueva.add(T);
						}
				}
				PanelUploaders.clear();
				int i=0;
				for (Widget widget : Listanueva) {
					FileUpload T=(FileUpload)widget;
					T.setName(Integer.toString(i));	
					PanelUploaders.add(widget);
				//	Window.alert(T.getFilename() + " : " + i);
					i++;
				}
				if ((!Listanueva.isEmpty())&& !title.getText().isEmpty())
					form.submit();
				else Window.alert(ErrorConstants.TEXT_NULL_OR_NO_IMAGEN);
			}
		});
		verticalPanel_2.add(submitButton);
		submitButton.setWidth("100%");
		submitButton.setEnabled(false);
		submitButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				
			}
		});
	
		submitButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		

		submitButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
	});
		

		submitButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});

		submitButton.setStyleName("gwt-ButtonCenter");
		
		startNewBlobstoreSession();
		
		actualFiles = 4;

		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.add(verticalPanel_1);
		verticalPanel_1.setSize("100%", "100%");

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel_1);

		Image image = new Image("Logo.jpg");
		horizontalPanel_1.add(image);

//		form.addSubmitHandler(new FormPanel.SubmitHandler() {
//			public void onSubmit(SubmitEvent event) {
//				if (!"".equalsIgnoreCase(fileUpload.getFilename())) {
//					GWT.log("UPLOADING FILE????" + fileUpload.getFilename(),
//							null);
//					// NOW WHAT????
//				} else {
//					event.cancel(); // cancel the event
//				}
//
//			}
//		});
		
		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

			public void onSubmitComplete(SubmitCompleteEvent event) {
				
				startNewBlobstoreSession();
				
				/*userImageService.getBookBlobsByUserId(ActualUser.getUser().getId(),
						new AsyncCallback<ArrayList<BookBlob>>() {

							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							public void onSuccess(ArrayList<BookBlob> result) {
								Image image = new Image();
								String imgURL = result.get(0).getWebLinks()
										.get(0);
								image.setUrl(imgURL);

								final PopupPanel imagePopup = new PopupPanel(
										true);
								imagePopup.setWidget(image);
								imagePopup.center();
								form.reset();
								

							}
						});*/
				bookReaderServiceHolder.loadUserById(ActualUser.getUser().getId(), new AsyncCallback<UserApp>() {
					
					public void onSuccess(UserApp result) {
						ActualUser.setUser(result);
						Controlador.change2Administrator();
						//form.reset();
						
					}
					
					public void onFailure(Throwable caught) {
						Window.alert(ErrorConstants.ERROR_LOAD_FORCE_LOGIN);
						
					}
				});

			}
		});
		
	}

	private void startNewBlobstoreSession() {
		userImageService.getBlobstoreUploadUrl(new AsyncCallback<String>() {

			public void onFailure(Throwable caught) {

			}

			public void onSuccess(String result) {

				form.setAction(result);
				submitButton.setText("Upload");
				submitButton.setEnabled(true);

			}
		});
	}
}
