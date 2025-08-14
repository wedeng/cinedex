package entity;

import java.util.List;

public interface MovieFieldInterface {

    public String getName();

    public boolean isSearchOption();

    public boolean isValid(String movieArgument);

    /**
     *
     * @return
     */
    public List<String> getValidArguments();

}
