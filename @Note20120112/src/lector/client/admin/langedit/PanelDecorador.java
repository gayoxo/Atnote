package lector.client.admin.langedit;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.language.Language;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class PanelDecorador extends VerticalPanel {

	protected boolean saved=true; 
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	
	public abstract void saveAll();
	
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	
	public boolean isSaved() {
		return saved;
	}
	
	public void saveLanguage(Language L)
	{
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Saving...");
		bookReaderServiceHolder.saveLanguage(L, new AsyncCallback<Void>() {

			public void onFailure(Throwable caught) {
				Window.alert("The annotations could not be deleted");
				LoadingPanel.getInstance()
						.hide();
				
			}

			public void onSuccess(Void result) {
				LoadingPanel.getInstance()
				.hide();
				
			}
		});
	}
	
}
