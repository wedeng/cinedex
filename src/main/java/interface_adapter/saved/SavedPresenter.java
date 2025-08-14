package interface_adapter.saved;

import interface_adapter.view.MovieDisplayState;
import interface_adapter.view.MovieDisplayViewModel;
import use_case.saved.SavedOutputBoundary;
import use_case.saved.SavedOutputData;

/**
 * The presenter for our saved use case of our application.
 */

public class SavedPresenter implements SavedOutputBoundary {

    private final MovieDisplayViewModel savedViewModel;

    public SavedPresenter(MovieDisplayViewModel savedViewModel) {
        this.savedViewModel = savedViewModel;
    }

    @Override
    public void prepareSuccessView(SavedOutputData outputData) {
        final MovieDisplayState savedState = this.savedViewModel.getState();

        savedState.setRetrievalError(null);

        this.savedViewModel.setState(savedState);
        this.savedViewModel.firePropertyChanged("state");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final MovieDisplayState savedState = this.savedViewModel.getState();

        savedState.setRetrievalError(errorMessage);

        this.savedViewModel.setState(savedState);
        this.savedViewModel.firePropertyChanged("state");
    }
}
