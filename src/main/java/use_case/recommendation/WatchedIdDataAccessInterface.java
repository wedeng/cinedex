package use_case.recommendation;

import java.util.List;

public interface WatchedIdDataAccessInterface {
    /**
     * Service that provides a list of watched movies ids for the user.
     * @return a list of watched Movies ids for the user.
     */
    List<Integer> getMovieIds();
}
