package interface_adapter.search;

import interface_adapter.view.ViewModel;

import java.util.List;

public class SearchViewModel extends ViewModel<SearchState> {
    public SearchViewModel(List<String> searchFields) {
        super("search");
        setState(new SearchState(searchFields));
    }
}
