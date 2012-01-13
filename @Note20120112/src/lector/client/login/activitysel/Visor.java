package lector.client.login.activitysel;


import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

public class Visor extends PopupPanel {

	public String Book="8topAAAAYAAJ";
	public Visor Yo;
	
	public Visor(String Bookin) {
		super(false);
		Book=Bookin;
		Yo=this;
		String[] Booksplit=Book.split("&");
		Book=Booksplit[0];
		setGlassEnabled(true);
		SimplePanel simplePanel = new SimplePanel();
		setWidget(simplePanel);
		//simplePanel.setSize("100%", "100%");
		simplePanel.setSize("846px", "608px");
		
		FlowPanel verticalPanel = new FlowPanel();
		simplePanel.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);
		
		MenuItem mntmClose = new MenuItem("Close", false, new Command() {
			public void execute() {
			Yo.hide();
			}
		});
		menuBar.addItem(mntmClose);
		
		String Direccion=Book +"&printsec=frontcover&output=embed";
		
		TextBox textBox = new TextBox();
		textBox.setVisibleLength(180);
		textBox.setReadOnly(true);
		textBox.setText(Direccion);
		verticalPanel.add(textBox);
		textBox.setWidth("839px");
		
		Frame frame = new Frame(Direccion);
		verticalPanel.add(frame);
		frame.setSize("842px", "555px");
		
	}



}
