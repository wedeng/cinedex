package use_case.sync;

/**
 * The output boundary for sync use cases.
 * Handles the presentation of synchronization results.
 */
public interface SyncOutputBoundary {

    /**
     * Prepares the success view for sync operations.
     * @param outputData the output data containing sync results
     */
    void prepareSuccessView(SyncOutputData outputData);

    /**
     * Prepares the failure view for sync operations.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
} 