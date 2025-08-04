package use_case.watched;

import entity.MovieInterface;

/**
 * Input Data for the watched use case.
 */

public class WatchedInputData {
    private final MovieInterface movie;

    public WatchedInputData(MovieInterface movie) {
        this.movie = movie;
    }

    public MovieInterface getMovie() {
        return this.movie;
    }

}

