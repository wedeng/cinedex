package interface_adapter.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Different types of views used in this program.
 */
public enum CardType {
    DISCOVER("Discover", true),
    SAVED("Saved", true),
    WATCHED("Watched", true),
    FILTER("Filter", false),
    RECOMMENDED("Recommended", false);

    private final String name;
    private final boolean isSearchable;
    private static final Map<String, CardType> lookup = new HashMap<>();

    static {
        for (CardType card : CardType.values()) {
            lookup.put(card.getName(), card);
        }
    }

    CardType(String name, boolean isSearchable) {
        this.name = name;
        this.isSearchable = isSearchable;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns true if the card type represents a valid type of movies to search from.
     * @return If the card type is valid
     */
    public boolean isValidSearchType() {return isSearchable;}

    public static CardType getCardType(String name) {
        return lookup.get(name);
    }
}
