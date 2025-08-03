package use_case.search;

import entity.Movie;

import java.util.List;

public class SearchOutputData {
    private final List<Movie> movies;
    private final boolean hasError;

    public SearchOutputData(List<Movie> movies, boolean hasError) {
        this.movies = movies;
        this.hasError = hasError;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public boolean hasError() {
        return hasError;
    }
}
