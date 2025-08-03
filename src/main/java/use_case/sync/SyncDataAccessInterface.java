package use_case.sync;

import entity.Movie;
import java.util.List;
import java.util.Map;

/**
 * Data access interface for sync operations.
 * Defines the contract for data access operations related to synchronization.
 */
public interface SyncDataAccessInterface {

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
     * Fetches user's rated movies from TMDB.
     * @param sessionId the session ID
     * @return map of movie ID to rating
     * @throws SyncException if the request fails
     */
    Map<Integer, Integer> getRatedMovies(String sessionId) throws SyncException;

    /**
     * Fetches user's preferred genres from TMDB.
     * @param sessionId the session ID
     * @return list of preferred genres
     * @throws SyncException if the request fails
     */
    List<String> getPreferredGenres(String sessionId) throws SyncException;

    /**
     * Fetches movie details from TMDB by ID.
     * @param movieId the movie ID
     * @return the movie details
     * @throws SyncException if the request fails
     */
    Movie getMovieDetails(int movieId) throws SyncException;

    /**
     * Updates user's saved movies on TMDB.
     * @param sessionId the session ID
     * @param movieIds list of saved movie IDs
     * @throws SyncException if the request fails
     */
    void updateSavedMovies(String sessionId, List<Integer> movieIds) throws SyncException;

    /**
     * Updates user's rated movies on TMDB.
     * @param sessionId the session ID
     * @param ratedMovies map of movie ID to rating
     * @throws SyncException if the request fails
     */
    void updateRatedMovies(String sessionId, Map<Integer, Integer> ratedMovies) throws SyncException;
} 