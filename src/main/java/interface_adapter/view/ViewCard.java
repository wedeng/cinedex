package interface_adapter.view;

public enum ViewCard {
    DISCOVER("Discover"),
    SAVED("Saved"),
    WATCHED("Watched"),
    SETTINGS("Settings");

    private final String name;

    ViewCard(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
