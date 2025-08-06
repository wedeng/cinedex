package use_case.search;

import entity.MovieInterface;

import java.util.List;

public class SearchOutputData {
    private final List<MovieInterface> movies;
    private final String resultType;
    private final boolean hasError;

    public SearchOutputData(List<MovieInterface> movies, String resultType, boolean hasError) {
        this.movies = movies;
        this.resultType = resultType;
        this.hasError = hasError;
    }

    public List<MovieInterface> getMovies() {
        return movies;
    }

    public boolean hasError() {
        return hasError;
    }

    public String getResultType() {
        return resultType;
    }
}
