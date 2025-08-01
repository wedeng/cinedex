package use_case.saved;

public interface SavedInputBoundary {
    void executeSearch(SavedInputData inputData);
    void executeAddToSavedMovies(int movieId);
}
