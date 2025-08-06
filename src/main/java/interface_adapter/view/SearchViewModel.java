package interface_adapter.view;

import java.util.List;

public class SearchViewModel extends ViewModel<SearchState> {
    public SearchViewModel(List<String> searchFields) {
        super("search");
        setState(new SearchState(searchFields));
    }
}
