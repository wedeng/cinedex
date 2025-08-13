package view.search;

import entity.Movie;
import entity.MovieInterface;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

import java.util.List;

public class SearchPresenter implements SearchOutputBoundary {

    private final SearchView searchView;

    public SearchPresenter(SearchView searchView) {
        this.searchView = searchView;
    }

    @Override
    public void prepareSuccessView(SearchOutputData searchOutputData) {
        List<MovieInterface> movies = searchOutputData.getMovies();
        searchView.displayResults(movies);

    }

    @Override
    public void prepareFailView(String error) {
        searchView.showError(error);
    }
}
