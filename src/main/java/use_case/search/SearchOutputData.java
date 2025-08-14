package use_case.search;

import entity.MovieInterface;

import java.util.List;

public class SearchOutputData {
    private final List<MovieInterface> movies;
    private final boolean hasError;

    public SearchOutputData(List<MovieInterface> movies, boolean hasError) {
        this.movies = movies;
        this.hasError = hasError;
    }

    public List<MovieInterface> getMovies() {
        return movies;
    }

    /**
     * Returns true if there is an error.
     * @return if output has error.
     */
    public boolean hasError() {
        return hasError;
    }

}
