package interface_adapter.search;

import java.util.HashMap;
import java.util.Map;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

/**
 * The controller for the search use case.
 */
public class SearchController {

    private final SearchInputBoundary searchInteractor;

    public SearchController(SearchInputBoundary searchInteractor) {
        this.searchInteractor = searchInteractor;
    }

    /**
     * Executes the search use case.
     * @param query the query.
     * @param arguments the arguments.
     * @param searchType the search Type.
     */
    public void execute(String query, Map<String, String> arguments, String searchType) {
        final HashMap<String, String> argumentsCopy = new HashMap<String, String>(arguments);
        argumentsCopy.put("title", query);
        final SearchInputData searchInputData = new SearchInputData(argumentsCopy);

        searchInteractor.execute(searchInputData);
    }
}
