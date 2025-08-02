package use_case.saved;

import entity.Movie;

/**
 * Input Data for the saved use case.
 */

public class SavedInputData {
    private final Movie movie;

    public SavedInputData(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }
}
