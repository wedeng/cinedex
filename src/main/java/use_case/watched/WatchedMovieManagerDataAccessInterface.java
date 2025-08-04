package use_case.watched;

import entity.MovieInterface;

/**
 * An interface for the watched movie service that's designed to add and remove watched movies.
 */

public interface WatchedMovieManagerDataAccessInterface {
    /**
     * Service that adds the given movie to watched list.
     * @param movie the given movie to add to the users watched list.
     */
    void addWatchedMovie(MovieInterface movie);

    /**
     * Service that removes the given movie from watched list.
     * @param movie the given movie to remove from the users watched list.
     */
    void removeWatchedMovie(MovieInterface movie);
}
