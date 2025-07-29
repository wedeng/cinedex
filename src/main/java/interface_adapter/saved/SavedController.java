package interface_adapter.saved;

import use_case.saved.SavedInputBoundary;
import use_case.saved.SavedInputData;

public class SavedController {
    private final SavedInputBoundary savedInteractor;

    public SavedController(SavedInputBoundary savedInteractor) {
        this.savedInteractor = savedInteractor;
    }

    public void searchMovies(String query) {
        SavedInputData inputData = new SavedInputData(query);
        savedInteractor.executeSearch(inputData);
    }

    public void addToSavedMovies(int movieId) {
        savedInteractor.executeAddToSavedMovies(movieId);
    }
}