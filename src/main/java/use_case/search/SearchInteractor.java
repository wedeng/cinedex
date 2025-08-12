package use_case.search;

import entity.MovieInterface;
import entity.movie_fields.MovieFieldInterface;
import entity.movie_fields.MovieFieldRegisterInterface;
import use_case.MovieDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Map<String, String> stringSearchArguments = searchInputData.getSearchArguments();

        for (MovieFieldInterface movieField : movieFieldRegister.getSearchFields()) {
            String argument = stringSearchArguments.get(movieField.getName());
            if (argument == null) {
                searchPresenter.prepareFailView("Missing argument for " + movieField.getName());
            }
            else if (!movieField.isValid(argument)) {
                searchPresenter.prepareFailView("Invalid argument for " + movieField.getName());
            }
            else {
                // Convert to required type
                Map<MovieFieldInterface, String> searchArguments = new HashMap<>();
                for (var entry : stringSearchArguments.entrySet()) {
                    searchArguments.put(movieFieldRegister.getField(entry.getKey()), entry.getValue());
                }

                // Get output from DAO
                List<MovieInterface> outputMovies = new ArrayList<>();
                String searchType = searchInputData.getSearchType();
                switch (searchType) {
                    case "Discover": outputMovies = movieDataAccessObject.getFromTMDB(50, searchArguments);
                    case "Saved": outputMovies = movieDataAccessObject.getSaved(50,  searchArguments);
                    case "Watched": outputMovies = movieDataAccessObject.getWatched(50, searchArguments);
                }
                final SearchOutputData searchOutputData = new SearchOutputData(outputMovies, searchType, false);
                searchPresenter.prepareSuccessView(searchOutputData);
            }
        }
    }
}
