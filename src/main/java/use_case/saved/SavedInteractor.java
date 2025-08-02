package use_case.saved;

import entity.Movie;
import use_case.DataAccessException;

/**
 * The saved use case interactor specifying the core business logic.
 * Implements the SavedInputBoundary.
 */

public class SavedInteractor implements SavedInputBoundary {

    private final AddSavedMoviesService addSavedMoviesService;
    private final RemoveSavedMovieService removeSavedMovieService;
    private final CheckSavedMoviesService checkSavedMoviesService;
    private final RetrieveSavedMoviesService retrieveSavedMoviesService;
    private final SavedOutputBoundary savedMoviePresenter;

    public SavedInteractor(AddSavedMoviesService addSavedMoviesService,
                           RemoveSavedMovieService removeSavedMovieService,
                           CheckSavedMoviesService checkSavedMoviesService,
                           RetrieveSavedMoviesService retrieveSavedMoviesService,
                           SavedOutputBoundary savedMoviePresenter) {
        this.addSavedMoviesService = addSavedMoviesService;
        this.removeSavedMovieService = removeSavedMovieService;
        this.checkSavedMoviesService = checkSavedMoviesService;
        this.retrieveSavedMoviesService = retrieveSavedMoviesService;
        this.savedMoviePresenter = savedMoviePresenter;
    }

    @Override
    public void executeAddToSavedMovies(SavedInputData savedInputData) {
        try {
            final Movie inputMovie = savedInputData.getMovie();
            // Checks if movie is already saved.
            if (this.checkSavedMoviesService.checkSavedMovies(inputMovie)) {
                savedMoviePresenter.prepareFailView("Movie is already in your saved movies");
                return;
            }
            // Adds to saved Movies.
            this.addSavedMoviesService.addMovie(inputMovie);

            // Prepares Output list for saved movies.
            final SavedOutputData savedOutputData = new SavedOutputData(
                    this.retrieveSavedMoviesService.retrieveSavedMovies(), true);
            this.savedMoviePresenter.prepareSuccessView(savedOutputData);

        }
        catch (DataAccessException e) {
            this.savedMoviePresenter.prepareFailView("Failed to add movie: " + e.getMessage());
        }
    }

    @Override
    public void executeRemoveFromSavedMovies(SavedInputData savedInputData) {
        // TODO: Complete this method!
    }
}
