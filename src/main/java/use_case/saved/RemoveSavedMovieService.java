package use_case.saved;

import entity.Movie;

/**
 * An interface for the saved movie service we provide.
 * Designed to remove movie from the saved movies.
 */

public interface RemoveSavedMovieService {
    /**
     * Service that removes the given movie object to saved movies.
     * @param movie the given movie to remove.
     */
    void removeSavedMovie(Movie movie);
}
