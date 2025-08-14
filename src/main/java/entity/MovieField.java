package entity;

import java.util.List;

/**
 * A movie field/parameter.
 * For example, "movie title" might be the name of one instance of this class.
 */
public class MovieField implements MovieFieldInterface {

    private final String name;
    private final boolean isSearchOption;
    private final String validArgsRegex;

    public MovieField(String name, boolean isSearchOption, String validArgsRegex) {
        this.name = name;
        this.isSearchOption = isSearchOption;
        this.validArgsRegex = validArgsRegex;
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
        return true;
    }

    @Override
    public List<String> getValidArguments() {
        return List.of();
    }

    public String getValidArgsRegex() {
        return validArgsRegex;
    }
}
