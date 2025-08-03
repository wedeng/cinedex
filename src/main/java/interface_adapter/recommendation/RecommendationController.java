package interface_adapter.recommendation;

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
     * @param movieListService interface for retrieving the users list of watched movie ids.
     */

    public void execute(MovieListService movieListService) {
        final RecommendationInputData recommendationInputData = new RecommendationInputData(movieListService);
        recommendationInputBoundary.executeRecommendation(recommendationInputData);
    }
}

