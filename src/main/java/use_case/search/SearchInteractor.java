package use_case.search;

import entity.Movie;
import java.util.List;

public class SearchInteractor implements SearchInputBoundary {

    private final MovieSearchService movieSearchService;
    private final SearchOutputBoundary searchOutputBoundary;

    public SearchInteractor(MovieSearchService movieSearchService, SearchOutputBoundary searchOutputBoundary) {
        this.movieSearchService = movieSearchService;
        this.searchOutputBoundary = searchOutputBoundary;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        try {
            List<Movie> movies = movieSearchService.searchMovies(
                    searchInputData.getQuery(),
                    searchInputData.getGenre(),
                    searchInputData.getYear(),
                    searchInputData.getMinRating()
            );

            SearchOutputData outputData = new SearchOutputData(movies, false);
            searchOutputBoundary.prepareSuccessView(outputData);

        } catch (Exception e) {
            searchOutputBoundary.prepareFailView("Error occurred while searching for movies: " + e.getMessage());

        }
    }
}
