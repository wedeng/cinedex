package use_case.authentication;

/**
 * The output boundary for authentication use cases.
 * Handles the presentation of authentication results.
 */

public interface AuthenticationOutputBoundary {
    /**
     * Prepares the success view for authentication operations.
     * @param outputData the output data containing authentication results
     */
    void prepareSuccessView(AuthenticationOutputData outputData);

    /**
     * Prepares the failure view for authentication operations.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

}
