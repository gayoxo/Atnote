package lector.client.admin.group;

import java.util.ArrayList;

import lector.client.admin.users.BotonesStackPanelUsersMio;
import lector.client.admin.users.EntidadUser;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.StackPanelMio;
import lector.share.model.UserApp;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

public class NewUser2group extends PopupPanel {

	private GroupAndUserPanel GAUP;
	private StackPanelMio stackPanel_1;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	
	public NewUser2group(GroupAndUserPanel GAUPin) {
		super(true);
		setGlassEnabled(true);
		GAUP=GAUPin;
		
		ScrollPanel scrollPanel = new ScrollPanel();
		setWidget(scrollPanel);
		scrollPanel.setSize("443px", "411px");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		scrollPanel.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);
		
		MenuItem mntmClose = new MenuItem("Close", false, new Command() {
			public void execute() {
				hide();
			}
		});
		menuBar.addItem(mntmClose);
		
		MenuItem CreateAndAdd = new MenuItem("CreateAndAdd", false, new Command() {
			public void execute() {
				hide();
				CreateAndAdd CAA=new CreateAndAdd(GAUP);
				CAA.center();
				CAA.setVisible(true);
			}
		});
		menuBar.addItem(CreateAndAdd);
		
		
		
		
		stackPanel_1 = new StackPanelMio();
		stackPanel_1.setBotonTipo(new BotonesStackPanelUsersMio(
				"prototipo", new VerticalPanel()));
		
		stackPanel_1.setBotonClick(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String UsuarioNombre=((Button)event.getSource()).getText();
				bookReaderServiceHolder.loadUserByEmail(UsuarioNombre, new AsyncCallback<UserApp>() {

					public void onFailure(Throwable caught) {
						Window.alert("Error Loading User");
						
					}

					public void onSuccess(UserApp result) {
						ArrayList<Long> ListaYa=GAUP.getMygroup().getUsersIds();
						if (!noestaenlalista(result,ListaYa)){
							GAUP.getMygroup().getUsersIds().add(result.getId());
						bookReaderServiceHolder.saveGroup(GAUP.getMygroup(), new AsyncCallback<Void>() {

							public void onFailure(Throwable caught) {
								Window.alert("The group could not saved");
								
							}

							public void onSuccess(Void result) {
								GAUP.refresh();
								
							}
						});
						}
						else Window.alert("The user was in the list before");
						
						
					}
				});

			}

			private boolean noestaenlalista(UserApp userEnter, ArrayList<Long> listaYa) {
				for (Long UsersT : listaYa) {
					if (UsersT.equals(userEnter.getId()))return true;
				}
				return false;
			}
		});
		
		bookReaderServiceHolder.getUsersApp(new AsyncCallback<ArrayList<UserApp>>() {
			
			public void onSuccess(ArrayList<UserApp> result) {
				if (result.size()<10)
				{
					Long IDi=0l;
					for (UserApp User1 : result) {
						EntidadUser E=new EntidadUser(User1.getEmail(), IDi);
						stackPanel_1.addBotonLessTen(E);
						IDi++;
					}
					
				}else			
				{
					Long IDi=0l;
					for (UserApp User1 : result)  {
						EntidadUser E=new EntidadUser(User1.getEmail(), IDi);
						stackPanel_1.addBoton(E);
						IDi++;
					}
				}
				stackPanel_1.ClearEmpty();
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("The users could not be retrieved at this moment");
				
			}
		});
		
		stackPanel_1.setSize("100%", "100%");
		verticalPanel.add(stackPanel_1);
	
		
//		for (UsersTemlateFake User : UsuariosPos) {
//			Button button = new Button(User.getEmail());
//			button.addClickHandler(new ClickHandler() {
//				public void onClick(ClickEvent event) {
//					ArrayList<UsersTemlateFake> ListaYa=GAUP.getMygroup().getUsuarios();
//					String UsuarioNombre=((Button)event.getSource()).getHTML();
//					UsersTemlateFake UserEnter=UsesrFake.getUser(UsuarioNombre);
//					if (!noestaenlalista(UserEnter,ListaYa))GAUP.getMygroup().getUsuarios().add(UserEnter);
//					else Window.alert("The book was in the list before");
//					GAUP.refresh();
//				}
//
//				private boolean noestaenlalista(UsersTemlateFake userEnter, ArrayList<UsersTemlateFake> listaYa) {
//					for (UsersTemlateFake UsersT : listaYa) {
//						if (UsersT.getEmail().equals(userEnter.getEmail())) return true;
//					}
//					return false;
//				}
//			});
//			verticalPanel.add(button);
//			button.setWidth("100%");
//		}
		
		
		
		
		
	}
	
	public void setGAUP(GroupAndUserPanel gAUP) {
		GAUP = gAUP;
	}

}
