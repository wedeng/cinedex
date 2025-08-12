package use_case.movie;

import entity.Movie;
import entity.AppUser;
import use_case.DataAccessException;
import java.util.List;

public class MovieInteractor implements MovieInputBoundary {
    private final MovieDataAccessInterface movieDataAccess;
    private final MovieOutputBoundary moviePresenter;

    public MovieInteractor(MovieDataAccessInterface movieDataAccess, 
                          MovieOutputBoundary moviePresenter) {
        this.movieDataAccess = movieDataAccess;
        this.moviePresenter = moviePresenter;
    }

    @Override
    public void executeSearch(MovieSearchInputData inputData) {
        try {
            List<Movie> movies = movieDataAccess.searchMovies(inputData.getSearchQuery());
            MovieSearchOutputData outputData = new MovieSearchOutputData(movies);
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
