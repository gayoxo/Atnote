package lector.client.reader;

import java.util.ArrayList;

import lector.client.book.reader.ImageService;
import lector.client.book.reader.ImageServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextArea;

public class arbitroLlamadas {

	private ArrayList<ExportObject> pendientes;
	private StringBuffer Result;
	static ImageServiceAsync imageServiceHolder = GWT
	.create(ImageService.class);
	
	public arbitroLlamadas(ArrayList<ExportObject> list) {
	pendientes=list;
	Result= new StringBuffer(
	"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head>");
	Result.append("<title>Export:");
	Result.append(System.currentTimeMillis());
	Result.append("</title><body><table width=\"100%\"><tr><td><h1>Export:");
	Result.append(System.currentTimeMillis());
	Result.append("</h1></td><td align=\"right\"><img src=\"http://a-note.appspot.com/Logo.jpg\" alt=\"atnote\" height=\"200\" width=\"400\" /> </td></tr></table>");;
	
	llamadaBucle();
	}

	private void llamadaBucle() {
		if (!pendientes.isEmpty())
		{
			ExportObject E=pendientes.get(0);
			pendientes.remove(0);
			imageServiceHolder.loadHTMLStringForExportUni(E,
					new AsyncCallback<String>() {

						public void onSuccess(String result) {
							Result.append(result);
							llamadaBucle();
						}

						public void onFailure(Throwable caught) {
							llamadaBucle();

						}
					});

		}else
		{
			Result.append("</body></html>");
			FormPanel formPanel = new FormPanel();
			formPanel
					.setEncoding(FormPanel.ENCODING_URLENCODED);
			formPanel.setMethod(FormPanel.METHOD_POST);
			TextArea textArea = new TextArea();
			textArea.setText(Result.toString());
			textArea.setName("html");
			formPanel.add(textArea);
			formPanel
					.setAction("../rs/AtNote/html/produce");
			formPanel.submit();
		}			
		
	}

}
