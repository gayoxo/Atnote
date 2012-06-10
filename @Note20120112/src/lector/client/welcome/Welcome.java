package lector.client.welcome;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.book.reader.ImageService;
import lector.client.book.reader.ImageServiceAsync;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.LogMessageConstants;
import lector.client.logger.Logger;
import lector.client.login.ActualUser;
import lector.client.login.UserApp;
import lector.client.reader.About;
import lector.client.reader.BookBlob;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class Welcome implements EntryPoint {
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	static ImageServiceAsync imageServiceHolder = GWT
			.create(ImageService.class);
	private Button btnNewButton;
	private HorizontalPanel horizontalPanel;
	private RootPanel Footer;

	// DESCOMENTAR EN DESARROLLO, CREA UN USUARIO ROOT.
	// private void callUserRoot() {
	// UserApp adminUser2 = new UserApp();
	// adminUser2.setEmail("root");
	// adminUser2.setProfile(Constants.PROFESSOR);
	// bookReaderServiceHolder.saveUser(adminUser2,
	// new AsyncCallback<Boolean>() {
	// public void onSuccess(Boolean result) {
	// }
	//
	// public void onFailure(Throwable caught) {
	// Window.alert("Ha fallado Cesar");
	// }
	// });
	// }

	public void onModuleLoad() {

//		imageServiceHolder.loadBookBlobById(194002L,
//				new AsyncCallback<BookBlob>() {
//
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//
//					}
//
//					public void onSuccess(BookBlob result) {
//						String pag2 = "/serve?blob-key=AMIfv954_m62pmYcOvOstnF-YQvsBvmFb5v_Ajzf1dAXTM6DmqKy4dEPhw6r0tDlLW2BeYiauz_tGvOEsPulVIyEsnSoetF9Oz0QpsheKtVh9UZnOq1DKewPoNkyQDIGVjpnfqR99bXjhgJdLpBDymXzSAdoOo1uyQ";
//						String pag3 = "/serve?blob-key=AMIfv97axsXwF4AhrzG3lkpXLYWvfcnRFP_3mxiArkwflkDEOcxyOBfgLW16amvEm2-NLYvk6jeGz9OfLt7w5191atbN01aEs7ePXPaK6GyhCzgJmHIUMPPUWcdWkn5zShYPZYpBNeR1bod8iu3jmrubEp6GQ_UZ9Q";
//						String pag4 = "/serve?blob-key=AMIfv97WycrtOFO9f-iBGGmTnNRRGnksddKphV5u_31XfvYant5pUo7-TzquU21-WcE69--tJwEKhIveWvEpockoATzTDmNKSCGqk8UxQhBf25Sx0qiTjPSBAraqKdwv5-JRLZK3YT67-2-40EHLONKwwjNpiouUQQ";
//						String pag7 = "/serve?blob-key=AMIfv96qrdq-4_TMVyHQ5R3ywDUR4ATgOiyJAjP6AsI2EMSCWZM5aVat0q_1sUndL14cKW6uvLB5oDzTlWBNdX9wV3_yL4bdtsBSQ-4ciBS9DtVNmHcOyQfpL42K5C3hBYlQgIo4LCUSWXgJh8OPrpcv9ESSxRO7gw";
//
//						result.getWebLinks().set(1, pag2);
//						result.getWebLinks().set(2, pag3);
//						result.getWebLinks().set(3, pag4);
//						result.getWebLinks().set(6, pag7);
//
//						imageServiceHolder.saveBookBlob(result,
//								new AsyncCallback<Void>() {
//
//									public void onFailure(Throwable caught) {
//										Window.alert("Todo Mal");
//
//									}
//
//									public void onSuccess(Void result) {
//										Window.alert("Todo Bien");
//
//									}
//								});
//					}
//				});

		// callUserRoot();
		RootPanel rootPanel = RootPanel.get();
		Footer = RootPanel.get("footer");
		rootPanel.setSize("100%", "100%");
		rootPanel.setStyleName("Root");

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setStyleName("Root");
		rootPanel.add(verticalPanel, 0, 0);
		// rootPanel.add(verticalPanel);
		verticalPanel.setSize("100%", "100%");

		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(verticalPanel_1);

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel_1);
		horizontalPanel_1
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		Image image_1 = new Image("Logo.jpg");
		image_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PopupPanel About = new About();
				About.center();
			}
		});
		horizontalPanel_1.add(image_1);

		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_2.setSpacing(10);
		verticalPanel_1.add(horizontalPanel_2);

		Image image = new Image("logo_ucm.jpg");
		image.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.open("http://www.ucm.es", "_blank", null);

			}
		});
		horizontalPanel_2.add(image);
		image.setSize("75px", "78px");

		Image image_2 = new Image("logo-leethi_fa.gif");
		image_2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.open("http://www.ucm.es/info/leethi/", "_blank", null);

			}
		});

		HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
		horizontalPanel_2.add(horizontalPanel_4);
		horizontalPanel_2.add(image_2);
		image_2.setSize("164px", "72px");

		HorizontalPanel horizontalPanel_5 = new HorizontalPanel();
		horizontalPanel_2.add(horizontalPanel_5);

		Image image_3 = new Image("ISLA.jpg");
		horizontalPanel_2.add(image_3);
		image_3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.open("http://ilsa.fdi.ucm.es/", "_blank", null);

			}
		});
		image_3.setSize("201px", "69px");

		Label lblNewLabel_1 = new Label(
				"Developers: Cesar Ruiz, Joaquin Gayoso");
		// verticalPanel_1.add(lblNewLabel_1);
		SimplePanel S = new SimplePanel();
		Footer.add(S);
		S.setWidget(lblNewLabel_1);
		lblNewLabel_1.setStyleName("Desarrolladores");

		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel_3);
		horizontalPanel_3.setHeight("70px");

		horizontalPanel = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel);

		Label lblCollaborativeAnnotationOf = new Label(
				"Collaborative Annotation of Digitalized Literary Texts");
		lblCollaborativeAnnotationOf.setStyleName("TituloWelcome");
		// verticalPanel.add(lblCollaborativeAnnotationOf);
		lblCollaborativeAnnotationOf.setWidth("1112px");

		// LoginOL
		// btnNewButton = new Button("Log In");
		// horizontalPanel.add(btnNewButton);
		// btnNewButton.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		// Controlador.change2Login();
		// }
		// });
		// btnNewButton.setText("Log In");
		// btnNewButton.setSize("164px", "50px");

		// LoginNew
		bookReaderServiceHolder.login(Window.Location.getHref(),
				new AsyncCallback<UserApp>() {

					public void onFailure(Throwable error) {
						Window.alert("Warning: Error in Database");
					}

					public void onSuccess(UserApp result) {

						ActualUser.setUser(result);
						btnNewButton = new Button("Log In");
						horizontalPanel.add(btnNewButton);
						btnNewButton.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								btnNewButton.setEnabled(false);
								Window.open(ActualUser.getUser().getLoginUrl(),
										"_self", "");
							}
						});
						btnNewButton.setText("Log In");
						btnNewButton.setSize("164px", "50px");
						btnNewButton.addClickHandler(new ClickHandler() {

							public void onClick(ClickEvent event) {
								((Button) event.getSource())
										.setStyleName("gwt-ButtonCenter");

							}
						});

						btnNewButton
								.addMouseDownHandler(new MouseDownHandler() {
									public void onMouseDown(MouseDownEvent event) {
										((Button) event.getSource())
												.setStyleName("gwt-ButtonCenterPush");
									}
								});

						btnNewButton.addMouseOutHandler(new MouseOutHandler() {
							public void onMouseOut(MouseOutEvent event) {
								((Button) event.getSource())
										.setStyleName("gwt-ButtonCenter");
							}
						});

						btnNewButton
								.addMouseOverHandler(new MouseOverHandler() {
									public void onMouseOver(MouseOverEvent event) {

										((Button) event.getSource())
												.setStyleName("gwt-ButtonCenterOver");

									}
								});

						btnNewButton.setStyleName("gwt-ButtonCenter");

						/*
						 * Anchor A = new Anchor("Sign In");
						 * A.setHref(ActualUser.getUser().getLoginUrl()); Anchor
						 * signOutLink = new Anchor("Sign Out");
						 * signOutLink.setHref
						 * (ActualUser.getUser().getLogoutUrl());
						 * horizontalPanel.add(signOutLink);
						 */
						if (result.isLoggedIn()) {
							Logger L = Logger.GetLogger();

							if (result.getProfile().equals(Constants.STUDENT)) {
								Controlador.change2MyActivities();
								Footer.clear();
							} else if (result.getProfile().equals(
									Constants.PROFESSOR)) {
								Controlador.change2Administrator();
								Footer.clear();
							}
							L.info(Welcome.class.getName(),
									LogMessageConstants.USER_SUCCESS_LOGGED_IN
											+ result.getEmail());

						} else {
							if (!result.isIsAuthenticated()) {
								Logger L = Logger.GetLogger();

								Window.alert("You are not authorized to view this application");
								horizontalPanel.remove(btnNewButton);
								btnNewButton = new Button("Log Out");
								btnNewButton.setText("Log Out");

								horizontalPanel.add(btnNewButton);

								btnNewButton.setSize("164px", "50px");
								btnNewButton.setEnabled(true);
								L.warning(
										Welcome.class.getName(),
										LogMessageConstants.USER_FAILURE_LOGGED_IN
												+ result.getEmail());
								btnNewButton
										.addClickHandler(new ClickHandler() {
											public void onClick(ClickEvent event) {

												// btnNewButton.setEnabled(false);
												Window.open(ActualUser
														.getUser()
														.getLogoutUrl(),
														"_self", "");
												// Window.open(ActualUser.getUser().getLogoutUrl(),
												// "_self", "");
												// ActualUser.getUser().setLoggedIn(true);
												// ActualUser.getUser().setIsAuthenticated(true);
											}
										});
								btnNewButton
										.addClickHandler(new ClickHandler() {

											public void onClick(ClickEvent event) {
												((Button) event.getSource())
														.setStyleName("gwt-ButtonCenter");

											}
										});

								btnNewButton
										.addMouseDownHandler(new MouseDownHandler() {
											public void onMouseDown(
													MouseDownEvent event) {
												((Button) event.getSource())
														.setStyleName("gwt-ButtonCenterPush");
											}
										});

								btnNewButton
										.addMouseOutHandler(new MouseOutHandler() {
											public void onMouseOut(
													MouseOutEvent event) {
												((Button) event.getSource())
														.setStyleName("gwt-ButtonCenter");
											}
										});

								btnNewButton
										.addMouseOverHandler(new MouseOverHandler() {
											public void onMouseOver(
													MouseOverEvent event) {

												((Button) event.getSource())
														.setStyleName("gwt-ButtonCenterOver");

											}
										});

								btnNewButton.setStyleName("gwt-ButtonCenter");

							}

						}
					}
				});

	}
}
