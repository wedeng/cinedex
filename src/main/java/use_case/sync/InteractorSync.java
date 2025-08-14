package use_case.sync;

import java.util.List;

import data_access.MongoMovieDataBase;
import data_access.CinedexMongoDataBase;
import entity.AppUser;
import entity.MovieInterface;
import entity.User;

/**
 * The "Use Case Interactor" for sync use cases.
 * Implements the business logic for data synchronization.
 */
public class InteractorSync implements SyncInputBoundary {
    private final DataAccessInterfaceSync dataAccessInterfaceSync;
    private final CinedexMongoDataBase mongoDB;
    private final SyncOutputBoundary syncOutputBoundary;

    public InteractorSync(DataAccessInterfaceSync dataAccessInterfaceSync,
                          CinedexMongoDataBase mongoDB,
                          SyncOutputBoundary syncOutputBoundary) {
        this.dataAccessInterfaceSync = dataAccessInterfaceSync;
        this.mongoDB = mongoDB;
        this.syncOutputBoundary = syncOutputBoundary;
    }

    @Override
    public void executeSyncFromTMDB() {
        try {
            // Get current session
            final String sessionId = dataAccessInterfaceSync.getCurrentSessionId();
            if (sessionId == null) {
                syncOutputBoundary.prepareFailView("No active session found");
            }

            // Fetch user data from TMDB
            final List<Integer> savedMovies = dataAccessInterfaceSync.getSavedMovies(sessionId);
            final List<Integer> watchedMovies = dataAccessInterfaceSync.getWatchedMovies(sessionId);

            // Sync movies to local database
            final int moviesSynced = syncMoviesToLocal(savedMovies);

            // Update local user data
            final int accountId = dataAccessInterfaceSync.getAccountId(sessionId);
            final String username = dataAccessInterfaceSync.getUsername(sessionId);

            final User user = new User(accountId, username);

            // TODO: No update user.
            mongoDB.saveUser(user);

            final SyncOutputData outputData = new SyncOutputData(moviesSynced, "from TMDB");
            syncOutputBoundary.prepareSuccessView(outputData);

        }
        catch (SyncException ex) {
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
            final String sessionId = dataAccessInterfaceSync.getCurrentSessionId();
            if (sessionId == null) {
                syncOutputBoundary.prepareFailView("No active session found");
            }

            // Get current user from local database
            final int accountId = dataAccessInterfaceSync.getAccountId(sessionId);

            AppUser localUser = mongoDB.getUser(accountId);

            if (localUser != null) {
                // Update TMDB with local changes
                dataAccessInterfaceSync.updateSavedMovies(sessionId, localUser.getSavedMovies());
                dataAccessInterfaceSync.updateWatchedMovies(sessionId, localUser.getWatchedMovies());
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
                MovieInterface existingMovie = mongoDB.getMovie(movieId);
                if (existingMovie == null) {
                    // Fetch movie details from TMDB
                    MovieInterface movie = syncDataAccessInterface.getMovieDetails(movieId);
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
