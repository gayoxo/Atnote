package lector.client.catalogo.client;

public class FileException extends Exception {

	public FileException() {
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Texto;
	
	public FileException(String Textoin) {
		Texto=Textoin;
	}
	@Override
	public String getMessage() {
		return Texto;
	}
}
