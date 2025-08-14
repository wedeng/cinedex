package interface_adapter.authentication;

import use_case.authentication.AuthenticationInputBoundary;

public class AuthenticationController {
    private final AuthenticationInputBoundary authenticationInputBoundary;

    public AuthenticationController(AuthenticationInputBoundary authenticationInputBoundary) {
        this.authenticationInputBoundary = authenticationInputBoundary;
    }

    /**
     * Execute the authentication use case.
     */
    public void authenticate() {
        this.authenticationInputBoundary.executeAuthentication();
    }
}
