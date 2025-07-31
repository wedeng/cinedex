package interface_adapter.recommendation;


import use_case.recommendation.RecommendationOutputBoundary;
import use_case.recommendation.RecommendationOutputData;

/**
 * The presenter for our Cinedex Application.
 */

public class RecommendationPresenter implements RecommendationOutputBoundary{

    private final RecommendationViewModel recommendationViewModel;

    public RecommendationPresenter(RecommendationViewModel recommendationViewModel) {
        this.recommendationViewModel = recommendationViewModel;
    }
    /**
     * Prepares the success view for the recommendation Use Case.
     * @param recommendationOutputData the output data
     */
    @Override
    public void prepareSuccessView(RecommendationOutputData recommendationOutputData) {
        final RecommendationState recommendationState = recommendationViewModel.getState();

        recommendationState.setRecommendationSuccess(recommendationOutputData.isSuccessful());
        recommendationState.setMovies(recommendationOutputData.getRecommendedMovies());
        recommendationState.setRecommendationError(null);

        this.recommendationViewModel.setState(recommendationState);
        recommendationViewModel.firePropertyChanged("recommendation");
    }

    /**
     * Prepares the fail view for the recommendation Use Case.
     * @param errorMessage the explanation for failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final RecommendationState recommendationState = recommendationViewModel.getState();
        recommendationState.setRecommendationSuccess(false);
        recommendationState.setMovies(null);
        recommendationState.setRecommendationError(errorMessage);

        this.recommendationViewModel.setState(recommendationState);
        recommendationViewModel.firePropertyChanged("recommendation");
    }
}