package entity.movie_fields;

/**
 * A movie field/parameter.
 * For example, "movie title" might be the name of one instance of this class.
 */
public class MovieField implements MovieFieldInterface {

    private final String name;
    private final boolean isSearchOption;

    public MovieField(String name, boolean isSearchOption) {
        this.name = name;
        this.isSearchOption = isSearchOption;
    }

    @Override
    public String getName() {
        return name;
    }

//    @Override
//    public String getDescription() {
//        return description;
//    }

    @Override
    public boolean isSearchOption() {
        return isSearchOption;
    }

    @Override
    public boolean isValid(String argument) {
        return false;
    }

}
