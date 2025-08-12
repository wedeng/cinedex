package use_case.movie;

import entity.AppUser;
import entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import use_case.note.DataAccessException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieInteractorTest {
    @Mock
    private MovieDataAccessInterface mockMovieDataAccess;
    @Mock
    private MovieOutputBoundary mockMoviePresenter;
    @Mock
    private AppUser mockUser;

    private MovieInteractor movieInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieInteractor = new MovieInteractor(mockMovieDataAccess, mockMoviePresenter);
    }

    @Test
    void executeSearch_SuccessfulSearch_ReturnsMovies() throws DataAccessException {
        MovieSearchInputData inputData = new MovieSearchInputData("Inception");
        List<Movie> expectedMovies = Arrays.asList(
                new Movie(1, "Inception", null, null, null, 0, null, null, 0, 0),
                new Movie(2, "Interstellar",  null, null, null, 0, null, null, 0, 0)
        );

        when(mockMovieDataAccess.searchMovies("Inception")).thenReturn(expectedMovies);

        movieInteractor.executeSearch(inputData);

        verify(mockMovieDataAccess).searchMovies("Inception");
        verify(mockMoviePresenter).prepareSearchSuccessView(argThat(
                output -> output.getMovies().size() == expectedMovies.size()
        ));
    }

    @Test
    void executeSearch_EmptyResults_HandlesGracefully() throws DataAccessException {
        String searchQuery = "Unknown";
        MovieSearchInputData inputData = new MovieSearchInputData(searchQuery);
        when(mockMovieDataAccess.searchMovies(searchQuery)).thenReturn(Collections.emptyList());

        movieInteractor.executeSearch(inputData);

        verify(mockMoviePresenter).prepareSearchSuccessView(argThat(
                output -> output.getMovies().isEmpty()
        ));
    }

    @Test
    void executeSearch_DataAccessException_PrepareFailView() throws DataAccessException {
        String searchQuery = "Error";
        MovieSearchInputData inputData = new MovieSearchInputData(searchQuery);
        when(mockMovieDataAccess.searchMovies(searchQuery)).thenThrow(new DataAccessException("Database error"));

        movieInteractor.executeSearch(inputData);

        verify(mockMoviePresenter).prepareFailView("Search failed: Database error");
    }
}
