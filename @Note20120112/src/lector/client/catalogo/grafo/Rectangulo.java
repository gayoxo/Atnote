package lector.client.catalogo.grafo;

public class Rectangulo {

	private int xori;
	private int yori;
	private int xfinal;
	private int yfinal;
	public int getXori() {
		return xori;
	}
	public void setXori(int xori) {
		this.xori = xori;
	}
	public int getYori() {
		return yori;
	}
	public void setYori(int yori) {
		this.yori = yori;
	}
	public int getXfinal() {
		return xfinal;
	}
	public void setXfinal(int xfinal) {
		this.xfinal = xfinal;
	}
	public int getYfinal() {
		return yfinal;
	}
	public void setYfinal(int yfinal) {
		this.yfinal = yfinal;
	}
	public Rectangulo(int xori, int yori, int xfinal, int yfinal) {
		super();
		this.xori = xori;
		this.yori = yori;
		this.xfinal = xfinal;
		this.yfinal = yfinal;
	}
	public int getwhight(){
		return xfinal-xori;
	}
	public int getheight(){
		return yfinal-yori;
	}
}
