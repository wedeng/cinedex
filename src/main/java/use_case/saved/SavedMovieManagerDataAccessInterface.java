package use_case.saved;

import entity.MovieInterface;

/**
 * An interface for the saved movie service that's designed to add and remove saved movies.
 */

public interface SavedMovieManagerDataAccessInterface {
    /**
     * Service that adds the given movie to saved movies.
     * @param movie the given movie to add to saved movies.
     */
    void addSavedMovie(MovieInterface movie);

    /**
     * Service that removes the given movie from saved movies.
     * @param movie the given movie to remove.
     */
    void removeSavedMovie(MovieInterface movie);
}
