package use_case.sync;

/**
 * Output data for sync operations.
 * Encapsulates the response data from synchronization requests.
 */
public class SyncOutputData {
    private final boolean success;
    private final String message;
    private final int moviesSynced;
    private final String errorDetails;

    /**
     * Constructor for successful sync operations.
     * @param moviesSynced the number of movies synced
     * @param syncType the type of sync operation performed
     */
    public SyncOutputData(int moviesSynced, String syncType) {
        this.success = true;
        this.moviesSynced = moviesSynced;
        this.message = String.format("Successfully synced %d movies (%s)", moviesSynced, syncType);
        this.errorDetails = null;
    }

    /**
     * Constructor for failed sync operations.
     * @param errorDetails the error details
     */
    public SyncOutputData(String errorDetails) {
        this.success = false;
        this.errorDetails = errorDetails;
        this.message = "Sync failed";
        this.moviesSynced = 0;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getMoviesSynced() {
        return moviesSynced;
    }

    public String getErrorDetails() {
        return errorDetails;
    }
} 