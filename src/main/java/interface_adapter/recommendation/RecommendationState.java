package interface_adapter.recommendation;

import java.util.List;

import entity.MovieInterface;

/**
 * The state information present for movie recommendations.
 * Possible States:
 * 1. Success with movies (movies >= 1)
 * 2. Success without movies (movies = 0)
 * 3. Failure all together (error in recommendation)
 */

public class RecommendationState {
    private List<MovieInterface> movies;
    private String recommendationError;
    private boolean recommendationSuccess;

    public RecommendationState() {
        this.movies = null;
        this.recommendationError = null;
        this.recommendationSuccess = false;
    }

    public List<MovieInterface> getMovies() {
        return this.movies;
    }

    /**
     * Sets the state with a list of movies.
     * @param movies the list of movies.
     */
    public void setMovies(List<MovieInterface> movies) {
        this.movies = movies;
    }

    public String getRecommendationError() {
        return this.recommendationError;
    }

    /**
     * Sets a string explaining the recommendation error.
     * @param recommendationError the recommendation Error.
     */
    public void setRecommendationError(String recommendationError) {
        this.recommendationError = recommendationError;
        this.recommendationSuccess = false;
    }

    public boolean isRecommendationSuccess() {
        return this.recommendationSuccess;
    }

    /**
     * Sets the status for the recommendation use case.
     * @param recommendationSuccess the recommendation status.
     */
    public void setRecommendationSuccess(boolean recommendationSuccess) {
        this.recommendationSuccess = recommendationSuccess;
        if (this.recommendationSuccess) {
            this.recommendationError = null;
        } 
    }
}

