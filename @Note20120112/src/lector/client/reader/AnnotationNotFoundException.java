package lector.client.reader;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AnnotationNotFoundException extends Exception implements Serializable{
    private String errorMessage;

    public AnnotationNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public AnnotationNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
