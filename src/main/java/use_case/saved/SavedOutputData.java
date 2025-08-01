package use_case.saved;

import java.util.List;

import entity.Movie;

public class SavedOutputData {
    private final List<Movie> movies;

    public SavedOutputData(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
