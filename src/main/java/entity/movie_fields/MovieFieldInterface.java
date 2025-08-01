package entity.movie_fields;

public interface MovieFieldInterface {

    public String getName();

    public boolean isSearchOption();

    public boolean isValid(String movieArgument);
}
