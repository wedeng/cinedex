package interface_adapter.search;

public final class SearchArgument {
    private final String identifier;
    private final String displayName;

    public SearchArgument(String identifier, String displayName) {
        this.identifier = identifier;
        this.displayName = displayName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDisplayName() {
        return displayName;
    }
}
