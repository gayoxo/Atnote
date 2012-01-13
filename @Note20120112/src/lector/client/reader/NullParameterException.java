package lector.client.reader;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NullParameterException extends Exception implements Serializable{
    private String errorMessage;

    public NullParameterException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public NullParameterException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
