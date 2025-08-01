package use_case.saved;

public class SavedInputData {
    private final String searchQuery;

    public SavedInputData(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery() {
        return searchQuery;
    }
}
