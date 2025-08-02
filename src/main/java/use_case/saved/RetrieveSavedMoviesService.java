package use_case.saved;

import java.util.List;

import entity.Movie;

public interface RetrieveSavedMoviesService {
    /**
     * Service that acquires all saved movies to display.
     * @return Returns a list of saved movie objects.
     */
    List<Movie> retrieveSavedMovies();
}
