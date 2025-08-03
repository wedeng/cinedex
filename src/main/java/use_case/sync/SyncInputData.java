package use_case.sync;

/**
 * Input data for sync operations.
 * Encapsulates the data needed for synchronization requests.
 */
public class SyncInputData {
    private final String syncType;

    /**
     * Constructor for sync operations.
     * @param syncType the type of sync operation ("from_tmdb" or "to_tmdb")
     */
    public SyncInputData(String syncType) {
        this.syncType = syncType;
    }

    public String getSyncType() {
        return syncType;
    }
} 