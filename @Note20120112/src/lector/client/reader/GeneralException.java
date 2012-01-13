package lector.client.reader;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GeneralException extends Exception implements Serializable{
    private String errorMessage;

    public GeneralException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public GeneralException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
