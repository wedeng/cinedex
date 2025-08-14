package use_case.authentication;

/**
 * Data Access Interface for Authentication.
 * Defines the operations needed for an Authenticated Session to occur.
 */

public interface AuthenticationDataAccessInterface {

    /**
     * Creates a request token for TMDB authentication.
     * @return the request token
     * @throws AuthenticationException if the request fails
     */
    String makeRequestToken() throws AuthenticationException;

    /**
     * Redirect to TMDB so users can input their information.
     * @param requestToken the request token to be validated.
     * @throws AuthenticationException if request fails.
     */
    void redirectWeb(String requestToken) throws AuthenticationException;

    /**
     * Creates a session with TMDB using an approved request token.
     *
     * @param requestToken the approved request token
     * @return String of the session.
     * @throws AuthenticationException if the request fails
     */
    String makeSession(String requestToken) throws AuthenticationException;
}
