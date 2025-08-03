package interface_adapter.saved;

/**
 * The state information present for saved use case (add or remove from saved).
 */

public class SavedState {
    private String savedTaskError;
    private boolean savedTaskSuccess;

    public SavedState() {
        this.savedTaskError = null;
        this.savedTaskSuccess = false;
    }

    public String getSavedTaskError() {
        return this.savedTaskError;
    }

    /**
     * Sets the error message for a failed case regarding the saved use case.
     * @param savedTaskError the explanation for error.
     */
    public void setSavedTaskError(String savedTaskError) {
        this.savedTaskError = savedTaskError;
        this.savedTaskSuccess = false;
    }

    public boolean isSavedTaskSuccess() {
        return this.savedTaskSuccess;
    }

    /**
     * Sets the status of a saved task (add or removed from save).
     * @param savedTaskStatus the saved task status.
     */
    public void setSavedTaskSuccess(boolean savedTaskStatus) {
        this.savedTaskSuccess = savedTaskStatus;
        this.savedTaskError = null;
    }
}
