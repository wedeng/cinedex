package interface_adapter.recommendation;

import interface_adapter.view.ViewModel;

/**
 * The View Model for the recommendation View.
 */

public class RecommendationViewModel extends ViewModel<RecommendationState> {
    public RecommendationViewModel() {
        super("recommendation");
        setState(new RecommendationState());
    }
}
