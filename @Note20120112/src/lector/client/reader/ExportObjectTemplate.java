package lector.client.reader;

public class ExportObjectTemplate extends ExportObject {

	private int profundidad;
	private String Text;
	
	public ExportObjectTemplate(int profundidadin, String Textin) {
		profundidad=profundidadin;
		Text=Textin;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}
	
	
}
