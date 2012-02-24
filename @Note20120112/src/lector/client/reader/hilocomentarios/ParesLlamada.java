package lector.client.reader.hilocomentarios;

import com.google.gwt.user.client.ui.VerticalPanel;

public class ParesLlamada {

	private VerticalPanel VP;
	private Long IDPadre;
	private Long IDThread;
	
	
	public ParesLlamada(VerticalPanel vP, Long iDPadre,Long IDThreadin) {
		super();
		VP = vP;
		IDPadre = iDPadre;
		IDThread = IDThreadin;
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
	
	public Long getIDThread() {
		return IDThread;
	}
	
	public void setIDThread(Long iDThread) {
		IDThread = iDThread;
	}
}
