package use_case.saved;

import entity.MovieInterface;

/**
 * The saved use case interactor specifying the core business logic.
 * Implements the SavedInputBoundary interface.
 */

public class SavedInteractor implements SavedInputBoundary {

    private final SavedMovieManagerDataAccessInterface savedMovieManagerDataAccessInterface;
    private final SavedMovieCheckerDataAccessInterface savedMovieCheckerDataAccessInterface;
    private final SavedOutputBoundary savedMoviePresenter;

    public SavedInteractor(SavedMovieManagerDataAccessInterface savedMovieManagerDataAccessInterface,
                           SavedMovieCheckerDataAccessInterface savedMovieCheckerDataAccessInterface,
                           SavedOutputBoundary savedMoviePresenter) {
        this.savedMovieManagerDataAccessInterface = savedMovieManagerDataAccessInterface;
        this.savedMovieCheckerDataAccessInterface = savedMovieCheckerDataAccessInterface;
        this.savedMoviePresenter = savedMoviePresenter;
    }

    @Override
    public void executeAddToSavedMovies(SavedInputData savedInputData) {

        final MovieInterface inputtedSavedMovie = savedInputData.getMovie();

        // Checks if inputted movie is in the saved movies.
        if (!this.savedMovieCheckerDataAccessInterface.checkSavedMovies(inputtedSavedMovie)) {
            savedMoviePresenter.prepareFailView("Movie is already in your saved movies");
        }
        else {
            // Adds inputted saved movie.
            this.savedMovieManagerDataAccessInterface.addSavedMovie(inputtedSavedMovie);

            // Prepares the output data.
            final SavedOutputData savedOutputData = new SavedOutputData(true);
            this.savedMoviePresenter.prepareSuccessView(savedOutputData);
        }
    }

    @Override
    public void executeRemoveFromSavedMovies(SavedInputData savedInputData) {

        final MovieInterface inputtedSavedMovie = savedInputData.getMovie();

        // Checks if inputted movie is in the saved movies.
        if (!this.savedMovieCheckerDataAccessInterface.checkSavedMovies(inputtedSavedMovie)) {

            // Removes inputted saved movie.
            this.savedMovieManagerDataAccessInterface.removeSavedMovie(inputtedSavedMovie);

            // Prepares the output data
            final SavedOutputData savedOutputData = new SavedOutputData(true);
            this.savedMoviePresenter.prepareSuccessView(savedOutputData);
        }
        else {
            savedMoviePresenter.prepareFailView("Movie is not in your saved movies");
        }
    }
}
