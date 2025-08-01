package interface_adapter.saved;

import use_case.saved.SavedOutputBoundary;
import use_case.saved.SavedOutputData;

public class SavedPresenter implements SavedOutputBoundary {
    private final SavedViewModel savedViewModel;

    public SavedPresenter(SavedViewModel savedViewModel) {
        this.savedViewModel = savedViewModel;
    }

    @Override
    public void prepareSearchSuccessView(SavedOutputData outputData) {
        savedViewModel.getState().setSearchResults(outputData.getMovies());
        savedViewModel.getState().setMessage("Search completed successfully");
        savedViewModel.getState().setError(null);
        savedViewModel.firePropertyChanged();
    }

    @Override
    public void prepareAddToSavedMoviesSuccessView(String message) {
        savedViewModel.getState().setMessage(message);
        savedViewModel.getState().setError(null);
        savedViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        savedViewModel.getState().setError(errorMessage);
        savedViewModel.firePropertyChanged();
    }
}