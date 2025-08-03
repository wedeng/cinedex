package use_case.recommendation;

import java.util.List;

/**
 * Input Data for the recommendation use case.
 */

public class RecommendationInputData {
    private MovieListService movieListService;

    public RecommendationInputData(MovieListService movieListService) {
        this.movieListService = movieListService;
    }

    public List<Integer> getWatchedMovieIds() {
        return this.movieListService.getMovieIds();
    }
}
