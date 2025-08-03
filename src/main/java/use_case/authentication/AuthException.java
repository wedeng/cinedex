package use_case.authentication;

/**
 * Exception thrown when authentication operations fail.
 */
public class AuthException extends Exception {

    /**
     * Constructor with error message.
     * @param message the error message
     */
    public AuthException(String message) {
        super(message);
    }

    /**
     * Constructor with error message and cause.
     * @param message the error message
     * @param cause the cause of the exception
     */
    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
} 