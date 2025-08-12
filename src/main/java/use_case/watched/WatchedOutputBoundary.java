package use_case.watched;

/**
 * The output boundary for the watched use case.
 */

public interface WatchedOutputBoundary {
    /**
     * Prepares the success view for the watched use case.
     * @param watchedOutputData the output data.
     */
    void prepareSuccessView(WatchedOutputData watchedOutputData);

    /**
     * Prepares the fail view for the watched use case.
     * @param errorMessage the explanation for failure.
     */
    void prepareFailView(String errorMessage);
}
