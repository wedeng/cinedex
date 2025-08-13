package use_case.search;

import entity.MovieInterface;

import java.util.List;

public interface MovieSearchService {
    List<MovieInterface> searchMovies(String query, String genre, Integer year, Double minRating);
}
