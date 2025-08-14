package use_case.authentication;

/**
 * Input data for authentication operations.
 * Encapsulates the data needed for authentication requests.
 */
public class AuthInputData {
    private final String requestToken;
    private final String sessionId;
    private final String operationType;

    /**
     * Constructor for authentication with request token.
     * @param requestToken the approved request token from TMDB
     */
    public AuthInputData(String requestToken) {
        this.requestToken = requestToken;
        this.sessionId = null;
        this.operationType = "authenticate";
    }

    /**
     * Constructor for session-based operations.
     * @param sessionId the TMDB session ID
     * @param operationType the type of operation
     */
    public AuthInputData(String sessionId, String operationType) {
        this.requestToken = null;
        this.sessionId = sessionId;
        this.operationType = operationType;
    }

    /**
     * Constructor for request token creation.
     */
    public AuthInputData() {
        this.requestToken = null;
        this.sessionId = null;
        this.operationType = "create_token";
    }

    public String getRequestToken() {
        return requestToken;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getOperationType() {
        return operationType;
    }
}