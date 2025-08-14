package entity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieFieldRegister implements MovieFieldRegisterInterface {

    static final MovieFieldRegister instance = new MovieFieldRegister();
    private final Set<MovieFieldInterface> movieFields;

    private MovieFieldRegister() {
        movieFields = new LinkedHashSet<>();

        initializeFields();
    }

    private void initializeFields() {
        addField(new MovieField("title", true, ""));
        addField(new MovieField("release year", true, ""));
        addField(new MovieField("poster", false, ""));
        addField(new MovieField("synopsis", false, ""));
        addField(new MovieField("runtime", false, ""));
        addField(new MovieField("language", true, ""));
        addField(new MovieField("director", true, ""));
    }

    public static MovieFieldRegister getInstance() {
        return instance;
    }

    private void addField(MovieFieldInterface field) {
        movieFields.add(field);
    }

    @Override
    public List<MovieFieldInterface> getFields() {
        return new ArrayList<>(movieFields);
    }

    @Override
    public List<String> getFieldNames() {
        return movieFields.stream()
                .map(MovieFieldInterface::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieFieldInterface> getSearchFields() {
        return movieFields.stream()
                .filter(MovieFieldInterface::isSearchOption)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getSearchFieldNames() {
        return movieFields.stream()
                .filter(MovieFieldInterface::isSearchOption)
                .map(MovieFieldInterface::getName)
                .collect(Collectors.toList());
    }

    @Override
    public MovieFieldInterface getField(String fieldName) {
        return null;
    }
}
