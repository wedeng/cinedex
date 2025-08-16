package use_case.authentication;

import java.util.List;

import entity.User;
import entity.UserInterface;
import entity.MovieInterface;
import entity.SessionInterface;

public interface CinedexMongoDataBaseInterface {

    // --- Movie Operations ---

    /**
     * Saves movie to local database as saved.
     * @param movie the movie to save.
     * @return true if saved.
     */
    boolean saveMovieAsSaved(MovieInterface movie);

    /**
     * Saves movie to local database as watched.
     * @param movie the movie to save.
     * @return true if saved.
     */
    boolean saveMovieAsWatched(MovieInterface movie);

    /**
     * Gets a given movie from our database.
     * @param movieId the movie id.
     * @return a movie.
     */
    MovieInterface getMovie(int movieId);

    /**
     * Provides a list of saved movies from the database.
     * @return a list of saved movies.
     */
    List<MovieInterface> getAllSavedMovies();

    /**
     * Provides a list of watched movies from the database.
     * @return a list of watched movies.
     */
    List<MovieInterface> getAllWatchedMovies();

    /**
     * Deletes movie from our database.
     * @param movieId the movie id.
     * @return true if successful.
     */
    boolean deleteMovie(int movieId);

    // --- Session Operations ---

    /**
     * Saved the session id to our local database.
     * @param session the sessionId of a user.
     * @return true if the method successfully saved to the database.
     */
    boolean saveSession(SessionInterface session);

    /**
     * Provides the current session object from the database.
     * @return the current session. .
     */
    SessionInterface getCurrentSession();

    /**
     * Gets the current session ID.
     * @return the current session ID, or null if no active session exists
     */
    String getCurrentSessionId();

    // --- User Operations ---

    /**
     * Saves the current user to our database.
     * @param user the current user.
     * @return true if saved to the database.
     */
    boolean saveUser(User user);

    /**
     * Retrieves the current user from the database.
     * @param accountId the users account id.
     * @return the appUser.
     */
    UserInterface getUser(int accountId);

}
