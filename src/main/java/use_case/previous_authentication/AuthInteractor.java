package use_case.previous_authentication;

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
            AppUser user = new AppUser(accountId, username, null, null, null, null);
            
            // Save user and session to local database
            mongoDB.saveUser(user);
            
            Session session = new Session(sessionId, accountId); // Create session with proper data
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
     * Syncs local data to TMDB with priority, then clears local database.
     */
    @Override
    public void executeLogout() {
        try {
            // Get current session
            Session currentSession = mongoDB.getCurrentSession();
            if (currentSession != null && currentSession.getSessionId() != null) {
                // 1. SYNC TO TMDB (with priority - overwrite TMDB data)
                syncLocalDataToTMDB(currentSession.getSessionId());
                
                // 2. Delete session from TMDB
                authDataAccessInterface.deleteSession(currentSession.getSessionId());
            }
            
            // 3. CLEAR LOCAL DATABASE (for next user)
            mongoDB.clearAllData();
            
            AuthOutputData outputData = new AuthOutputData();
            authOutputBoundary.prepareSuccessView(outputData);
            
        } catch (Exception ex) {
            authOutputBoundary.prepareFailView(ex.getMessage());
        }
    }
    
    /**
     * Syncs all local user data to TMDB with priority (overwrites TMDB data).
     * @param sessionId the current session ID
     */
    private void syncLocalDataToTMDB(String sessionId) {
        try {
            // Get current user from local database
            Session currentSession = mongoDB.getCurrentSession();
            if (currentSession != null) {
                int accountId = currentSession.getAccountId();
                AppUser localUser = mongoDB.getUser(accountId);
                
                if (localUser != null) {
                    // Update TMDB with local changes (overwrites TMDB data)
                    authDataAccessInterface.updateSavedMovies(sessionId, localUser.getSavedMovies());
                    authDataAccessInterface.updateWatchedMovies(sessionId, localUser.getWatchedMovies());
                    authDataAccessInterface.updateRatedMovies(sessionId, localUser.getRatedMovies());
                }
            }
        } catch (Exception e) {
            // Log error but don't fail logout
            System.err.println("Warning: Failed to sync data to TMDB during logout: " + e.getMessage());
        }
    }
}
