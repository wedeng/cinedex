package use_case;

import entity.Movie;
import entity.movie_fields.MovieField;

import java.util.List;
import java.util.Map;

public interface MovieDataAccessInterface {

    List<Movie> getSaved(int numOfResults, Map<MovieField, String> searchArguments);

    List<Movie> getWatched(int numOfResults,  Map<MovieField, String> searchArguments);

    List<Movie> getFromTMDB(int numOfResults, Map<MovieField, String> searchArguments);

    List<Movie> getSaved(int numOfResults);

    List<Movie> getWatched(int numOfResults);

    List<Movie> getFromTMDB(int numOfResults);
}

