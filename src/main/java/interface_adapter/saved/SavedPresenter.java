package interface_adapter.saved;

import use_case.saved.SavedOutputBoundary;
import use_case.saved.SavedOutputData;

/**
 * The presenter for our saved use case of our application.
 */

public class SavedPresenter implements SavedOutputBoundary {

    private final SavedViewModel savedViewModel;

    public SavedPresenter(SavedViewModel savedViewModel) {
        this.savedViewModel = savedViewModel;
    }

    @Override
    public void prepareSuccessView(SavedOutputData outputData) {
        final SavedState savedState = this.savedViewModel.getState();

        savedState.setSavedTaskError(null);
        savedState.setSavedTaskSuccess(outputData.isSaved());

        this.savedViewModel.setState(savedState);
        this.savedViewModel.firePropertyChanged("saved");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final SavedState savedState = this.savedViewModel.getState();

        savedState.setSavedTaskError(errorMessage);
        savedState.setSavedTaskSuccess(false);

        this.savedViewModel.setState(savedState);
        this.savedViewModel.firePropertyChanged("saved");
    }
}
