package lector.client.reader.export;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Stack;

import lector.client.book.reader.ImageService;
import lector.client.book.reader.ImageServiceAsync;
import lector.client.controler.InformationConstants;
import lector.client.reader.ExportObject;
import lector.client.reader.ExportObjectTemplate;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;

public class arbitroLlamadasRTF {

	private Stack<ExportObject> pendientes;
	private StringBuffer Result;
	private FormPanel formPanel;
	private Button Submit;
	static ImageServiceAsync imageServiceHolder = GWT
	.create(ImageService.class);
	
	public arbitroLlamadasRTF(ArrayList<ExportObject> list) {
	pendientes=clone(list);	

	Result= new StringBuffer();

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
			
//			\pard\s1\sb100\sa100\kerning36\b\f0\fs48 Creamos \par
//			\pard\s2\sb100\sa100\kerning0\fs36 Por dentro bien \par
//			\pard\s1\sb100\sa100\kerning36\fs48 Porque no va \par
			
			if (E instanceof ExportObjectTemplate)
			{
				ExportObjectTemplate EE=(ExportObjectTemplate) E;
				if (EE.getProfundidad()>3) EE.setProfundidad(3);
				StringBuffer SB=new StringBuffer();
				SB.append("\\pard\\s");
				SB.append(EE.getProfundidad());
				SB.append("\\sb100\\sa100\\kerning");
				switch (EE.getProfundidad()) {
				case 1:
					SB.append("36\\b\\f0\\fs48 ");
					break;
				case 2:
					SB.append("0\\fs36 ");
					break;
				case 3:
					SB.append("0\\fs27 ");
					break;
				default:
					SB.append("0\\fs27 ");
					break;
				}
				SB.append(EE.getText());
				SB.append("\\par");
				Result.append(SB.toString());
				llamadaBucle();
				
			}
			else
			{
			imageServiceHolder.loadRTFStringForExportUni(E,
					new AsyncCallback<String>() {

						public void onSuccess(String result) {
							Result.append(result);
							llamadaBucle();
						}

						public void onFailure(Throwable caught) {
							llamadaBucle();

						}
					});
			}

		}else
		{
			
			
			
			
			
			LoadingPanel.getInstance().hide();
				
		//	Result.append("</body></html>");
//			RichTextArea textArea1 = new RichTextArea();
//			textArea1.setHTML(Result.toString());	
			formPanel = new FormPanel("_blank");
			formPanel
			.setAction("../RTF.php");
			formPanel
					.setEncoding(FormPanel.ENCODING_URLENCODED);
			formPanel.setMethod(FormPanel.METHOD_POST);
			TextArea textArea = new TextArea();
			textArea.setText(Result.toString());
			textArea.setSize("100%", "100%");
			textArea.setReadOnly(true);
//			try {
//				textArea.setText(new String(Result.toString().getBytes("UTF-8")));
//				
//			} catch (UnsupportedEncodingException e) {
//				textArea.setText(e.toString());
//			}
			textArea.setName("html");
			textArea.getValue();
//			formPanel.add(textArea);
			//AQUI
			VerticalPanel V=new VerticalPanel();		
			V.add(textArea);
			V.setSize("100%", "100%");
			
			TextArea textArea2 = new TextArea();
			textArea2.setSize("100%", "100%");
			textArea2.setReadOnly(true);
			textArea2.setText(Long.toString(System.currentTimeMillis()));
//			try {
//				textArea.setText(new String(Result.toString().getBytes("UTF-8")));
//				
//			} catch (UnsupportedEncodingException e) {
//				textArea.setText(e.toString());
//			}
			textArea2.setName("ExportN");
			textArea2.getValue();
			
			//AQUI
			V.add(textArea2);


//			formPanel
//			.setAction("http://phpconvertservice.netne.net");
			
			//Window.alert(InformationConstants.WAIT_RESULTS);
			formPanel.add(V);

			RootPanel RP=RootPanel.get();
			formPanel.setVisible(false);
			RP.add(formPanel);
			Window.alert(InformationConstants.WAIT_RESULTS);
			formPanel.submit();
//			if (!Window.Navigator.getUserAgent().contains("Chrome"))
//				{
//				PopUpExportConfirm PopUpExportConfirm =new PopUpExportConfirm(formPanel);
//				PopUpExportConfirm.center();
//				}
//			else
//				{
//				formPanel.submit();
//				Window.alert(InformationConstants.WAIT_RESULTS);
//				}
			
		}			
		
	}

	

}
