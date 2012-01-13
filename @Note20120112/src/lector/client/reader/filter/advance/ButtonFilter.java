package lector.client.reader.filter.advance;

import com.google.gwt.user.client.ui.Button;

public class ButtonFilter extends Button {
	
	private Long ID;
	private Tiposids Idtipo;
	
	public ButtonFilter(String HTML,Long IDin,Tiposids idtipo) {
		super(HTML);
		ID=IDin;
		Idtipo = idtipo;
	}
	
	public void setIdtipo(Tiposids idtipo) {
		Idtipo = idtipo;
	}
	
	public Long getID() {
		return ID;
	}
	
	public Tiposids getIdtipo() {
		return Idtipo;
	}
	
	public void setID(Long iD) {
		ID = iD;
	}
	
	

}
