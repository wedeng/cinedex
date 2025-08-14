package use_case.search;

import java.util.List;

import entity.MovieInterface;

public interface MovieSearchService {

    /**
     * Search Service according to some search argument.
     * @param query search by query.
     * @param genre search by genre.
     * @param year search by year.
     * @param minRating search by minRating.
     * @return A list of movie objects
     */
    List<MovieInterface> searchMovies(String query, String genre, Integer year, Double minRating);
}
