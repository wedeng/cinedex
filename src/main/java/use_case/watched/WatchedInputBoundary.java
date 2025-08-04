package use_case.watched;

/**
 * Input boundary for adding or removing movies from the users watched movie list.
 */

public interface WatchedInputBoundary {
    /**
     * Executes the watched use case by adding a movie to watched list.
     * @param watchedInputData the input data.
     */
    void executeAddToWatched(WatchedInputData watchedInputData);

    /**
     * Executes the watched use case by removing a movie from the users watched list.
     * @param watchedInputData the input data.
     */
    void executeRemoveFromWatched(WatchedInputData watchedInputData);
}
