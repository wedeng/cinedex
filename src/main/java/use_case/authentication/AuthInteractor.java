package use_case.authentication;

import data_access.MongoMovieDataBase;
import entity.AppUser;
import entity.Session;

/**
 * The "Use Case Interactor" for authentication use cases.
 * Implements the business logic for TMDB authentication.
 */
public class AuthInteractor implements AuthInputBoundary {

    private final AuthDataAccessInterface authDataAccessInterface;
    private final MongoMovieDataBase mongoDB;
    private final AuthOutputBoundary authOutputBoundary;

    public AuthInteractor(AuthDataAccessInterface authDataAccessInterface,
                          MongoMovieDataBase mongoDB,
                          AuthOutputBoundary authOutputBoundary) {
        this.authDataAccessInterface = authDataAccessInterface;
        this.mongoDB = mongoDB;
        this.authOutputBoundary = authOutputBoundary;
    }

    /**
     * Executes the create request token use case.
     */
    @Override
    public void executeCreateRequestToken() {
        try {
            String requestToken = authDataAccessInterface.createRequestToken();
            String authUrl = "https://www.themoviedb.org/authenticate/" + requestToken;
            
            AuthOutputData outputData = new AuthOutputData(requestToken, authUrl);
            authOutputBoundary.prepareSuccessView(outputData);
        } catch (AuthException ex) {
            authOutputBoundary.prepareFailView(ex.getMessage());
        }
    }

    /**
     * Executes the authenticate use case.
     * @param requestToken the approved request token from TMDB
     */
    @Override
    public void executeAuthenticate(String requestToken) {
        try {
            // Create session with TMDB
            String sessionId = authDataAccessInterface.createSession(requestToken);
            
            // Get user account details
            int accountId = authDataAccessInterface.getAccountId(sessionId);
            String username = authDataAccessInterface.getUsername(sessionId);
            
            // Create basic user entity (without synced data)
            AppUser user = new AppUser(accountId, username, null, null, null);
            
            // Save user and session to local database
            mongoDB.saveUser(user);
            
            Session session = new Session(); // Create session entity
            mongoDB.saveSession(session);
            
            // Return success with session info
            AuthOutputData outputData = new AuthOutputData(sessionId, user, 0);
            authOutputBoundary.prepareSuccessView(outputData);
            
        } catch (AuthException ex) {
            authOutputBoundary.prepareFailView(ex.getMessage());
        }
    }

    /**
     * Executes the logout use case.
     */
    @Override
    public void executeLogout() {
        try {
            // Get current session
            Session currentSession = mongoDB.getCurrentSession();
            if (currentSession != null) {
                // Delete session from TMDB
                // Note: This would require storing session ID in the Session entity
                // authDataAccessInterface.deleteSession(sessionId);
            }
            
            // Clear local session
            // Note: This would require a method to clear the current session
            
            AuthOutputData outputData = new AuthOutputData();
            authOutputBoundary.prepareSuccessView(outputData);
            
        } catch (Exception ex) {
            authOutputBoundary.prepareFailView(ex.getMessage());
        }
    }
}
