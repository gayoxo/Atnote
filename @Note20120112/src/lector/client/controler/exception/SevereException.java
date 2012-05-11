package lector.client.controler.exception;

public class SevereException extends Exception {

	public SevereException() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String text;

	public SevereException(String text) {
		this.text = text;
	}

	@Override
	public String getMessage() {
		return text;
	}
}
