package use_case.recommendation;

import java.util.List;

import entity.MovieInterface;

/**
 * The interface for the recommendation service we provide.
 */

public interface RecommendationDataAccessInterface {
    /**
     * Service that provides a list of recommended movies given a movie.
     * @param movieId the id of a given movie.
     * @return A list of recommended movies.
     */
    List<MovieInterface> recommendMovies(int movieId);
}
