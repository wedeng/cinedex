package use_case.saved;

public interface SavedOutputBoundary {
    void prepareSearchSuccessView(SavedOutputData outputData);
    void prepareAddToSavedMoviesSuccessView(String message);
    void prepareFailView(String errorMessage);
}
