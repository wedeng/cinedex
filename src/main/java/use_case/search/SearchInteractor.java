package use_case.search;

import entity.movie_fields.MovieFieldInterface;
import entity.movie_fields.MovieFieldRegisterInterface;

import java.util.Map;

public class SearchInteractor implements SearchInputBoundary {

    private final MovieFieldRegisterInterface movieFieldRegister;
    private final SearchOutputBoundary searchPresenter;
    private final MovieDataAccessInterface movieDataAccessObject;

    public SearchInteractor(MovieFieldRegisterInterface movieFieldRegister, SearchOutputBoundary searchPresenter, MovieDataAccessInterface movieDataAccess) {
        this.movieFieldRegister = movieFieldRegister;
        this.searchPresenter = searchPresenter;
        this.movieDataAccessObject = movieDataAccess;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        Map<String, String> searchArguments = searchInputData.getSearchArguments();

        for (MovieFieldInterface movieField : movieFieldRegister.getSearchFields()) {
            String argument = searchArguments.get(movieField.getName());
            if (argument == null) {
                searchPresenter.prepareFailView("Missing argument for " + movieField.getName());
            }
            else if (!movieField.isValid(argument)) {
                searchPresenter.prepareFailView("Invalid argument for " + movieField.getName());
            }
            else {
                // TODO: Call on movieDataAccessObject accordingly
//                SearchOutputData outputData = new SearchOutputData(movies, false);
//                searchOutputBoundary.prepareSuccessView(outputData);
//
//                searchOutputBoundary.prepareFailView("Error occurred while searching for movies: " + e.getMessage());
            }
        }
    }
}
