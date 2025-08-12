package use_case.search;

import entity.Movie;

import java.util.List;

public interface MovieSearchService {
    List<Movie> searchMovies(String query, String genre, Integer year, Double minRating);
}
