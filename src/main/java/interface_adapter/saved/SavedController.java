package interface_adapter.saved;

import entity.Movie;
import use_case.saved.SavedInputBoundary;
import use_case.saved.SavedInputData;

public class SavedController {
    private final SavedInputBoundary savedInputBoundary;

    public SavedController(SavedInputBoundary savedInputBoundary) {
        this.savedInputBoundary = savedInputBoundary;
    }

    /**
     * Executes the Add to saved objective.
     * @param movie the given movie to be saved.
     */
    public void executeAddToSaved(Movie movie) {
        final SavedInputData savedInputData = new SavedInputData(movie);
        savedInputBoundary.executeAddToSavedMovies(savedInputData);
    }

    /**
     * Executes the Remove from saved objective.
     * @param movie the given movie to be removed from saved.
     */
    public void executeRemoveFromSaved(Movie movie) {
        final SavedInputData savedInputData = new SavedInputData(movie);
        savedInputBoundary.executeRemoveFromSavedMovies(savedInputData);
    }
}
