package use_case.saved;

import entity.MovieInterface;

/**
 * Input Data for the saved use case.
 */

public class SavedInputData {
    private final MovieInterface movie;

    public SavedInputData(MovieInterface movie) {
        this.movie = movie;
    }

    public MovieInterface getMovie() {
        return this.movie;
    }
}
