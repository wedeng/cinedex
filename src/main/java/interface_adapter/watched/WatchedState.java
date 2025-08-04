package interface_adapter.watched;

/**
 * The state information present for watched use case (add or remove from watched).
 */

public class WatchedState {
    private String watchedManagerErrorMessage;
    private boolean watchedManagerSuccess;

    public WatchedState() {
        this.watchedManagerErrorMessage = null;
        this.watchedManagerSuccess = false;
    }

    public String getWatchedManagerErrorMessage() {
        return this.watchedManagerErrorMessage;
    }

    /**
     * Sets the error message for a failed case regarding the watched use case.
     * @param watchedManagerErrorMessage the explanation for error.
     */
    public void setWatchedManagerErrorMessage(String watchedManagerErrorMessage) {
        this.watchedManagerErrorMessage = watchedManagerErrorMessage;
        this.watchedManagerSuccess = false;

    }

    public boolean isWatchedManagerSuccess() {
        return this.watchedManagerSuccess;
    }

    /**
     * Sets the status of a watched task (add or removed from watched).
     * @param watchedManagerSuccess the watched task status.
     */
    public void setWatchedManagerSuccess(boolean watchedManagerSuccess) {
        this.watchedManagerSuccess = watchedManagerSuccess;
        this.watchedManagerErrorMessage = null;
    }
}
