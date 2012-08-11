package lector.client.controler;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;

import com.google.gwt.core.client.GWT;

public class ActualizarActividad {

	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	
	public ActualizarActividad() {
		//Obtener todas las actividades y despues actualizarlas
	}
}
