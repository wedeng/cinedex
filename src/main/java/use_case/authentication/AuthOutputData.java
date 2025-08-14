package use_case.authentication;

import entity.AppUser;
import java.util.List;

/**
 * Output data for authentication operations.
 * Encapsulates the response data from authentication requests.
 */
public class AuthOutputData {
    private final boolean success;
    private final String message;
    private final String requestToken;
    private final String sessionId;
    private final String authUrl;
    private final AppUser user;
    private final int moviesSynced;
    private final String errorDetails;

    /**
     * Constructor for successful request token creation.
     * @param requestToken the request token
     * @param authUrl the URL for user to authorize the token
     */
    public AuthOutputData(String requestToken, String authUrl) {
        this.success = true;
        this.requestToken = requestToken;
        this.authUrl = authUrl;
        this.sessionId = null;
        this.user = null;
        this.moviesSynced = 0;
        this.message = "Request token created successfully";
        this.errorDetails = null;
    }

    /**
     * Constructor for successful authentication and sync.
     * @param sessionId the TMDB session ID
     * @param user the authenticated user
     * @param moviesSynced the number of movies synced
     */
    public AuthOutputData(String sessionId, AppUser user, int moviesSynced) {
        this.success = true;
        this.sessionId = sessionId;
        this.user = user;
        this.moviesSynced = moviesSynced;
        this.requestToken = null;
        this.authUrl = null;
        this.message = String.format("Authentication successful. Synced %d movies.", moviesSynced);
        this.errorDetails = null;
    }

    /**
     * Constructor for successful sync operation.
     * @param moviesSynced the number of movies synced
     */
    public AuthOutputData(int moviesSynced) {
        this.success = true;
        this.moviesSynced = moviesSynced;
        this.sessionId = null;
        this.user = null;
        this.requestToken = null;
        this.authUrl = null;
        this.message = String.format("Successfully synced %d movies.", moviesSynced);
        this.errorDetails = null;
    }

    /**
     * Constructor for successful logout.
     */
    public AuthOutputData() {
        this.success = true;
        this.moviesSynced = 0;
        this.sessionId = null;
        this.user = null;
        this.requestToken = null;
        this.authUrl = null;
        this.message = "Successfully logged out and updated TMDB.";
        this.errorDetails = null;
    }

    /**
     * Constructor for failed operations.
     * @param errorDetails the error details
     */
    public AuthOutputData(String errorDetails) {
        this.success = false;
        this.errorDetails = errorDetails;
        this.message = "Authentication failed";
        this.requestToken = null;
        this.sessionId = null;
        this.authUrl = null;
        this.user = null;
        this.moviesSynced = 0;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public AppUser getUser() {
        return user;
    }

    public int getMoviesSynced() {
        return moviesSynced;
    }

    public String getErrorDetails() {
        return errorDetails;
    }
}
