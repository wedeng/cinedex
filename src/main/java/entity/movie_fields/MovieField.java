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

    public MovieField(int id, String name, boolean isSearchOption, String validArgsRegex) {
        this.id = id;
        this.name = name;
        this.isSearchOption = isSearchOption;
        this.validArgsRegex = validArgsRegex;
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

    public String getValidArgsRegex() {
        return validArgsRegex;
    }
}
