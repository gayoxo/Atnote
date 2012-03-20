package lector.client.login;



import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Constants;
import lector.client.service.AnnotationSchema;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Generador_Basico implements EntryPoint {

	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	
	public void onModuleLoad() {
//		UserApp adminUser = new UserApp();
//		adminUser.setEmail("root");
//		adminUser.setProfile(Constants.PROFESSOR);
//		bookReaderServiceHolder.saveUser(adminUser, new AsyncCallback<Boolean>() {
//
//			public void onSuccess(Boolean result) {
//
//			}
//
//			public void onFailure(Throwable caught) {
//				Window.alert("Ha fallado La carga de root");
//
//			}
//		});
		
//		String A="https://chart.googleapis.com/chart?cht=gv:dot&chl=digraph{122->312;122->322;312->322}&chof=json";
//		bookReaderServiceHolder.getJSONServiceTODrawGraph(URL.encode(A), new AsyncCallback<String>() {
//			
//			public void onSuccess(String result) {
//				Window.alert(result);
//				
//			}
//			
//			public void onFailure(Throwable caught) {
//				Window.alert("error");
//				
//			}
//		});
//		
//		bookReaderServiceHolder.getSchemaByCatalogId(222l, new AsyncCallback<ArrayList<AnnotationSchema>>() {
//			
//			public void onSuccess(ArrayList<AnnotationSchema> result) {
//				StringBuilder SB=new StringBuilder();
//				for (AnnotationSchema annotationSchema : result) {
//					SB.append(annotationSchema.toString());
//				}
//				Window.alert(SB.toString());
//				
//			}
//			
//			public void onFailure(Throwable caught) {
//				Window.alert("error");
//				
//			}
//		});
	}

}
