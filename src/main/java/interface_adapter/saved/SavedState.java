package interface_adapter.saved;

import entity.Movie;
import java.util.ArrayList;
import java.util.List;

public class SavedState {
    private String searchQuery = "";
    private List<Movie> searchResults = new ArrayList<>();
    private List<Movie> savedMovies = new ArrayList<>();
    private String message = "";
    private String error = null;

    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }

    public List<Movie> getSearchResults() { return searchResults; }
    public void setSearchResults(List<Movie> searchResults) { this.searchResults = searchResults; }

    public List<Movie> getSavedMovies() { return savedMovies; }
    public void setSavedMovies(List<Movie> savedMovies) { this.savedMovies = savedMovies; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}