package use_case.recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.MovieInterface;

/**
 * The Recommendation Interactor: Class specifying the implementation of the recommendation use case.
 */

public class RecommendationInteractor implements RecommendationInputBoundary {
    private final WatchedIdDataAccessInterface watchedMovieIdDataAccessInterface;
    private final RecommendationDataAccessInterface recommendationDataAccessInterface;
    private final RecommendationOutputBoundary recommendationPresenter;

    public RecommendationInteractor(WatchedIdDataAccessInterface watchedMovieIdDataAccessInterface,
                                    RecommendationDataAccessInterface recommendationDataAccessInterface,
                                    RecommendationOutputBoundary recommendationPresenter) {

        this.watchedMovieIdDataAccessInterface = watchedMovieIdDataAccessInterface;
        this.recommendationDataAccessInterface = recommendationDataAccessInterface;
        this.recommendationPresenter = recommendationPresenter;

    }

    @Override
    public void executeRecommendation() {
        final List<Integer> moviesIdList = this.watchedMovieIdDataAccessInterface.getMovieIds();
        if (moviesIdList.size() == 0) {
            recommendationPresenter.prepareFailView(
                    "Error: Need at least one watched movie to make recommendations"
            );
        }
        else {
            final List<MovieInterface> recommendedMovieList = new ArrayList<>();

            for (int i = 0; i < moviesIdList.size(); i++) {
                recommendedMovieList.addAll(
                        this.recommendationDataAccessInterface.recommendMovies(moviesIdList.get(i))
                );
            }
            if (recommendedMovieList.size() == 0) {
                recommendationPresenter.prepareFailView("Error: No recommendations found");
            }
            else {
                Collections.shuffle(recommendedMovieList);
                final RecommendationOutputData recommendationOutputData = new RecommendationOutputData(
                        recommendedMovieList, true);
                recommendationPresenter.prepareSuccessView(recommendationOutputData);
            }
        }
    }
}
