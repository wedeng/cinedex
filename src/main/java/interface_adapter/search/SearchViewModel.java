package interface_adapter.search;

import java.util.List;

import interface_adapter.view.ViewModel;

public class SearchViewModel extends ViewModel<SearchState> {
    public SearchViewModel(List<String> searchFields) {
        super("search");
        setState(new SearchState(searchFields));
    }
}
