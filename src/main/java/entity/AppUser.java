package entity;

import java.util.List;
import java.util.Map;

/**
 * The representation of a AppUser for our program.
 */

public class AppUser implements AppUserInterface {
    private final int accountId;
    private final String username;
    private final List<String> preferredGenres;
    private final List<Integer> savedMovies;
    private final List<Integer> watchedMovies;
    private final Map<Integer, Integer> ratedMovies;

    public AppUser(int accountId, String username,
                   List<String> preferredGenres, List<Integer> savedMovies,
                   List<Integer> watchedMovies, Map<Integer, Integer> ratedMovies) {
        this.accountId = accountId;
        this.username = username;
        this.preferredGenres = preferredGenres;
        this.savedMovies = savedMovies;
        this.watchedMovies = watchedMovies;
        this.ratedMovies = ratedMovies;
    }

    @Override
    public int getAccountId() {
        return this.accountId;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public Map<Integer, Integer> getRatedMovies() {
        return this.ratedMovies;
    }

    @Override
    public List<String> getPreferredGenres() {
        return this.preferredGenres;
    }

    @Override
    public List<Integer> getSavedMovies() {
        return this.savedMovies;
    }

    @Override
    public List<Integer> getWatchedMovies() {
        return this.watchedMovies;
    }
}
