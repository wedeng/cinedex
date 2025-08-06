package entity.movie_fields;

import java.util.List;

/**
 * A register of movie fields.
 */
public interface MovieFieldRegisterInterface {

    List<MovieFieldInterface> getFields();

    List<MovieFieldInterface> getSearchFields();

    MovieFieldInterface getField(String fieldName);


}
