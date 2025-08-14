package use_case.search;

import java.util.List;
import java.util.Map;

import entity.MovieInterface;
import entity.MovieFieldInterface;
import entity.MovieFieldRegisterInterface;

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
        final Map<String, String> searchArguments = searchInputData.getSearchArguments();

        for (MovieFieldInterface movieField : movieFieldRegister.getSearchFields()) {

            final String argument = searchArguments.get(movieField.getName());

            if (!movieField.isValid(argument)) {
                searchPresenter.prepareFailView("Invalid argument for " + movieField.getName());
            }
            else {

                // Get output from DAO
                final List<MovieInterface> outputMovies = movieDataAccessObject.searchMovies(searchArguments);

                final SearchOutputData searchOutputData = new SearchOutputData(outputMovies, false);
                searchPresenter.prepareSuccessView(searchOutputData);
            }
        }
    }
}
