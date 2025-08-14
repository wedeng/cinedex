package use_case.search;

import entity.MovieInterface;

import java.util.List;
import java.util.Map;

public class SearchInteractor implements SearchInputBoundary {

    private final SearchOutputBoundary searchPresenter;
    private final SearchDataAccessInterface movieDataAccessObject;

    public SearchInteractor(SearchOutputBoundary searchPresenter, SearchDataAccessInterface movieDataAccess) {
        this.searchPresenter = searchPresenter;
        this.movieDataAccessObject = movieDataAccess;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        Map<String, String> searchArguments = searchInputData.getSearchArguments();

        // Attempt to get output from DAO
        try {
            List<MovieInterface> outputMovies = movieDataAccessObject.searchMovies(searchArguments);
            final SearchOutputData searchOutputData = new SearchOutputData(outputMovies, false);
            searchPresenter.prepareSuccessView(searchOutputData);
        }
        catch (Exception exception) {
            searchPresenter.prepareFailView(exception.getMessage());
        }

    }
}