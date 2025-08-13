package use_case;

import entity.MovieInterface;

import java.util.List;

public interface MovieDataAccessInterface {

    List<MovieInterface> getSaved(int numOfResults);

    List<MovieInterface> getWatched(int numOfResults);

    List<MovieInterface> getFromTMDB(int numOfResults);
}

