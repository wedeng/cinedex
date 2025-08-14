package use_case.sync;

import java.util.List;

import entity.MovieInterface;

/**
 * Data access interface for sync operations.
 * Defines the contract for data access operations related to synchronization.
 */
public interface DataAccessInterfaceSync {

    /**
     * Gets the current session ID.
     * @return the current session ID or null if no active session
     * @throws SyncException if the request fails
     */
    String getCurrentSessionId() throws SyncException;

    /**
     * Gets the account ID for the current session.
     * @param sessionId the session ID
     * @return the account ID
     * @throws SyncException if the request fails
     */
    int getAccountId(String sessionId) throws SyncException;

    /**
     * Gets the username for the current session.
     * @param sessionId the session ID
     * @return the username
     * @throws SyncException if the request fails
     */
    String getUsername(String sessionId) throws SyncException;

    /**
     * Fetches user's saved movies from TMDB.
     * @param sessionId the session ID
     * @return list of saved movie IDs
     * @throws SyncException if the request fails
     */
    List<Integer> getSavedMovies(String sessionId) throws SyncException;

    /**
     * Fetches user's watched movies from TMDB.
     * @param sessionId the session ID
     * @return list of watched movie IDs
     * @throws SyncException if the request fails
     */
    List<Integer> getWatchedMovies(String sessionId) throws SyncException;

    /**
     * Fetches movie details from TMDB by ID.
     * @param movieId the movie ID
     * @return the movie details
     * @throws SyncException if the request fails
     */
    MovieInterface getMovieDetails(int movieId) throws SyncException;

    /**
     * Updates user's saved movies on TMDB.
     * @param sessionId the session ID
     * @param movieIds list of saved movie IDs
     * @throws SyncException if the request fails
     */
    void updateSavedMovies(String sessionId, List<Integer> movieIds) throws SyncException;

    /**
     * Updates user's watched movies on TMDB.
     * @param sessionId the session ID
     * @param watchedMovies list of watched movie IDs
     * @throws SyncException if the request fails
     */
    void updateWatchedMovies(String sessionId, List<Integer> watchedMovies) throws SyncException;

}
