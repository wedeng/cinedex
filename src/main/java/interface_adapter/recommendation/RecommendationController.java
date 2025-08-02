package interface_adapter.recommendation;

import use_case.recommendation.MovieCountService;
import use_case.recommendation.MovieListService;
import use_case.recommendation.RecommendationInputBoundary;
import use_case.recommendation.RecommendationInputData;

public class RecommendationController {
    private final RecommendationInputBoundary recommendationInputBoundary;

    public RecommendationController(RecommendationInputBoundary recommendationInputBoundary) {
        this.recommendationInputBoundary = recommendationInputBoundary;
    }

    /**
     * Executes the recommendation Use Case.
     * @param movieListService interface for list of the users watched movie ids.
     * @param movieCountService interface for the total number of movies the user wants recommended.
     */

    public void execute(MovieListService movieListService, MovieCountService movieCountService) {
        final RecommendationInputData recommendationInputData = new RecommendationInputData(movieListService, movieCountService);
        recommendationInputBoundary.executeRecommendation(recommendationInputData);
    }
}

