package view;

import javax.swing.ImageIcon;

public enum AppIcon {
    DISCOVER_32("src/main/resources/discover32.png"),
    DISCOVER_64("src/main/resources/discover64.png"),
    SAVED_32("src/main/resources/saved32.png"),
    SAVED_64("src/main/resources/saved64.png"),
    SAVED_REMOVE_32("src/main/resources/savedremove32.png"),
    SAVED_REMOVE_64("src/main/resources/savedremove64.png"),
    WATCHED_32("src/main/resources/watched32.png"),
    WATCHED_64("src/main/resources/watched64.png"),
    WATCHED_REMOVE_32("src/main/resources/watchedremove32.png"),
    RECOMMENDED_32("src/main/resources/recommended32.png"),
    RECOMMENDED_64("src/main/resources/recommended64.png"),
    SEARCH_32("src/main/resources/search32.png"),
    FILTER_32("src/main/resources/filter32.png");

    private final String filePath;

    AppIcon(String filePath) {
        this.filePath = filePath;
    }

    public ImageIcon getIcon() {
        return new ImageIcon(filePath);
    }
}
