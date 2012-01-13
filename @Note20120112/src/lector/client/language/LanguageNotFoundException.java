package lector.client.language;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LanguageNotFoundException extends Exception implements Serializable{
    private String errorMessage;

    public LanguageNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LanguageNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
