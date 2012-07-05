package lector.client.reader.export;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Stack;

import lector.client.book.reader.ImageService;
import lector.client.book.reader.ImageServiceAsync;
import lector.client.controler.InformationConstants;
import lector.client.reader.ExportObject;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class arbitroLlamadasHTML {

	private Stack<ExportObject> pendientes;
	private StringBuffer Result;
	static ImageServiceAsync imageServiceHolder = GWT
	.create(ImageService.class);
	
	public arbitroLlamadasHTML(ArrayList<ExportObject> list) {
	pendientes=clone(list);	

	Result= new StringBuffer(
	"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head>");
	Result.append("<title>Export:");
	Result.append(System.currentTimeMillis());
	Result.append("</title><body><table width=\"100%\"><tr><td><h1>Export:");
	Result.append(System.currentTimeMillis());
	Result.append("</h1></td><td align=\"right\"><img src=\"http://a-note.appspot.com/logo_200_400.jpg\" alt=\"atnote\" height=\"200\" width=\"400\" /> </td></tr></table>");;
	
	LoadingPanel.getInstance().center();
	LoadingPanel.getInstance().setLabelTexto("Loading...");
	//llamadaBucle();
	}

	private Stack<ExportObject> clone(ArrayList<ExportObject> list) {
		Stack<ExportObject> EO=new Stack<ExportObject>();
		for (int i = 1; i <= list.size(); i++) {
			EO.push(list.get(list.size()-i));
		}
//		for (ExportObject exportObject : list) {
//			EO.push(exportObject);
//		}
		return EO;
	}

	public void llamadaBucle() {
		if (!pendientes.isEmpty())
		{
			ExportObject E=pendientes.pop();
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
			
			LoadingPanel.getInstance().hide();
			//	Result.append("</body></html>");
//				RichTextArea textArea1 = new RichTextArea();
//				textArea1.setHTML(Result.toString());	
				FormPanel formPanel = new FormPanel();
				formPanel
						.setEncoding(FormPanel.ENCODING_URLENCODED);
				formPanel.setMethod(FormPanel.METHOD_POST);
				TextArea textArea = new TextArea();
				textArea.setText(Result.toString());
//				try {
//					textArea.setText(new String(Result.toString().getBytes("UTF-8")));
//					
//				} catch (UnsupportedEncodingException e) {
//					textArea.setText(e.toString());
//				}
				textArea.setName("html");
				textArea.getValue();
				VerticalPanel V=new VerticalPanel();
				formPanel.add(V);
				V.add(textArea);
				TextArea textArea2 = new TextArea();
				textArea2.setText(Long.toString(System.currentTimeMillis()));
//				try {
//					textArea.setText(new String(Result.toString().getBytes("UTF-8")));
//					
//				} catch (UnsupportedEncodingException e) {
//					textArea.setText(e.toString());
//				}
				textArea2.setName("ExportN");
				textArea2.getValue();
				V.add(textArea2);
				formPanel
						.setAction("../HTML.php");
//				formPanel
//				.setAction("http://phpconvertservice.netne.net");
				
				Window.alert(InformationConstants.WAIT_RESULTS);
				formPanel.submit();
		}			
		
	}

}
