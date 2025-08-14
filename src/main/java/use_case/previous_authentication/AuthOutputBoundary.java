package use_case.previous_authentication;

/**
 * The output boundary for authentication use cases.
 * Handles the presentation of authentication results.
 */
public interface AuthOutputBoundary {

    /**
     * Prepares the success view for authentication operations.
     * @param outputData the output data containing authentication results
     */
    void prepareSuccessView(AuthOutputData outputData);

    /**
     * Prepares the failure view for authentication operations.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
