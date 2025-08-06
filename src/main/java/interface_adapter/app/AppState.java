package interface_adapter.app;

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
