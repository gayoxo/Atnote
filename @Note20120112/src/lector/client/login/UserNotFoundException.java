package lector.client.login;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception implements IsSerializable {

    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public UserNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
