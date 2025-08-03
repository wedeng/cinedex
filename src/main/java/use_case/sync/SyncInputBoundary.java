package use_case.sync;

/**
 * The Input Boundary for sync use cases.
 * Handles data synchronization between TMDB and local database.
 */
public interface SyncInputBoundary {

    /**
     * Syncs user data from TMDB to local database.
     */
    void executeSyncFromTMDB();

    /**
     * Syncs local changes back to TMDB.
     */
    void executeSyncToTMDB();
} 