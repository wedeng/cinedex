package use_case.saved;

import java.util.List;

import entity.AppUser;
import entity.Movie;
import use_case.note.DataAccessException;

public interface SavedDataAccessInterface {
    List<Movie> searchMovies(String query) throws DataAccessException;
    void addToSavedMovies(AppUser user, int movieId) throws DataAccessException;
    AppUser getCurrentUser() throws DataAccessException;
    void updateUser(AppUser user) throws DataAccessException;
}