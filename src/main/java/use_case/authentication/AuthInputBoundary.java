package use_case.authentication;

/**
 * The Input Boundary for authentication use cases.
 * Handles user authentication with TMDB.
 */
public interface AuthInputBoundary {

    /**
     * Creates a request token for TMDB authentication.
     */
    void executeCreateRequestToken();

    /**
     * Authenticates user with TMDB using approved request token.
     * @param requestToken the approved request token from TMDB
     */
    void executeAuthenticate(String requestToken);

    /**
     * Logs out the current user.
     */
    void executeLogout();
}
