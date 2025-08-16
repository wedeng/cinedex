package use_case.search;

import java.util.List;
import java.util.Map;

import entity.MovieInterface;

public interface SearchDataAccessInterface {

    /**
     * Service that allows us to search for movies based on a search argument.
     * @param searchArguments the search argument.
     * @return A list of movies.
     */
    List<MovieInterface> searchMovies(Map<String, String> searchArguments);

}
