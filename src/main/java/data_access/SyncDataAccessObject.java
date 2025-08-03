package data_access;

import use_case.sync.SyncDataAccessInterface;
import use_case.sync.SyncException;
import use_case.authentication.AuthDataAccessInterface;
import use_case.authentication.AuthException;
import entity.Movie;

import java.util.List;
import java.util.Map;

/**
 * Data access object for sync operations.
 * Wraps AuthDataAccessObject to provide sync-specific interface.
 */
public class SyncDataAccessObject implements SyncDataAccessInterface {

    private final AuthDataAccessInterface authDAO;
    private final MongoMovieDataBase mongoDB;

    public SyncDataAccessObject(AuthDataAccessInterface authDAO, MongoMovieDataBase mongoDB) {
        this.authDAO = authDAO;
        this.mongoDB = mongoDB;
    }

    @Override
    public String getCurrentSessionId() throws SyncException {
        // Get current session from local database
        try {
            entity.Session currentSession = mongoDB.getCurrentSession();
            return currentSession != null ? currentSession.getSessionId() : null;
        } catch (Exception e) {
            throw new SyncException("Error getting current session: " + e.getMessage(), e);
        }
    }

    @Override
    public int getAccountId(String sessionId) throws SyncException {
        try {
            return authDAO.getAccountId(sessionId);
        } catch (AuthException e) {
            throw new SyncException("Error getting account ID: " + e.getMessage(), e);
        }
    }

    @Override
    public String getUsername(String sessionId) throws SyncException {
        try {
            return authDAO.getUsername(sessionId);
        } catch (AuthException e) {
            throw new SyncException("Error getting username: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Integer> getSavedMovies(String sessionId) throws SyncException {
        try {
            return authDAO.getSavedMovies(sessionId);
        } catch (AuthException e) {
            throw new SyncException("Error getting saved movies: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<Integer, Integer> getRatedMovies(String sessionId) throws SyncException {
        try {
            return authDAO.getRatedMovies(sessionId);
        } catch (AuthException e) {
            throw new SyncException("Error getting rated movies: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getPreferredGenres(String sessionId) throws SyncException {
        try {
            return authDAO.getPreferredGenres(sessionId);
        } catch (AuthException e) {
            throw new SyncException("Error getting preferred genres: " + e.getMessage(), e);
        }
    }

    @Override
    public Movie getMovieDetails(int movieId) throws SyncException {
        try {
            return authDAO.getMovieDetails(movieId);
        } catch (AuthException e) {
            throw new SyncException("Error getting movie details: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateSavedMovies(String sessionId, List<Integer> movieIds) throws SyncException {
        try {
            authDAO.updateSavedMovies(sessionId, movieIds);
        } catch (AuthException e) {
            throw new SyncException("Error updating saved movies: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateRatedMovies(String sessionId, Map<Integer, Integer> ratedMovies) throws SyncException {
        try {
            authDAO.updateRatedMovies(sessionId, ratedMovies);
        } catch (AuthException e) {
            throw new SyncException("Error updating rated movies: " + e.getMessage(), e);
        }
    }
} 