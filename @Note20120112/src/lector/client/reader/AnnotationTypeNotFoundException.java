package lector.client.reader;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AnnotationTypeNotFoundException extends Exception implements Serializable{
    private String errorMessage;

    public AnnotationTypeNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public AnnotationTypeNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
