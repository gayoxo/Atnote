package lector.client.reader;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BookNotFoundException extends Exception implements Serializable{
    private String errorMessage;

    public BookNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public BookNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
