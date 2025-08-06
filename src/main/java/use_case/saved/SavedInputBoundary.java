package use_case.saved;

/**
 * Input boundary for adding or removing movies from the users saved movie list.
 */

public interface SavedInputBoundary {
    /**
     * Executes the saved used case by adding a movie to saved movies.
     * @param savedInputData the input data.
     */
    void executeAddToSavedMovies(SavedInputData savedInputData);

    /**
     * Execute the saved use case by removing a movie from saved movies.
     * @param savedInputData the input data.
     */
    void executeRemoveFromSavedMovies(SavedInputData savedInputData);
}
