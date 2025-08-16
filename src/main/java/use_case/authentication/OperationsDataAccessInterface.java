package use_case.authentication;

import java.util.List;

import entity.MovieInterface;

/**
 * Preforms critical operations for the authentication use case.
 */

public interface OperationsDataAccessInterface {
    /**
     * Gets the account details for the current session.
     * @param sessionId the session ID
     * @return the account ID
     * @throws AuthenticationException if the request fails
     */
    int getAccountId(String sessionId) throws AuthenticationException;

    /**
     * Gets the username for the current session.
     * @param sessionId the session ID
     * @return the username
     * @throws AuthenticationException if the request fails
     */
    String getUsername(String sessionId) throws AuthenticationException;

    /**
     * Fetches user's saved movies from TMDB.
     * @param sessionId the session ID
     * @return list of saved movie IDs
     * @throws AuthenticationException if the request fails
     */
    List<Integer> getSavedMovies(String sessionId) throws AuthenticationException;

    /**
     * Fetches user's watched movies from TMDB.
     * @param sessionId the session ID
     * @return list of watched movie IDs
     * @throws AuthenticationException if the request fails
     */
    List<Integer> getWatchedMovies(String sessionId) throws AuthenticationException;

    /**
     * Fetches movie details from TMDB by ID.
     * @param movieId the movie ID
     * @return the movie details
     * @throws AuthenticationException if the request fails
     */
    MovieInterface getMovieDetails(int movieId) throws AuthenticationException;

    /**
     * Updates user's saved movies on TMDB.
     * @param sessionId the session ID
     * @param movieIds list of saved movie IDs
     * @throws AuthenticationException if the request fails
     */
    void updateSavedMovies(String sessionId, List<Integer> movieIds) throws AuthenticationException;

    /**
     * Updates user's watched movies on TMDB.
     * @param sessionId the session ID
     * @param watchedMovies list of watched movie IDs
     * @throws AuthenticationException if the request fails
     */
    void updateWatchedMovies(String sessionId, List<Integer> watchedMovies) throws AuthenticationException;

    /**
     * Deletes a session.
     * @param sessionId the session ID to delete
     * @throws AuthenticationException if the request fails
     */
    void deleteSession(String sessionId) throws AuthenticationException;

}
