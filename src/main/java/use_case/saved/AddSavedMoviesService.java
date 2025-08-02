package use_case.saved;

import entity.Movie;

/**
 * An interface for the saved movie service we provide.
 * Designed to add to saved movies.
 */

public interface AddSavedMoviesService {
    /**
     * Service that adds the given movie object to saved movies.
     * @param movie the given movie to add to saved movies.
     */
    void addMovie(Movie movie);
}
