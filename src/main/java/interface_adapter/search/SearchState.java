package interface_adapter.search;

import interface_adapter.view.CardType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchState {
    private CardType cardSearchType;
    private final Map<String, String> searchFields;

    public SearchState(List<String> searchFields) {
        this.searchFields = new HashMap<>();
        for (String searchField : searchFields) {
            this.searchFields.put(searchField, "");
        }
    }

    public void setSearchArgument(String field, String argument) throws IllegalArgumentException {
        if (this.searchFields.containsKey(field)) {
            searchFields.put(field, argument);
        }
        else {
            throw new IllegalArgumentException(field + " is not a valid search argument");
        }
    }

    public Map<String, String> getSearchFields() {
        return searchFields;
    }

    public void setCardSearchType(CardType searchableView) throws IllegalArgumentException {
        if (searchableView.isValidSearchType()) {
            this.cardSearchType = cardSearchType;
        }
    }
    public CardType getCardSearchType() {
        return cardSearchType;
    }
}
