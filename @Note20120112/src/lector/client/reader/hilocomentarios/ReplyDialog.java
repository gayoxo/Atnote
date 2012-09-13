package lector.client.reader.hilocomentarios;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.login.ActualUser;
import lector.client.reader.MainEntryPoint;
import lector.client.reader.RichTextToolbar;
import lector.share.model.AnnotationThread;
import lector.share.model.Language;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;

import java.util.ArrayList;
import java.util.Date;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

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
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	public ReplyDialog(Long Padrein, Long AnotPadrein) {
		super(false);
		Padre = Padrein;
		AnotPadre = AnotPadrein;
		setAnimationEnabled(true);
		Date now = new Date();
		if ((ActualUser.getUser().getName()!=null)&&(!ActualUser.getUser().getName().isEmpty()))
			 setHTML(ActualUser.getUser().getName() + "  -  " + now.toGMTString());
			else  setHTML(ActualUser.getUser().getEmail() + "  -  " + now.toGMTString());
		richTextArea = new RichTextArea();
		toolbar = new RichTextToolbar(richTextArea);
		verticalPanel = new VerticalPanel();

		menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);

		Language ActualLang = ActualUser.getLanguage();

		Guardar = new MenuItem(ActualLang.getSave(), false, new Command() {
			public void execute() {
				String Nombre;
				if ((ActualUser.getUser().getName()!=null)&&(!ActualUser.getUser().getName().isEmpty()))
					Nombre=ActualUser.getUser().getName();
					else Nombre=ActualUser.getUser().getEmail();
				AnnotationThread AT = new AnnotationThread(Padre, AnotPadre,
						new ArrayList<Long>(),
						new Text(richTextArea.getHTML()), ActualUser.getUser()
								.getId(), Nombre);
				bookReaderServiceHolder.saveAnnotationThread(AT,
						new AsyncCallback<Long>() {

							public void onSuccess(Long result) {
								MainEntryPoint.refreshP();
								hide();

							}

							public void onFailure(Throwable caught) {
								Window.alert("Se ha producido un error al salvar el hilo-comentario");
								// TODO: Mensaje.

							}
						});
				
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
