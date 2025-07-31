package use_case.recommendation;

import java.util.List;

/**
 * Input Data for the recommendation use case.
 */

public class RecommendationInputData {
    private MovieCountService movieCountService;
    private MovieListService movieListService;

    public RecommendationInputData(MovieListService movieListService, MovieCountService movieCountService) {
        this.movieListService = movieListService;
        this.movieCountService = movieCountService;
    }

    public int getMaxRecommendations() {
        return this.movieCountService.getMovieCount();
    }

    public List<Integer> getWatchedMovieIds() {
        return this.movieListService.getMovieIds();
    }
}
