package lector.client.catalogo.client;

public class FolderException extends Exception {

	public FolderException() {
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Texto;
	
	public FolderException(String Textoin) {
		Texto=Textoin;
	}
	@Override
	public String getMessage() {
		return Texto;
	}
}
