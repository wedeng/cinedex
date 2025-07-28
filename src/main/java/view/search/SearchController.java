package view.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

public class SearchController {
    private final SearchInputBoundary searchInteractor;


    public SearchController(SearchInputBoundary searchInteractor) {
        this.searchInteractor = searchInteractor;
    }

    public void executeSearch(String query, String genre, Integer year, Double minRating) {
        SearchInputData inputData = new SearchInputData(query, genre, year, minRating);
        searchInteractor.execute(inputData);
    }
}
