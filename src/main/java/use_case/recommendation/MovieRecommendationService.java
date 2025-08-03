package use_case.recommendation;

import java.util.List;

import entity.MovieInterface;

/**
 * The interface for the recommendation service we provide.
 */

public interface MovieRecommendationService {
    /**
     * List of recommended movies given a users watched movie.
     * @param movieId the id of a given movie.
     * @return A list of recommended movies.
     */
    List<MovieInterface> recommendMovies(int movieId);
}
