package use_case.recommendation;

import java.util.List;

public interface MovieListService {
    /**
     * Returns a list of watched Movies ids for account User.
     * @return a list of watched Movies ids for account User.
     */
    List<Integer> getMovieIds();
}
