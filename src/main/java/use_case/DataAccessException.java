package use_case;

/**
 * Exception thrown when there are issues with data access operations.
 */
public class DataAccessException extends Exception {
    
    public DataAccessException(String message) {
        super(message);
    }
    
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
} 