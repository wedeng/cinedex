package use_case.authentication;

/**
 * The Input Boundary for authentication use cases.
 */

public interface AuthenticationInputBoundary {
    /**
     * Authenticates user with OAuth method.
     * */
    void executeAuthentication();
}
