package entity.movie_fields;

import entity.Movie;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MovieFieldRegister implements MovieFieldRegisterInterface {

    static final MovieFieldRegister instance = new MovieFieldRegister();
    private final Set<MovieFieldInterface> movieFields;

    private MovieFieldRegister() {
        movieFields = new LinkedHashSet<>();

        initializeFields();
    }

    private void initializeFields() {
        addField(new MovieField(0, "title", true, ""));
        addField(new MovieField(1, "release date", true, ""));
        addField(new MovieField(2, "poster", true, ""));
        addField(new MovieField(3, "synopsis", false, ""));
        addField(new MovieField(4, "runtime", true, ""));
        addField(new MovieField(5, "genre", true, ""));
        addField(new MovieField(6, "language", true, ""));
        addField(new MovieField(7, "director", true, ""));

    }

    public static MovieFieldRegister getInstance() {
        return instance;
    }

    private void addField(MovieFieldInterface field) {
        movieFields.add(field);
    }

    @Override
    public List<MovieFieldInterface> getFields() {
        return List.of();
    }

    @Override
    public List<MovieFieldInterface> getSearchFields() {
        return List.of();
    }

    @Override
    public MovieFieldInterface getField(String fieldName) {
        return null;
    }
}
