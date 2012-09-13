package lector.client.reader.hilocomentarios;

import java.util.ArrayList;
import java.util.Stack;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.reader.MainEntryPoint;
import lector.share.model.AnnotationThread;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProcesoCarga {

	private ParesLlamada P;
	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	
	public ProcesoCarga(ParesLlamada p) {
		this.P=p;
		AsyncCallback<ArrayList<AnnotationThread>> Callback=new AsyncCallback<ArrayList<AnnotationThread>>() {
			
			public void onSuccess(ArrayList<AnnotationThread> result) {
				if (result==null) result = new ArrayList<AnnotationThread>();
				for (AnnotationThread annotationThread : result) {
					Respuesta R=new Respuesta(annotationThread,P.getSelectores());
					P.getVP().add(R);
					
					JeraquiaSimulada JS=new JeraquiaSimulada();
					Arbitro.getInstance().addLlamada(new ParesLlamada(JS.getVerticalPanel(), P.getIDPadre(),annotationThread.getId(),P.getSelectores()));
					P.getVP().add(JS);
//					MainEntryPoint.setPorcentScrollAnnotationsPanel();
				}
				
				Stack<ParesLlamada> Pila = Arbitro.getInstance().getPllamada();
				
				if (!Pila.isEmpty())
				{
					ParesLlamada P2=Pila.pop();
					new ProcesoCarga(P2);
				}
				else Arbitro.getInstance().setActivo(false);
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Error Mensaje");
				//TODO error mensaje cambiar
				
			}
		};
//		if (P.getIDThread().equals(Constants.THREADFATHERNULLID))
//			bookReaderServiceHolder.getAnnotationThreadsByItsFather(P.getIDPadre(),P.getIDThread(),Callback);
//		else 
			bookReaderServiceHolder.getAnnotationThreadsByItsFather(P.getIDPadre(),P.getIDThread(),Callback);
	}
	
	

}
