package use_case.recommendation;

import java.util.List;

import entity.MovieInterface;

public class RecommendationOutputData {

    private List<MovieInterface> recommendedMovies;
    private final boolean isSuccessful;

    public RecommendationOutputData(List<MovieInterface> recommendedMovies, boolean isSuccessful) {
        this.recommendedMovies = recommendedMovies;
        this.isSuccessful = isSuccessful;
    }

    public List<MovieInterface> getRecommendedMovies() {
        return this.recommendedMovies;
    }

    public boolean isSuccessful() {
        return this.isSuccessful;
    }
}
