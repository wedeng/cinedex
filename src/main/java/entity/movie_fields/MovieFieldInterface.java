package entity.movie_fields;

public interface MovieFieldInterface {

    public int getId();

    public String getName();

    public boolean isSearchOption();

    public boolean isValid(String movieArgument);
}
