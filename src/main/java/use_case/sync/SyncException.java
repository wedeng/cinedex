package use_case.sync;

/**
 * Exception thrown when sync operations fail.
 */
public class SyncException extends Exception {

    /**
     * Constructor with error message.
     * @param message the error message
     */
    public SyncException(String message) {
        super(message);
    }

    /**
     * Constructor with error message and cause.
     * @param message the error message
     * @param cause the cause of the exception
     */
    public SyncException(String message, Throwable cause) {
        super(message, cause);
    }
} 