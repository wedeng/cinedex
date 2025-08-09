package use_case.saved;

import entity.AppUser;
import entity.Movie;
import org.junit.Test;
import use_case.note.DataAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SavedInteractorTest {

    @Test
    public void testSearchMoviesSuccess() {
        // Mock data access
        SavedDataAccessInterface mockDAO = new SavedDataAccessInterface() {
            @Override
            public List<Movie> searchMovies(String query) throws DataAccessException {
                List<Movie> movies = new ArrayList<>();
                movies.add(new Movie(1, "Test Movie", LocalDate.of(2023, 1, 1), 
                                   "/poster.jpg", "Test synopsis", 120, "Action", 
                                   "English", 3.99, 9.99));
                return movies;
            }

            @Override
            public void addToSavedMovies(AppUser user, int movieId) throws DataAccessException {}

            @Override
            public AppUser getCurrentUser() throws DataAccessException {
                return new AppUser(1, "testuser", new ArrayList<>(), new ArrayList<>(), new java.util.HashMap<>());
            }

            @Override
            public void updateUser(AppUser user) throws DataAccessException {}
        };

        // Mock presenter
        SavedOutputBoundary mockPresenter = new SavedOutputBoundary() {
            @Override
            public void prepareSearchSuccessView(SavedOutputData outputData) {
                assertEquals(1, outputData.getMovies().size());
                assertEquals("Test Movie", outputData.getMovies().get(0).getTitle());
            }

            @Override
            public void prepareAddToSavedMoviesSuccessView(String message) {}

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not fail");
            }
        };

        SavedInteractor interactor = new SavedInteractor(mockDAO, mockPresenter);
        SavedInputData inputData = new SavedInputData("test");
        
        interactor.executeSearch(inputData);
    }
}