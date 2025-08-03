package use_case.saved;

import entity.MovieInterface;

/**
 * The saved use case interactor specifying the core business logic.
 * Implements the SavedInputBoundary.
 */

public class SavedInteractor implements SavedInputBoundary {

    private final SavedMoviesService savedMoviesService;
    private final SavedMovieDataAccessInterface savedMovieDataAccessInterface;
    private final SavedOutputBoundary savedMoviePresenter;

    public SavedInteractor(SavedMoviesService savedMoviesService,
                           SavedMovieDataAccessInterface savedMovieDataAccessInterface,
                           SavedOutputBoundary savedMoviePresenter) {
        this.savedMoviesService = savedMoviesService;
        this.savedMovieDataAccessInterface = savedMovieDataAccessInterface;
        this.savedMoviePresenter = savedMoviePresenter;
    }

    @Override
    public void executeAddToSavedMovies(SavedInputData savedInputData) {

        final MovieInterface inputtedSavedMovie = savedInputData.getMovie();

        // Checks if inputted movie is in the saved movies.
        if (!this.savedMovieDataAccessInterface.checkSavedMovies(inputtedSavedMovie)) {
            savedMoviePresenter.prepareFailView("Movie is already in your saved movies");
        }
        else {
            // Adds inputted saved movie.
            this.savedMoviesService.addSavedMovie(inputtedSavedMovie);

            // Prepares the output data.
            final SavedOutputData savedOutputData = new SavedOutputData(true);
            this.savedMoviePresenter.prepareSuccessView(savedOutputData);
        }
    }

    @Override
    public void executeRemoveFromSavedMovies(SavedInputData savedInputData) {

        final MovieInterface inputtedSavedMovie = savedInputData.getMovie();

        // Checks if inputted movie is in the saved movies.
        if (!this.savedMovieDataAccessInterface.checkSavedMovies(inputtedSavedMovie)) {

            // Removes inputted saved movie.
            this.savedMoviesService.removeSavedMovie(inputtedSavedMovie);

            // Prepares the output data
            final SavedOutputData savedOutputData = new SavedOutputData(true);
            this.savedMoviePresenter.prepareSuccessView(savedOutputData);
        }
        else {
            savedMoviePresenter.prepareFailView("Movie is not in your saved movies");
        }
    }
}
