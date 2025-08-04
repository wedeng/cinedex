package use_case.watched;

import entity.MovieInterface;

/**
 * The watched use case interactor specifying the core business logic.
 * Implements the WatchedInputBoundary interface.
 */

public class WatchedInteractor implements WatchedInputBoundary {

    private final WatchedMovieManagerDataAccessInterface watchedMovieManagerDataAccessInterface;
    private final WatchedMovieCheckerDataAccessInterface watchedMovieCheckerDataAccessInterface;
    private final WatchedOutputBoundary watchedMoviePresenter;

    public WatchedInteractor(WatchedMovieManagerDataAccessInterface watchedMovieManagerDataAccessInterface,
                             WatchedMovieCheckerDataAccessInterface watchedMovieCheckerDataAccessInterface,
                             WatchedOutputBoundary watchedPresenter) {
        this.watchedMovieManagerDataAccessInterface = watchedMovieManagerDataAccessInterface;
        this.watchedMovieCheckerDataAccessInterface = watchedMovieCheckerDataAccessInterface;
        this.watchedMoviePresenter = watchedPresenter;
    }

    @Override
    public void executeAddToWatched(WatchedInputData watchedInputData) {

        final MovieInterface inputtedWatchedMovie = watchedInputData.getMovie();

        // Checks if movie is in watched list.
        if (!this.watchedMovieCheckerDataAccessInterface.checkWatchedMovie(inputtedWatchedMovie)) {
            watchedMoviePresenter.prepareFailView("Movie is already watched");
        }
        else {
            this.watchedMovieManagerDataAccessInterface.addWatchedMovie(inputtedWatchedMovie);

            final WatchedOutputData watchedOutputData = new WatchedOutputData(true);
            watchedMoviePresenter.prepareSuccessView(watchedOutputData);
        }
    }

    @Override
    public void executeRemoveFromWatched(WatchedInputData watchedInputData) {

        final MovieInterface inputtedWatchedMovie = watchedInputData.getMovie();

        // Checks if inputted movie is in watched movies
        if (!this.watchedMovieCheckerDataAccessInterface.checkWatchedMovie(inputtedWatchedMovie)) {

            // Removes the inputted movie from watched movies
            this.watchedMovieManagerDataAccessInterface.removeWatchedMovie(inputtedWatchedMovie);

            // Prepares the output data
            final WatchedOutputData watchedOutputData = new WatchedOutputData(true);
            watchedMoviePresenter.prepareSuccessView(watchedOutputData);
        }
        else {
            this.watchedMoviePresenter.prepareFailView("Movie is not in your watched movies");
        }
    }
}
