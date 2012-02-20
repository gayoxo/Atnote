package lector.client.catalogo.client;

public class DecendanceException extends Exception {

	public DecendanceException() {
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Texto;
	
	public DecendanceException(String Textoin) {
		Texto=Textoin;
	}
	@Override
	public String getMessage() {
		return Texto;
	}
}
