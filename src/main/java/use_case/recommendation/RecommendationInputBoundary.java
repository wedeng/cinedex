package use_case.recommendation;

/**
 * Input Boundary for actions which are related to recommendations.
 */

public interface RecommendationInputBoundary {
    /**
     * Executes the recommendation use case.
     * @param recommendationInputData the input data.
     */
    void executeRecommendation(RecommendationInputData recommendationInputData);
}
