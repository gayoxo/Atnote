package lector.client.reader;

import java.io.Serializable;

@SuppressWarnings("serial")
public class IlegalFolderFusionException extends Exception implements Serializable{
    private String errorMessage;

    public IlegalFolderFusionException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public IlegalFolderFusionException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
