package use_case.search;

import entity.MovieInterface;

import java.util.List;
import java.util.Map;

public interface SearchDataAccessInterface {

    List<MovieInterface> searchMovies(Map<String, String> searchArguments);

}

