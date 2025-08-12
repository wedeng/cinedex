package interface_adapter.watched;

import entity.MovieInterface;
import use_case.watched.WatchedInputBoundary;
import use_case.watched.WatchedInputData;

public class WatchedController {
    private final WatchedInputBoundary watchedInputBoundary;

    public WatchedController(WatchedInputBoundary watchedInputBoundary) {
        this.watchedInputBoundary = watchedInputBoundary;
    }

    /**
     * Executes the Add to watched objective.
     * @param movie the given movie to be added.
     */
    public void executeAddToWatched(MovieInterface movie) {
        final WatchedInputData watchedInputData = new WatchedInputData(movie);
        this.watchedInputBoundary.executeAddToWatched(watchedInputData);
    }

    /**
     * Execute the Remove from watched objective.
     * @param movie the given movie to be removed.
     */
    public void executeRemoveFromWatched(MovieInterface movie) {
        final WatchedInputData watchedInputData = new WatchedInputData(movie);
        this.watchedInputBoundary.executeRemoveFromWatched(watchedInputData);
    }
}
