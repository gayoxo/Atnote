package lector.client.controler.exception;

public class WarningException extends Exception {

	public WarningException() {
	}
	
	private static final long serialVersionUID = 1L;
	private String text;
	
	public WarningException(String text) {
		this.text= text;
	}
	@Override
	public String getMessage() {
		return text;
	}
}
