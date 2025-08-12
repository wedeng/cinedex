package use_case;

import entity.MovieInterface;
import entity.movie_fields.MovieFieldInterface;

import java.util.List;
import java.util.Map;

public interface MovieDataAccessInterface {

    List<MovieInterface> getSaved(int numOfResults, Map<MovieFieldInterface, String> searchArguments);

    List<MovieInterface> getWatched(int numOfResults, Map<MovieFieldInterface, String> searchArguments);

    List<MovieInterface> getFromTMDB(int numOfResults, Map<MovieFieldInterface, String> searchArguments);

    List<MovieInterface> getSaved(int numOfResults);

    List<MovieInterface> getWatched(int numOfResults);

    List<MovieInterface> getFromTMDB(int numOfResults);
}

