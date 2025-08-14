package interface_adapter.search;

import java.util.List;

public enum SearchField {
    TITLE("title", "Title", false, null),

    INCLUDE_ADULT("include_adult", "Include Adult", true,
            List.of(new SearchArgument("true", "true"),
                    new SearchArgument("false", "false"))),

    SORT_BY("sort_by", "Sort by", true, List.of(
            new SearchArgument("title.asc", "Title (Ascending)"),
            new SearchArgument("title.desc", "Title (Descending)"),
            new SearchArgument("popularity.asc", "Popularity (Ascending)"),
            new SearchArgument("popularity.desc", "Popularity (Descending)"),
            new SearchArgument("revenue.asc", "Revenue (Ascending)"),
            new SearchArgument("revenue.desc", "Revenue (Descending)"),
            new SearchArgument("vote_average.asc", "Vote Average (Ascending)"),
            new SearchArgument("vote_average.desc", "Vote Average (Descending)"),
            new SearchArgument("vote_count.asc", "Vote Count (Ascending)"),
            new SearchArgument("vote_count.desc", "Vote Count (Descending)"))),

    GENRE("with_genres", "Genre", true, List.of(
            new SearchArgument("28", "Action"),
            new SearchArgument("12", "Adventure"),
            new SearchArgument("16", "Animation"),
            new SearchArgument("35", "Comedy"),
            new SearchArgument("80", "Crime"),
            new SearchArgument("99", "Documentary"),
            new SearchArgument("18", "Drama"),
            new SearchArgument("10751", "Family"),
            new SearchArgument("14", "Fantasy"),
            new SearchArgument("36", "History"),
            new SearchArgument("27", "Horror"),
            new SearchArgument("10402", "Music"),
            new SearchArgument("9648", "Mystery"),
            new SearchArgument("10749", "Romance"),
            new SearchArgument("878", "Science Fiction"),
            new SearchArgument("10770", "TV Movie"),
            new SearchArgument("53", "Thriller"),
            new SearchArgument("10752", "War"),
            new SearchArgument("37", "Western"))),

    RELEASE_DATE_GTE("release_date.gte", "Release Date (YYYY-MM-DD) >=", false, null),
    RELEASE_DATE_LTE("release_date.lte", "Release Date (YYYY-MM-DD) <=", false, null),
    YEAR("year", "Release Year", false, null);

    private final String identifier;
    private final String displayName;
    private final boolean hasLimitedArguments;
    private final List<SearchArgument> allValidArguments;

    SearchField(String identifier, String displayName, boolean hasLimitedArguments,
                List<SearchArgument> allValidArguments) {
        this.identifier = identifier;
        this.displayName = displayName;
        this.hasLimitedArguments = hasLimitedArguments;
        this.allValidArguments = allValidArguments;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean hasLimitedArguments() {
        return hasLimitedArguments;
    }

    public List<SearchArgument> getAllValidArguments() {
        return allValidArguments;
    }
}
