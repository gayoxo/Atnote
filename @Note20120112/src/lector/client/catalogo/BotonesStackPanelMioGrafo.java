package lector.client.catalogo;

public class BotonesStackPanelMioGrafo extends BotonesStackPanelMio {

	public BotonesStackPanelMioGrafo(String HTML) {
		super(HTML);
	}

	@Override
	public BotonesStackPanelMio Clone() {

		BotonesStackPanelMioGrafo New=new BotonesStackPanelMioGrafo(getHTML());	
		New.setActual(getActual());
		return New;
	}
}
