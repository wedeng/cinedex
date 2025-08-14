package interface_adapter.recommendation;

import interface_adapter.view.MovieDisplayState;
import interface_adapter.view.MovieDisplayViewModel;
import use_case.recommendation.RecommendationOutputBoundary;
import use_case.recommendation.RecommendationOutputData;

/**
 * The presenter for our recommendation use case of our application.
 */

public class RecommendationPresenter implements RecommendationOutputBoundary {

    private final MovieDisplayViewModel recommendationViewModel;

    public RecommendationPresenter(MovieDisplayViewModel recommendationViewModel) {
        this.recommendationViewModel = recommendationViewModel;
    }

    /**
     * Prepares the success view for the recommendation use case.
     * @param recommendationOutputData the output data.
     */
    @Override
    public void prepareSuccessView(RecommendationOutputData recommendationOutputData) {
        final MovieDisplayState recommendationState = recommendationViewModel.getState();

        recommendationState.setDisplayedMovies(recommendationOutputData.getRecommendedMovies());
        recommendationState.setRetrievalError(null);

        this.recommendationViewModel.setState(recommendationState);
        this.recommendationViewModel.firePropertyChanged("recommendation");
    }

    /**
     * Prepares the fail view for the recommendation use case.
     * @param errorMessage the explanation for failure.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final MovieDisplayState recommendationState = recommendationViewModel.getState();

        recommendationState.setDisplayedMovies(null);
        recommendationState.setRetrievalError(errorMessage);

        this.recommendationViewModel.setState(recommendationState);
        recommendationViewModel.firePropertyChanged("recommendation");
    }
}
