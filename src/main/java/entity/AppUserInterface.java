package entity;

import java.util.List;
import java.util.Map;

/**
 * A simple implementation of the AppUser interface.
 */

public interface AppUserInterface {

    /**
     * Returns the accountId of the AppUser.
     * @return the accountId of the AppUser.
     */
    int getAccountId();

    /**
     * Returns the username of the AppUser.
     * @return the username of the AppUser.
     */
    String getUsername();

    /**
     * Returns the ratedMovies of the AppUser.
     * @return the ratedMovies of the AppUser.
     */
    Map<Integer, Integer> getRatedMovies();

    /**
     * Returns the preferredGenres of the AppUser.
     * @return the preferredGenres of the AppUser.
     */
    List<String> getPreferredGenres();

    /**
     * Returns the savedMovies of the AppUser.
     * @return the savedMovies of the AppUser.
     */
    List<Integer> getSavedMovies();

    /**
     * Returns the watchedMovies of the AppUser.
     * @return the watchedMovies of the AppUser.
     */
    List<Integer> getWatchedMovies();

}
