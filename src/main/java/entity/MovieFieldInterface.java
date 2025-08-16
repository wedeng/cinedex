package entity;

public interface MovieFieldInterface {

    public int getId();

    public String getName();

    public boolean isSearchOption();

    public boolean isValid(String movieArgument);

    /**
     * Get the default value (if any) of the movie field.
     * May return an empty String.
     * @return the default value
     */
    public String getDefaultValue();
}
