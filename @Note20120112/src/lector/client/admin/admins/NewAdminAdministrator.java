package lector.client.admin.admins;

import java.util.ArrayList;
import java.util.Stack;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.StackPanelMio;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;
import lector.share.model.UserApp;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class NewAdminAdministrator implements EntryPoint {

	private TextBox textBox;
	private VerticalPanel verticalPanel_1;
	private StackPanelMio stackPanel_1;
	private Stack<UserApp> Pilallamada;
	private Button SaveAdmins;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);

	public void onModuleLoad() {
		RootPanel RootTXOriginal = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root");
		RootTXOriginal.setStyleName("Root");
		
		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		menuBar.setWidth("100%");
		
		MenuItem menuItem = new MenuItem(" ", false, (Command) null);
		menuItem.setHTML("Administrators");
		menuItem.setEnabled(false);
		menuBar.addItem(menuItem);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuItem mntmBack = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		menuBar.addItem(mntmBack);
		
		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		RootTXOriginal.add(horizontalSplitPanel, 0, 25);
		horizontalSplitPanel.setSize("100%", "100%");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		horizontalSplitPanel.setLeftWidget(verticalPanel);
		verticalPanel.setSize("100%", "");
		

		
		
		SimplePanel simplePanel = new SimplePanel();
		verticalPanel.add(simplePanel);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		simplePanel.setWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		textBox = new TextBox();
		textBox.setVisibleLength(50);
		horizontalPanel.add(textBox);
		textBox.setWidth("90%");
		
		Button btnNewButton = new Button("New button");
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
		btnNewButton.setText("+");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				textBox.setText(ClearText(textBox.getText()));
				String S=textBox.getText();
				if (isValidEmail(S))
				{
					
				
				
				//if (!textBox.getText().isEmpty() && textBox.getText().length()>2){
				Button btnNewButton_1 = new Button("<img src=\"Admin.gif\">" + textBox.getText());
				btnNewButton_1.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						Button sourceB=((Button)event.getSource());
						verticalPanel_1.remove(sourceB);
						if (verticalPanel_1.getWidgetCount()==0) SaveAdmins.setVisible(false);
					}
				});
				btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
					public void onMouseDown(MouseDownEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonPush");
					}
				});
				btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
					public void onMouseOut(MouseOutEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
					}
				});
				btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
					public void onMouseOver(MouseOverEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
					}
				});
				btnNewButton_1.setStyleName("gwt-ButtonTOP");
				btnNewButton_1.setText(textBox.getText());
				btnNewButton_1.setHTML("<img src=\"Admin.gif\">" + textBox.getText());
				verticalPanel_1.add(btnNewButton_1);
				textBox.setText("");
				btnNewButton_1.setSize("100%", "100%");
				SaveAdmins.setVisible(true);
				}else Window.alert(ErrorConstants.ERROR_NO_EMAIL_VALIDO + S);
			}

			private String ClearText(String text) {
				StringBuffer Sout=new StringBuffer();
				for (int i = 0; i < text.length(); i++) {
					if (text.charAt(i)!=' ')
						Sout.append(text.charAt(i));
						
				}
				return Sout.toString();
			}
			
			private native boolean isValidEmail(String email) /*-{ 
	        var reg1 = /(@.*@)|(\.\.)|(@\.)|(\.@)|(^\.)/; // not valid 
	        var reg2 = /^.+\@(\[?)[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,3}|[0-9]{1,3})(\]?)$/; // valid 
	        	return !reg1.test(email) && reg2.test(email); 
			}-*/; 
		});
		horizontalPanel.add(btnNewButton);
		btnNewButton.setWidth("66px");
		btnNewButton.setHTML("+");
		
		verticalPanel_1 = new VerticalPanel();
		verticalPanel.add(verticalPanel_1);
		verticalPanel_1.setWidth("100%");
		
		SaveAdmins = new Button("Save New Administrator");
		SaveAdmins.setVisible(false);
		SaveAdmins.addClickHandler(new ClickHandler() {
		
			private AsyncCallback<Boolean> callback;
			
			public void onClick(ClickEvent event) {
				
				Pilallamada = new Stack<UserApp>();
				int Elementos_a_salvar = verticalPanel_1.getWidgetCount();
				for (int i = 0; i < Elementos_a_salvar; i++) {
					Widget Uno = verticalPanel_1.getWidget(i);
					Button Dos = (Button) Uno;
					String Nombre = Dos.getText();
					Pilallamada.add(new UserApp(Nombre, Constants.PROFESSOR));
					}
				if (!Pilallamada.isEmpty()) {
					callback = new AsyncCallback<Boolean>() {

						public void onFailure(Throwable caught) {
							LoadingPanel.getInstance().hide();
							Window.alert("The user could not be saved at this moment");

						}

						public void onSuccess(Boolean result) {
							LoadingPanel.getInstance().hide();
							if (!result) Window.alert("The user " + Pilallamada.peek().getEmail() + " already exists (if you do not see him/her it's because he may be a student)");						
							Pilallamada.pop();
							if (!Pilallamada.isEmpty())
							{

								LoadingPanel.getInstance().center();
								LoadingPanel.getInstance().setLabelTexto("Saving...");
								bookReaderServiceHolder.saveUser(
										Pilallamada.peek(), callback);
							
							}
								else{
									refreshPanel();

								}

						}
					};
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto("Saving...");
					bookReaderServiceHolder.saveUser(Pilallamada.peek(),
							callback);

				}
				verticalPanel_1.clear();
				SaveAdmins.setVisible(false);
			}
		});
		SaveAdmins.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBottonSavePush");
			}
		});
		SaveAdmins.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBottonSave");
			}
		});
		SaveAdmins.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBottonSaveOver");
			}
		});
		SaveAdmins.setStyleName("gwt-ButtonBottonSave");
		//SaveAdmins.setStyleName("gwt-MenuItemMio");
		verticalPanel.add(SaveAdmins);
		SaveAdmins.setSize("100%", "100%");
		
		
		
		
		ScrollPanel scrollPanel = new ScrollPanel();
		horizontalSplitPanel.setRightWidget(scrollPanel);
		scrollPanel.setSize("100%", "100%");
		
		stackPanel_1 = new StackPanelMio();
		scrollPanel.setWidget(stackPanel_1);
	//	simplePanel.add(stackPanel_1);
		
		stackPanel_1.setBotonTipo(new BotonesStackPanelAdminsMio(
				"prototipo", new VerticalPanel()));
		stackPanel_1.setBotonClick(new ClickHandler() {

			public void onClick(ClickEvent event) {
				
				Long Ident=((BotonesStackPanelAdminsMio) event.getSource()).getEntidad().getID();
				String Name=((BotonesStackPanelAdminsMio) event.getSource()).getEntidad().getName();
//				if ((ActualUser.getUser().getName()!=null)&&(!ActualUser.getUser().getName().isEmpty()))
//					Name=((BotonesStackPanelAdminsMio) event.getSource()).getEntidad().get;
//					else  Name=((BotonesStackPanelAdminsMio) event.getSource()).getEntidad().getName();
				if (Window
						.confirm("Are you sure you want to delete this "+Name+"?, His/her activities, anbnotations and book will be deleted"))
				{
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto("Loading...");
				
								LoadingPanel.getInstance().center();
								LoadingPanel.getInstance().setLabelTexto("Deleting...");
								
								bookReaderServiceHolder.deleteProfessor(Ident, new AsyncCallback<Integer>() {

									public void onFailure(Throwable caught) {
										Window.alert("Sorry but the user could not be removed, try again later");
									}

									public void onSuccess(Integer result) {
										refreshPanel();
									}
								});
								
								//Salir si me borro a mi mismo
								if (ActualUser.getUser().getId()==Ident)
								{
									Window.open(ActualUser.getUser().getLogoutUrl(), "_self", "");
								ActualUser.setUser(null);
								ActualUser.setBook(null);
								ActualUser.setReadingactivity(null);
								}
								
							}
			}	
						});
				

		
				
		// Profesores
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
		bookReaderServiceHolder.getProfessor(new AsyncCallback<ArrayList<UserApp>>() {

			public void onFailure(Throwable caught) {
				LoadingPanel.getInstance().hide();
			}

			public void onSuccess(ArrayList<UserApp> result) {
				LoadingPanel.getInstance().hide();
				if (result.size() < 10) {
					for (UserApp User1 : result) {
						String Bienvenida;
						if ((User1.getName()!=null)&&(!User1.getName().isEmpty()))
							Bienvenida= User1.getName();
							else 
							Bienvenida=User1.getEmail();
						EntidadAdmin E = new EntidadAdmin(Bienvenida, User1.getId());
						stackPanel_1.addBotonLessTen(E);
					}

				} else {
					for (UserApp User1 : result) {
						String Bienvenida;
						if ((User1.getName()!=null)&&(!User1.getName().isEmpty()))
							Bienvenida= User1.getName();
							else 
							Bienvenida=User1.getEmail();
						EntidadAdmin E = new EntidadAdmin(Bienvenida, User1.getId());
						stackPanel_1.addBoton(E);
					}
				}
				stackPanel_1.setSize("100%", "100%");
				stackPanel_1.ClearEmpty();
				
			}
		});
		
		stackPanel_1.setSize("100%", "100%");
	}

	protected void refreshPanel() {
		stackPanel_1.Clear();
		//Profesores
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
		bookReaderServiceHolder.getProfessor(new AsyncCallback<ArrayList<UserApp>>() {

			public void onFailure(Throwable caught) {
				LoadingPanel.getInstance().hide();
			}

			public void onSuccess(ArrayList<UserApp> result) {
				LoadingPanel.getInstance().hide();
				if (result.size() < 10) {
					for (UserApp User1 : result) {
						String Bienvenida;
						if ((User1.getName()!=null)&&(!User1.getName().isEmpty()))
							Bienvenida= User1.getName();
							else 
							Bienvenida=User1.getEmail();
						EntidadAdmin E = new EntidadAdmin(Bienvenida, User1.getId());
						stackPanel_1.addBotonLessTen(E);
					}

				} else {
					for (UserApp User1 : result) {
						String Bienvenida;
						if ((User1.getName()!=null)&&(!User1.getName().isEmpty()))
							Bienvenida= User1.getName();
							else 
							Bienvenida=User1.getEmail();
						EntidadAdmin E = new EntidadAdmin(Bienvenida, User1.getId());
						stackPanel_1.addBoton(E);
					}
				}
				stackPanel_1.setSize("100%", "100%");
				stackPanel_1.ClearEmpty();
				
			}
		});
		
	}
}
