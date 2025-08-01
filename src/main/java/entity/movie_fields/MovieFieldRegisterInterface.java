package entity.movie_fields;

import java.util.List;

/**
 * A register of movie fields.
 */
public interface MovieFieldRegisterInterface {

    List<MovieField> getFields();

    List<MovieField> getSearchFields();
}
