package use_case.recommendation;

import java.util.List;

import entity.Movie;

public class RecommendationOutputData {

    private List<Movie> recommendedMovies;
    private final boolean isSuccessful;

    public RecommendationOutputData(List<Movie> recommendedMovies, boolean isSuccessful) {
        this.recommendedMovies = recommendedMovies;
        this.isSuccessful = isSuccessful;
    }

    public List<Movie> getRecommendedMovies() {
        return this.recommendedMovies;
    }

    public boolean isSuccessful() {
        return this.isSuccessful;
    }
}
