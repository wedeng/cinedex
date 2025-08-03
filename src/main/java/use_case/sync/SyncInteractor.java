package use_case.sync;

import data_access.MongoMovieDataBase;
import entity.AppUser;
import entity.Movie;

import java.util.List;
import java.util.Map;

/**
 * The "Use Case Interactor" for sync use cases.
 * Implements the business logic for data synchronization.
 */
public class SyncInteractor implements SyncInputBoundary {

    private final SyncDataAccessInterface syncDataAccessInterface;
    private final MongoMovieDataBase mongoDB;
    private final SyncOutputBoundary syncOutputBoundary;

    public SyncInteractor(SyncDataAccessInterface syncDataAccessInterface,
                          MongoMovieDataBase mongoDB,
                          SyncOutputBoundary syncOutputBoundary) {
        this.syncDataAccessInterface = syncDataAccessInterface;
        this.mongoDB = mongoDB;
        this.syncOutputBoundary = syncOutputBoundary;
    }

    /**
     * Executes the sync from TMDB use case.
     */
    @Override
    public void executeSyncFromTMDB() {
        try {
            // Get current session
            String sessionId = syncDataAccessInterface.getCurrentSessionId();
            if (sessionId == null) {
                syncOutputBoundary.prepareFailView("No active session found");
                return;
            }

            // Fetch user data from TMDB
            List<Integer> savedMovies = syncDataAccessInterface.getSavedMovies(sessionId);
            Map<Integer, Integer> ratedMovies = syncDataAccessInterface.getRatedMovies(sessionId);
            List<String> preferredGenres = syncDataAccessInterface.getPreferredGenres(sessionId);
            
            // Sync movies to local database
            int moviesSynced = syncMoviesToLocal(savedMovies);
            
            // Update local user data
            int accountId = syncDataAccessInterface.getAccountId(sessionId);
            String username = syncDataAccessInterface.getUsername(sessionId);
            
            AppUser user = new AppUser(accountId, username, preferredGenres, savedMovies, ratedMovies);
            mongoDB.updateUser(user);
            
            SyncOutputData outputData = new SyncOutputData(moviesSynced, "from TMDB");
            syncOutputBoundary.prepareSuccessView(outputData);
            
        } catch (SyncException ex) {
            syncOutputBoundary.prepareFailView(ex.getMessage());
        }
    }

    /**
     * Executes the sync to TMDB use case.
     */
    @Override
    public void executeSyncToTMDB() {
        try {
            // Get current session
            String sessionId = syncDataAccessInterface.getCurrentSessionId();
            if (sessionId == null) {
                syncOutputBoundary.prepareFailView("No active session found");
                return;
            }

            // Get current user from local database
            int accountId = syncDataAccessInterface.getAccountId(sessionId);
            AppUser localUser = mongoDB.getUser(accountId);
            
            if (localUser != null) {
                // Update TMDB with local changes
                syncDataAccessInterface.updateSavedMovies(sessionId, localUser.getSavedMovies());
                syncDataAccessInterface.updateRatedMovies(sessionId, localUser.getRatedMovies());
            }
            
            SyncOutputData outputData = new SyncOutputData(0, "to TMDB");
            syncOutputBoundary.prepareSuccessView(outputData);
            
        } catch (SyncException ex) {
            syncOutputBoundary.prepareFailView(ex.getMessage());
        }
    }

    /**
     * Helper method to sync movies from TMDB to local database.
     * @param movieIds list of movie IDs to sync
     * @return number of movies successfully synced
     */
    private int syncMoviesToLocal(List<Integer> movieIds) {
        int syncedCount = 0;
        
        for (Integer movieId : movieIds) {
            try {
                // Check if movie already exists in local database
                Movie existingMovie = mongoDB.getMovie(movieId);
                if (existingMovie == null) {
                    // Fetch movie details from TMDB
                    Movie movie = syncDataAccessInterface.getMovieDetails(movieId);
                    if (movie != null) {
                        // Save to local database
                        if (mongoDB.saveMovie(movie)) {
                            syncedCount++;
                        }
                    }
                }
            } catch (SyncException e) {
                // Log error but continue with other movies
                System.err.println("Failed to sync movie " + movieId + ": " + e.getMessage());
            }
        }
        
        return syncedCount;
    }
} 