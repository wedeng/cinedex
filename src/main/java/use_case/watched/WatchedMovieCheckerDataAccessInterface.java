package use_case.watched;

import entity.MovieInterface;

/**
 * An interface for the watched movie service that's designed to access critical data.
 */

public interface WatchedMovieCheckerDataAccessInterface {
    /**
     * Service that checks if the given movie is already in the watched list.
     * @param movie the given movie.
     * @return true if the given movie is not in watched list.
     */
    boolean checkWatchedMovie(MovieInterface movie);
}
