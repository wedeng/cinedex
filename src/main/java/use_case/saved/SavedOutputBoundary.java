package use_case.saved;

/**
 * The output boundary for the saved use case.
 */

public interface SavedOutputBoundary {
    /**
     * Prepares the success view for the saved use case.
     * @param savedOutputData the output data.
     */
    void prepareSuccessView(SavedOutputData savedOutputData);

    /**
     * Prepares the fail view for the saved use case.
     * @param errorMessage the explanation for failure.
     */
    void prepareFailView(String errorMessage);
}
