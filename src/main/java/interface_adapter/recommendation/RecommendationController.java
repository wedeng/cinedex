package interface_adapter.recommendation;

import use_case.recommendation.RecommendationInputBoundary;

public class RecommendationController {
    private final RecommendationInputBoundary recommendationInputBoundary;

    public RecommendationController(RecommendationInputBoundary recommendationInputBoundary) {
        this.recommendationInputBoundary = recommendationInputBoundary;
    }

    /**
     * Executes the recommendation use case.
     */

    public void execute() {
        recommendationInputBoundary.executeRecommendation();
    }
}

