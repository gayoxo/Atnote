package lector.client.reader.hilocomentarios;

import lector.client.language.Language;
import lector.client.login.ActualUser;
import lector.client.reader.RichTextToolbar;
import lector.client.reader.annotthread.AnnotationThread;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;

import java.util.ArrayList;
import java.util.Date;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

public class ReplyDialog extends DialogBox {

	
	private RichTextArea richTextArea;
	private RichTextToolbar toolbar;
	private VerticalPanel verticalPanel;
	private MenuBar menuBar;
	private MenuItem Guardar;
	private MenuItem Clear;
	private MenuItem Cancel;
	private Long Padre;
	private Long AnotPadre;
	
	
	public ReplyDialog(Long Padrein, Long AnotPadrein) {
		super(false);
		Padre=Padrein;
		AnotPadre=AnotPadrein;
		setAnimationEnabled(true);
		Date now = new Date();
		setHTML(ActualUser.getUser().getEmail() + "  -  " + now.toGMTString());
		richTextArea = new RichTextArea();
		toolbar = new RichTextToolbar(richTextArea);
		verticalPanel=new VerticalPanel();
		
		menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);
		
		Language ActualLang = ActualUser.getLanguage();
		
		Guardar = new MenuItem(ActualLang.getSave(), false, new Command() {
			public void execute() {
				AnnotationThread AT=new AnnotationThread(Padre, AnotPadre, new ArrayList<Long>(), new Text(richTextArea.getText()),ActualUser.getUser().getId());
				//TODO Insertar añadir
				hide();
			}
		});
		menuBar.addItem(Guardar);
		Guardar.setHTML(ActualLang.getSave());
		
		Clear = new MenuItem(ActualLang.getClearAnot(), false, new Command() {
			public void execute() {
				richTextArea.setText("");
			}
		});
		menuBar.addItem(Clear);
		Clear.setHTML(ActualLang.getClearAnot());
		
		Cancel = new MenuItem(ActualLang.getCancel(), false, new Command() {
			public void execute() {
				hide();
			}
		});
		Cancel.setHTML(ActualLang.getCancel());
		menuBar.addItem(Cancel);
		verticalPanel.add(toolbar);
		verticalPanel.add(richTextArea);
		richTextArea.setWidth("99%");
		setWidget(verticalPanel);
		
	}

}
