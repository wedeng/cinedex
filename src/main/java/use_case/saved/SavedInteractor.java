package use_case.saved;

import java.util.List;

import entity.AppUser;
import entity.Movie;
import use_case.note.DataAccessException;

public class SavedInteractor implements SavedInputBoundary {
    private final SavedDataAccessInterface movieDataAccess;
    private final SavedOutputBoundary moviePresenter;

    public SavedInteractor(SavedDataAccessInterface movieDataAccess, 
                          SavedOutputBoundary moviePresenter) {
        this.movieDataAccess = movieDataAccess;
        this.moviePresenter = moviePresenter;
    }

    @Override
    public void executeSearch(SavedInputData inputData) {
        try {
            List<Movie> movies = movieDataAccess.searchMovies(inputData.getSearchQuery());
            SavedOutputData outputData = new SavedOutputData(movies);
            moviePresenter.prepareSearchSuccessView(outputData);
        } catch (DataAccessException e) {
            moviePresenter.prepareFailView("Search failed: " + e.getMessage());
        }
    }

    @Override
    public void executeAddToSavedMovies(int movieId) {
        try {
            AppUser currentUser = movieDataAccess.getCurrentUser();
            
            // Check if movie is already in saved movies
            if (currentUser.getSavedMovies().contains(movieId)) {
                moviePresenter.prepareFailView("Movie is already in your saved movies");
                return;
            }
            
            movieDataAccess.addToSavedMovies(currentUser, movieId);
            moviePresenter.prepareAddToSavedMoviesSuccessView("Movie added to saved movies!");
        } catch (DataAccessException e) {
            moviePresenter.prepareFailView("Failed to add movie: " + e.getMessage());
        }
    }
}
