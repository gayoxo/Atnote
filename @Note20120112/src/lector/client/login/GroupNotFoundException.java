package lector.client.login;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

@SuppressWarnings("serial")
public class GroupNotFoundException extends Exception implements Serializable,IsSerializable{
    private String errorMessage;

    public GroupNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public GroupNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
