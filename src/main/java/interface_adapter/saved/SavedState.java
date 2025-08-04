package interface_adapter.saved;

/**
 * The state information present for saved use case (add or remove from saved).
 */

public class SavedState {
    private String savedManagerErrorMessage;
    private boolean savedManagerSuccess;

    public SavedState() {
        this.savedManagerErrorMessage = null;
        this.savedManagerSuccess = false;
    }

    public String getSavedManagerErrorMessage() {
        return this.savedManagerErrorMessage;
    }

    /**
     * Sets the error message for a failed case regarding the saved use case.
     * @param savedManagerErrorMessage the explanation for error.
     */
    public void setSavedManagerErrorMessage(String savedManagerErrorMessage) {
        this.savedManagerErrorMessage = savedManagerErrorMessage;
        this.savedManagerSuccess = false;
    }

    public boolean isSavedManagerSuccess() {
        return this.savedManagerSuccess;
    }

    /**
     * Sets the status of a saved task (add or removed from save).
     * @param savedTaskStatus the saved task status.
     */
    public void setSavedManagerSuccess(boolean savedTaskStatus) {
        this.savedManagerSuccess = savedTaskStatus;
        this.savedManagerErrorMessage = null;
    }
}
