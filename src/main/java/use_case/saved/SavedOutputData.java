package use_case.saved;

import java.util.List;

import entity.Movie;

public class SavedOutputData {
    private List<Movie> savedMovies;
    private final boolean isSaved;

    public SavedOutputData(List<Movie> savedMovies, boolean isSaved) {
        this.savedMovies = savedMovies;
        this.isSaved = isSaved;
    }

    public List<Movie> getSavedMovies() {
        return this.savedMovies;
    }

    public boolean isSaved() {
        return this.isSaved;
    }
}
