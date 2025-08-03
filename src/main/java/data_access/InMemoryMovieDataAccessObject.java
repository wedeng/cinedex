package data_access;

import entity.AppUser;
import entity.Movie;
import use_case.note.DataAccessException;
import use_case.saved.SavedDataAccessInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryMovieDataAccessObject implements SavedDataAccessInterface {
    private final Map<String, List<Movie>> mockMovieDatabase = new HashMap<>();
    private AppUser currentUser;

    public InMemoryMovieDataAccessObject() {
        initializeMockData();
        currentUser = new AppUser(1, "testuser", 
                                 new ArrayList<>(), new ArrayList<>(), new HashMap<>());
    }

    private void initializeMockData() {
        List<Movie> actionMovies = new ArrayList<>();
        actionMovies.add(new Movie(1, "The Matrix", LocalDate.of(1999, 3, 31), "/poster1.jpg",
                                  "A computer hacker learns reality is a simulation", 
                                  136, "Action", "English", 3.99, 9.99));
        actionMovies.add(new Movie(2, "John Wick", LocalDate.of(2014, 10, 24), "/poster2.jpg",
                                  "An ex-hitman seeks vengeance", 
                                  101, "Action", "English", 2.99, 7.99));
        
        mockMovieDatabase.put("action", actionMovies);
        mockMovieDatabase.put("matrix", actionMovies.subList(0, 1));
        mockMovieDatabase.put("john", actionMovies.subList(1, 2));
    }

    @Override
    public List<Movie> searchMovies(String query) throws DataAccessException {
        if (query == null || query.trim().isEmpty()) {
            throw new DataAccessException("Search query cannot be empty");
        }
        
        String lowerQuery = query.toLowerCase();
        return mockMovieDatabase.getOrDefault(lowerQuery, new ArrayList<>());
    }

    @Override
    public void addToSavedMovies(AppUser user, int movieId) throws DataAccessException {
        if (!user.getSavedMovies().contains(movieId)) {
            user.getSavedMovies().add(movieId);
        }
    }

    @Override
    public AppUser getCurrentUser() throws DataAccessException {
        return currentUser;
    }

    @Override
    public void updateUser(AppUser user) throws DataAccessException {
        this.currentUser = user;
    }
}
