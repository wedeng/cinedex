package use_case.saved;

import entity.AppUser;
import entity.Movie;
import entity.MovieInterface;
import use_case.DataAccessException;

import java.util.List;

/**
 * Interface for data access operations related to saved movies.
 */
public interface SavedDataAccessInterface {
    
    /**
     * Search for movies based on a query.
     * @param query the search query
     * @return list of movies matching the query
     * @throws DataAccessException if there's an error accessing data
     */
    List<MovieInterface> searchMovies(String query) throws DataAccessException;
    
    /**
     * Add a movie to the user's saved movies list.
     * @param user the user
     * @param movieId the ID of the movie to add
     * @throws DataAccessException if there's an error accessing data
     */
    void addToSavedMovies(AppUser user, int movieId) throws DataAccessException;
    
    /**
     * Get the current user.
     * @return the current user
     * @throws DataAccessException if there's an error accessing data
     */
    AppUser getCurrentUser() throws DataAccessException;
    
    /**
     * Update the user data.
     * @param user the updated user data
     * @throws DataAccessException if there's an error accessing data
     */
    void updateUser(AppUser user) throws DataAccessException;
} 