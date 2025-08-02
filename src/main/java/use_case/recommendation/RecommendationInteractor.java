package use_case.recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.Movie;

/**
 * The Recommendation Interactor.
 */

public class RecommendationInteractor implements RecommendationInputBoundary {
    private final MovieRecommendationService movieRecommendationServiceObject;
    private final RecommendationOutputBoundary recommendationPresenter;

    public RecommendationInteractor(MovieRecommendationService movieRecommendationService,
                                    RecommendationOutputBoundary recommendationOutputBoundary) {

        this.movieRecommendationServiceObject = movieRecommendationService;
        this.recommendationPresenter = recommendationOutputBoundary;
    }

    @Override
    public void executeRecommendation(RecommendationInputData recommendationInputData) {
        final List<Integer> moviesIdList = recommendationInputData.getWatchedMovieIds();
        final int maxRecommendations = recommendationInputData.getMaxRecommendations();
        
        if (moviesIdList.size() == 0) {
            recommendationPresenter.prepareFailView("Error: Need at least one watched movie to make recommendations");
        }
        else {
            final List<Movie> recommendedMovieList = new ArrayList<>();
            for (int i = 0; i < moviesIdList.size(); i++) {
                recommendedMovieList.addAll(movieRecommendationServiceObject.recommendMovies(moviesIdList.get(i)));
            }
            Collections.shuffle(recommendedMovieList);

            final List<Movie> completedRecommendedMovieList = new ArrayList<>();
            for (int j = 0; j < Math.min(maxRecommendations, recommendedMovieList.size()); j++) {
                completedRecommendedMovieList.add(recommendedMovieList.get(j));
            }
            if (completedRecommendedMovieList.size() == 0) {
                recommendationPresenter.prepareFailView("Error: No recommendations found");
            }
            else {
                final RecommendationOutputData recommendationOutputData = new RecommendationOutputData(
                        completedRecommendedMovieList, true);
                recommendationPresenter.prepareSuccessView(recommendationOutputData);
            }
        }
    }
}
