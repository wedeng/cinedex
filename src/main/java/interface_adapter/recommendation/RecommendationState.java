package interface_adapter.recommendation;

import entity.Movie;
import java.util.List;

/**
 * The state information present for movie recommendations.
 * 
 * Program states:
 * 1. Success with movies
 * 2. Success without movies
 * 3. Failure all together
 */

public class RecommendationState {
    private List<Movie> movies;
    private String recommendationError;
    private boolean recommendationSuccess;

    public RecommendationState(List<Movie> movies) {
        this.movies = movies;
        this.recommendationError = null;
        this.recommendationSuccess = true;
    }

    public RecommendationState() {
        this.movies = null;
        this.recommendationError = null;
        this.recommendationSuccess = false;
    }

    public List<Movie> getMovies() {
        return this.movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getRecommendationError() {
        return this.recommendationError;
    }

    public void setRecommendationError(String recommendationError) {
        this.recommendationError = recommendationError;
        this.recommendationSuccess = false;
    }

    public boolean isRecommendationSuccess() {
        return this.recommendationSuccess;
    }

    public void setRecommendationSuccess(boolean recommendationSuccess) {
        this.recommendationSuccess = recommendationSuccess;
        if (this.recommendationSuccess) {
            this.recommendationError = null;
        } 
    }
}

