package use_case.search;

import entity.MovieInterface;
import entity.MovieFieldInterface;
import entity.MovieFieldRegisterInterface;

import java.util.List;
import java.util.Map;

public class SearchInteractor implements SearchInputBoundary {

    private final MovieFieldRegisterInterface movieFieldRegister;
    private final SearchOutputBoundary searchPresenter;
    private final SearchDataAccessInterface movieDataAccessObject;

    public SearchInteractor(MovieFieldRegisterInterface movieFieldRegister, SearchOutputBoundary searchPresenter, SearchDataAccessInterface movieDataAccess) {
        this.movieFieldRegister = movieFieldRegister;
        this.searchPresenter = searchPresenter;
        this.movieDataAccessObject = movieDataAccess;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        Map<String, String> searchArguments = searchInputData.getSearchArguments();

        for (MovieFieldInterface movieField : movieFieldRegister.getSearchFields()) {

            String argument = searchArguments.get(movieField.getName());

            if (!movieField.isValid(argument)) {
                searchPresenter.prepareFailView("Invalid argument for " + movieField.getName());
            }
            else {
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
    }
}
