package entity.movie_fields;

/**
 * A movie field/parameter.
 * For example, "movie title" might be the name of one instance of this class.
 */
public class MovieField implements MovieFieldInterface {

    private final int id;
    private final String name;
    private final boolean isSearchOption;
    private final String validArgsRegex;
    private final String defaultValue;

    public MovieField(int id, String name, boolean isSearchOption, String validArgsRegex, String defaultValue) {
        this.id = id;
        this.name = name;
        this.isSearchOption = isSearchOption;
        this.validArgsRegex = validArgsRegex;
        this.defaultValue = defaultValue;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isSearchOption() {
        return isSearchOption;
    }

    @Override
    public boolean isValid(String argument) {
        return false;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    public String getValidArgsRegex() {
        return validArgsRegex;
    }
}
