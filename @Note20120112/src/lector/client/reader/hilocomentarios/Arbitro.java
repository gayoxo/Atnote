package lector.client.reader.hilocomentarios;

import java.util.ArrayList;
import java.util.Stack;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.reader.annotthread.AnnotationThread;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.core.client.GWT;

public class Arbitro {

	private Stack<ParesLlamada> Pllamada;
	private boolean activo;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	
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
		ParesLlamada P=Pllamada.pop();
		Long L=-1l;
		AnnotationThread A=new AnnotationThread(L, L1.getIDPadre(), new ArrayList<Long>(), new Text("Reply1"));
		Respuesta R=new Respuesta(A);
		L1.getVP().add(R);
		}
		
	}
	
}
