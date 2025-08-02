package use_case.saved;

import entity.Movie;

public interface CheckSavedMoviesService {
    /**
     * Checks if the given movie is already saved or not.
     * @param movie the given movie to check.
     * @return returns true if the given movie is not in saved movies.
     */
    boolean checkSavedMovies(Movie movie);
}
