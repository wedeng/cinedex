package use_case.recommendation;

/**
 * Exception thrown when recommendation operations fail.
 */

public class RecommendationException extends Exception {
    /**
     * Constructor with error message.
     * @param message the error message
     */
    public RecommendationException(String message) {
        super(message);
    }

    /**
     * Constructor with error message and cause.
     * @param message the error message
     * @param cause the cause of the exception
     */
    public RecommendationException(String message, Throwable cause) {
        super(message, cause);
    }
}
