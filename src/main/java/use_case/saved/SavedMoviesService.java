package use_case.saved;

import entity.MovieInterface;

/**
 * An interface for the saved movie service that's designed to add and remove saved movies.
 */

public interface SavedMoviesService {
    /**
     * Service that adds the given movie interface object to saved movies.
     * @param movie the given movie to add to saved movies.
     */
    void addSavedMovie(MovieInterface movie);

    /**
     * Service that removes the given movie interface object to saved movies.
     * @param movie the given movie to remove.
     */
    void removeSavedMovie(MovieInterface movie);
}
