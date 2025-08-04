package use_case.saved;

import entity.MovieInterface;

/**
 * An interface for the saved movie service that's designed to access critical data.
 */

public interface SavedMovieCheckerDataAccessInterface {
    /**
     * Service that checks if the given movie is already saved or not.
     * @param movie the given movie to check.
     * @return true if the given movie is not in saved movies.
     */
    boolean checkSavedMovies(MovieInterface movie);

}
