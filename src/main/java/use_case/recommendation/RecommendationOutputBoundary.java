package use_case.recommendation;

/**
 * Output Boundary for recommendation Use Case.
 */

public interface RecommendationOutputBoundary {
    /**
     * Prepares the success view for the recommendation Use Case.
     * @param recommendationOutputData the output data.
     */
    void prepareSuccessView(RecommendationOutputData recommendationOutputData);

    /**
     * Prepares the fail view for the recommendation Use Case.
     * @param errorMessage the explanation for failure.
     */
    void prepareFailView(String errorMessage);

}
