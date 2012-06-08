package lector.client.catalogo;

public class EstadoElementKey {

	private ElementKey EK;
	private boolean B;
	
	public EstadoElementKey(ElementKey eK, boolean b) {
		EK=eK;
		B=b;
	}

	public ElementKey getEK() {
		return EK;
	}

	public void setEK(ElementKey eK) {
		EK = eK;
	}

	public boolean isB() {
		return B;
	}

	public void setB(boolean b) {
		B = b;
	}

	
	
}
