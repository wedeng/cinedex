package use_case.search;

import java.util.Map;

/**
 * The input data for the Search use case.
 */
public class SearchInputData {

    private final Map<String, String> searchArguments;

    public SearchInputData(Map<String, String> searchArguments) {
        this.searchArguments = searchArguments;
    }

    public Map<String, String> getSearchArguments() {
        return searchArguments;
    }

}
