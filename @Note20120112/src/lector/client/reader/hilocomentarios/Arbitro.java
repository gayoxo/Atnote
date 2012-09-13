package lector.client.reader.hilocomentarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Constants;
import lector.client.login.ActualUser;
import lector.share.model.AnnotationThread;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Arbitro {

	private Stack<ParesLlamada> Pllamada;
	private boolean activo;

	
	private static Arbitro Yo;
	
	
	public Arbitro() {
	Pllamada=new Stack<ParesLlamada>();
	activo=false;
	}
	
	
	public static Arbitro getInstance()
	{
	if (Yo==null) Yo=new Arbitro();
	return Yo;
	}
	
	public void addLlamada(ParesLlamada L1)
	{
	Pllamada.add(L1);
	if (!activo)
		{
		activo=true;
		ParesLlamada P=Pllamada.pop();
		new ProcesoCarga(P);
		}
//		Long L=-1l;
//		AnnotationThread A=new AnnotationThread(L, L1.getIDPadre(), new ArrayList<Long>(), new Text("Reply1"),ActualUser.getUser().getId(),ActualUser.getUser().getEmail());
//		A.setCreatedDate(new Date());
//		Respuesta R=new Respuesta(A);
//		L1.getVP().add(R);
		
		
	}


	
	public Stack<ParesLlamada> getPllamada() {
		return Pllamada;
	}
	
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}

