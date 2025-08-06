package use_case.search;

import java.util.Map;

/**
 * The input data for the Search use case.
 */
public class SearchInputData {

    private final Map<String, String> searchArguments;

    private final String searchType;

    public SearchInputData(Map<String, String> searchArguments, String searchType) {
        this.searchArguments = searchArguments;
        this.searchType = searchType;
    }

    public Map<String, String> getSearchArguments() {
        return searchArguments;
    }

    public String getSearchType() {
        return searchType;
    }
}
