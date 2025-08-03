package use_case.authentication;

import entity.AppUser;
import entity.Movie;
import java.util.List;

/**
 * Data access interface for authentication operations.
 * Defines the contract for data access operations related to authentication and sync.
 */
public interface AuthDataAccessInterface {

    /**
     * Creates a request token for TMDB authentication.
     * @return the request token
     * @throws AuthException if the request fails
     */
    String createRequestToken() throws AuthException;

    /**
     * Creates a session with TMDB using an approved request token.
     * @param requestToken the approved request token
     * @return the session ID
     * @throws AuthException if the request fails
     */
    String createSession(String requestToken) throws AuthException;

    /**
     * Gets the account details for the current session.
     * @param sessionId the session ID
     * @return the account ID
     * @throws AuthException if the request fails
     */
    int getAccountId(String sessionId) throws AuthException;

    /**
     * Gets the username for the current session.
     * @param sessionId the session ID
     * @return the username
     * @throws AuthException if the request fails
     */
    String getUsername(String sessionId) throws AuthException;

    /**
     * Fetches user's saved movies from TMDB.
     * @param sessionId the session ID
     * @return list of saved movie IDs
     * @throws AuthException if the request fails
     */
    List<Integer> getSavedMovies(String sessionId) throws AuthException;

    /**
     * Fetches user's rated movies from TMDB.
     * @param sessionId the session ID
     * @return map of movie ID to rating
     * @throws AuthException if the request fails
     */
    java.util.Map<Integer, Integer> getRatedMovies(String sessionId) throws AuthException;

    /**
     * Fetches user's preferred genres from TMDB.
     * @param sessionId the session ID
     * @return list of preferred genres
     * @throws AuthException if the request fails
     */
    List<String> getPreferredGenres(String sessionId) throws AuthException;

    /**
     * Fetches movie details from TMDB by ID.
     * @param movieId the movie ID
     * @return the movie details
     * @throws AuthException if the request fails
     */
    Movie getMovieDetails(int movieId) throws AuthException;

    /**
     * Updates user's saved movies on TMDB.
     * @param sessionId the session ID
     * @param movieIds list of saved movie IDs
     * @throws AuthException if the request fails
     */
    void updateSavedMovies(String sessionId, List<Integer> movieIds) throws AuthException;

    /**
     * Updates user's rated movies on TMDB.
     * @param sessionId the session ID
     * @param ratedMovies map of movie ID to rating
     * @throws AuthException if the request fails
     */
    void updateRatedMovies(String sessionId, java.util.Map<Integer, Integer> ratedMovies) throws AuthException;

    /**
     * Deletes a session.
     * @param sessionId the session ID to delete
     * @throws AuthException if the request fails
     */
    void deleteSession(String sessionId) throws AuthException;
} 