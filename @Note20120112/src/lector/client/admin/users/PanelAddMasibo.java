package lector.client.admin.users;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;


import lector.client.admin.group.CreateAndAdd;
import lector.client.controler.ErrorConstants;

public class PanelAddMasibo extends PopupPanel {

	private NewUserAdministrator Padre;
	private CreateAndAdd Padre2;
	private TextArea textArea;
	private MenuBar menuBar;
	
	public PanelAddMasibo(NewUserAdministrator padre) {
		super(false);
		Padre=padre;
		setup();
		MenuItem AddMasibo = new MenuItem("Add", false, new Command() {
			public void execute() {
				String Textogeneral=textArea.getText();
				String[] Lineas=Textogeneral.split("\n");
				for (String Linea : Lineas) {
					String Limpia=ClearText(Linea);
					if (!Limpia.isEmpty())
						if (isValidEmail(Limpia))
							Padre.addText(Limpia);
						else Window.alert(ErrorConstants.ERROR_NO_EMAIL_VALIDO + Limpia);
				}
				hide();
				
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
		AddMasibo.setHTML("Add Writed");
		menuBar.addItem(AddMasibo);
		
	}

	public PanelAddMasibo(CreateAndAdd yO) {
		super(false);
		Padre2=yO;
		setup();
		MenuItem AddMasibo = new MenuItem("Add", false, new Command() {
			public void execute() {
				String Textogeneral=textArea.getText();
				String[] Lineas=Textogeneral.split("\n");
				for (String Linea : Lineas) {
					String Limpia=ClearText(Linea);
					if (!Limpia.isEmpty())
						if (isValidEmail(Limpia))
							Padre2.addText(Limpia);
						else Window.alert(ErrorConstants.ERROR_NO_EMAIL_VALIDO + Limpia);
				}
				hide();
				
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
		AddMasibo.setHTML("Add Writed");
		menuBar.addItem(AddMasibo);
	}

	
	private void setup()
	{

		
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		setWidget(dockLayoutPanel);
		dockLayoutPanel.setSize("422px", "498px");
		
		menuBar = new MenuBar(false);
		dockLayoutPanel.addNorth(menuBar, 1.7);
		
		MenuItem BotonClose = new MenuItem("Close", false, new Command() {
			public void execute() {
				hide();
			}
		});
		BotonClose.setHTML("Close");
		menuBar.addItem(BotonClose);
		
		
		
		textArea = new TextArea();
		dockLayoutPanel.add(textArea);
		
	}
}
