package interface_adapter.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

import java.util.Map;

/**
 * The controller for the search use case.
 */
public class SearchController {

    private final SearchInputBoundary searchInteractor;

    public SearchController(SearchInputBoundary searchInteractor) {
        this.searchInteractor = searchInteractor;
    }

    /**
     * Executes the Search use case.
     */
    public void execute(String query, Map<String, String> arguments) {

        final SearchInputData searchInputData = new SearchInputData(arguments);

        searchInteractor.execute(searchInputData);
    }
}
