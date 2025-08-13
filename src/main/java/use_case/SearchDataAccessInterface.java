package use_case;

import entity.MovieInterface;

import java.util.List;
import java.util.Map;

public interface SearchDataAccessInterface {

    List<MovieInterface> searchMovies(Map<String, String> searchArguments);

}

