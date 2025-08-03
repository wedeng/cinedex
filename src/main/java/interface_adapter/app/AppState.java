package interface_adapter.app;

import interface_adapter.view.ViewCard;

/**
 * The State for the app.
 */
public class AppState {

    private String error;

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }
}
