package lector.client.reader.hilocomentarios;

import com.google.gwt.user.client.ui.VerticalPanel;

public class ParesLlamada {

	private VerticalPanel VP;
	private Long IDPadre;
	
	
	public ParesLlamada(VerticalPanel vP, Long iDPadre) {
		super();
		VP = vP;
		IDPadre = iDPadre;
	}
	
	public Long getIDPadre() {
		return IDPadre;
	}
	
	public VerticalPanel getVP() {
		return VP;
	}
	
	public void setIDPadre(Long iDPadre) {
		IDPadre = iDPadre;
	}
	
	public void setVP(VerticalPanel vP) {
		VP = vP;
	}
	
}
