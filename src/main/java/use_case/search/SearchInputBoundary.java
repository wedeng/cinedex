package use_case.search;

public interface SearchInputBoundary {

    /**
     * Executes the search use case.
     * @param searchInputData The input data.
     */
    void execute(SearchInputData searchInputData);

}
