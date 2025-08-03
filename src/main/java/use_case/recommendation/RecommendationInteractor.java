package use_case.recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.MovieInterface;

/**
 * The Recommendation Interactor.
 */

public class RecommendationInteractor implements RecommendationInputBoundary {
    private final MovieRecommendationService movieRecommendationServiceObject;
    private final RecommendationOutputBoundary recommendationPresenter;

    public RecommendationInteractor(MovieRecommendationService movieRecommendationService,
                                    RecommendationOutputBoundary recommendationPresenter) {

        this.movieRecommendationServiceObject = movieRecommendationService;
        this.recommendationPresenter = recommendationPresenter;
    }

    @Override
    public void executeRecommendation(RecommendationInputData recommendationInputData) {
        final List<Integer> moviesIdList = recommendationInputData.getWatchedMovieIds();

        if (moviesIdList.size() == 0) {
            recommendationPresenter.prepareFailView("Error: Need at least one watched movie to make recommendations");
        }
        else {
            final List<MovieInterface> recommendedMovieList = new ArrayList<>();

            for (int i = 0; i < moviesIdList.size(); i++) {
                recommendedMovieList.addAll(movieRecommendationServiceObject.recommendMovies(moviesIdList.get(i)));
            }
            Collections.shuffle(recommendedMovieList);

            if (recommendedMovieList.size() == 0) {
                recommendationPresenter.prepareFailView("Error: No recommendations found");
            }
            else {
                final RecommendationOutputData recommendationOutputData = new RecommendationOutputData(
                        recommendedMovieList, true);
                recommendationPresenter.prepareSuccessView(recommendationOutputData);
            }
        }
    }
}
