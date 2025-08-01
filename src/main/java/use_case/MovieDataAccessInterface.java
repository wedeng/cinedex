package use_case;

import entity.Movie;

import java.util.List;

public interface MovieDataAccessInterface {

    List<Movie> getSaved(int numOfResults);

    List<Movie> getWatched(int numOfResults);

    List<Movie> getFromTMDB(int numOfResults);
}

