package use_case.search;

import entity.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class MovieSearchServiceTest {
    @Mock
    private MovieSearchService movieSearchService;

    private final Movie testMovie = new Movie(
            1,
            "Inception",
            LocalDate.of(2010, 7, 16),
            "poster_path.jpg",
            "A synopsis for this movie",
            148,
            "Sci-Fi",
            "English",
            8.8,
            8.9
    );

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchMovies_WithValidParameters_ReturnsMovies() {
        when(movieSearchService.searchMovies("Inception", "Sci-Fi", 2010, 8.0)).thenReturn(List.of(testMovie));

        List<Movie> result = movieSearchService.searchMovies("Inception", "Sci-Fi", 2010, 8.0);

        Assertions.assertNotNull(result);
    }

    @Test
    void searchMovies_WithNullQuery_ReturnsEmptyList() {
        when(movieSearchService.searchMovies(null, null, null, null)).thenReturn(List.of());
        List<Movie> result = movieSearchService.searchMovies(null, null, null, null);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void searchMovies_WithPartialQuery_ReturnsMovies() {
        when(movieSearchService.searchMovies("Inception", null, null, null)).thenReturn(List.of());
        List<Movie> results = movieSearchService.searchMovies("Inception", null, null, null);
        Assertions.assertEquals(1, results.size());
    }

    @Test
    void searchMovies_WithNonMatchingParameters_ReturnsEmptyList() {
        when(movieSearchService.searchMovies("Nonexistent", "Comedy", 1990, 9.9)).thenReturn(List.of());
        List<Movie> result = movieSearchService.searchMovies("Nonexistent", "Comedy", 1990, 9.9);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void searchMovies_WithEmptyQuery_HandlesGracefully() {
        when(movieSearchService.searchMovies("", null, null, null)).thenReturn(List.of());
        List<Movie> results = movieSearchService.searchMovies("", null, null, null);
        Assertions.assertTrue(results.isEmpty());
    }
}
