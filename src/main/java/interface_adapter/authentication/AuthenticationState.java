package interface_adapter.authentication;

/**
 * The state information present for authentication use case (add or remove from saved).
 */

public class AuthenticationState {
    private String authenticationErrorMessage;
    private boolean authenticationSuccess;

    public AuthenticationState() {
        this.authenticationErrorMessage = null;
        this.authenticationSuccess = false;
    }

    public String getAuthenticationErrorMessage() {
        return this.authenticationErrorMessage;
    }

    /**
     * Sets the error message for a failed case regarding the use case.
     * @param authenticationErrorMessage the explanation for error.
     */
    public void setAuthenticationErrorMessage(String authenticationErrorMessage) {
        this.authenticationErrorMessage = authenticationErrorMessage;
        this.authenticationSuccess = false;
    }

    public boolean isAuthenticationSuccess() {
        return this.authenticationSuccess;
    }

    /**
     * Sets the status of a success of the use case.
     * @param authenticationSuccess the authentication task status.
     */
    public void setAuthenticationSuccess(boolean authenticationSuccess) {
        this.authenticationSuccess = authenticationSuccess;
        this.authenticationErrorMessage = null;
    }

}
