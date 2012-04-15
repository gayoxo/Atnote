package lector.client.reader.hilocomentarios;

import java.util.ArrayList;

import lector.client.reader.TextSelector;

import com.google.gwt.user.client.ui.VerticalPanel;

public class ParesLlamada {

	private VerticalPanel VP;
	private Long IDPadre;
	private Long IDThread;
	private ArrayList<TextSelector> Selectores;
	
	
	public ParesLlamada(VerticalPanel vP, Long iDPadre,Long IDThreadin,ArrayList<TextSelector> Selectoresin) {
		super();
		VP = vP;
		IDPadre = iDPadre;
		IDThread = IDThreadin;
		Selectores=Selectoresin;
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
	
	public ArrayList<TextSelector> getSelectores() {
		return Selectores;
	}
	
	public void setSelectores(ArrayList<TextSelector> selectores) {
		Selectores = selectores;
	}
}
