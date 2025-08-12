package entity.movie_fields;

import java.util.List;

/**
 * A register of movie fields.
 */
public interface MovieFieldRegisterInterface {

    List<MovieFieldInterface> getFields();

    List<String> getFieldNames();

    List<MovieFieldInterface> getSearchFields();

    List<String> getSearchFieldNames();

    MovieFieldInterface getField(String fieldName);


}
