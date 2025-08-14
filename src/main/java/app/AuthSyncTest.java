package app;

import data_access.MongoMovieDataBase;
import data_access.AuthDataAccessObject;
import data_access.SyncDataAccessObject;
import use_case.authentication.*;
import use_case.sync.*;

/**
 * Test class for authentication and sync functionality.
 * Demonstrates the complete workflow.
 */
public class AuthSyncTest {

    public static void main(String[] args) {
        System.out.println("=== CineDex Authentication & Sync Test ===\n");

        try {
            // Initialize components
            MongoMovieDataBase mongoDB = new MongoMovieDataBase();
            AuthDataAccessObject authDAO = new AuthDataAccessObject("d14c8b3240616174d718e3f575657829");

            // Create output boundaries for logging
            AuthOutputBoundary authOutput = new AuthOutputBoundary() {
                @Override
                public void prepareSuccessView(AuthOutputData outputData) {
                    System.out.println("Authentication succeeded: " + outputData.getMessage());
                    if (outputData.getRequestToken() != null) {
                        System.out.println("  Request Token: " + outputData.getRequestToken());
                        System.out.println("  Auth URL: " + outputData.getAuthUrl());
                    }
                    if (outputData.getSessionId() != null) {
                        System.out.println("  Session ID: " + outputData.getSessionId());
                    }
                    if (outputData.getUser() != null) {
                        System.out.println("  User: " + outputData.getUser().getUsername() + " (ID: " + outputData.getUser().getAccountId() + ")");
                    }
                }

                @Override
                public void prepareFailView(String errorMessage) {
                    System.err.println("Authentication failed: " + errorMessage);
                }
            };

            SyncOutputBoundary syncOutput = new SyncOutputBoundary() {
                @Override
                public void prepareSuccessView(SyncOutputData outputData) {
                    System.out.println("Sync completed successfully: " + outputData.getMessage());
                    System.out.println("  Movies synced: " + outputData.getMoviesSynced());
                }

                @Override
                public void prepareFailView(String errorMessage) {
                    System.err.println("Sync failed: " + errorMessage);
                }
            };

            // Create sync data access object
            SyncDataAccessObject syncDAO = new SyncDataAccessObject(authDAO, mongoDB);

            // Create interactors
            AuthInteractor authInteractor = new AuthInteractor(authDAO, mongoDB, authOutput);
            SyncInteractor syncInteractor = new SyncInteractor(syncDAO, mongoDB, syncOutput);

            // Test 1: Create Request Token
            System.out.println("1. Creating request token...");
            authInteractor.executeCreateRequestToken();

            // Wait briefly for the request to complete
            Thread.sleep(1000);

            System.out.println("\n2. Testing authentication (requires approval)");
            System.out.println("   - Open the Auth URL displayed above in your browser");
            System.out.println("   - Approve the request token to continue");

            // Uncomment the line below after approving the token:
            // authInteractor.executeAuthenticate("your_approved_request_token_here");

            System.out.println("\n3. Testing sync operations");
            System.out.println("   - Ensure you have an active session from a successful authentication");

            // Uncomment the lines below to run sync tests:
            // syncInteractor.executeSyncFromTMDB();
            // syncInteractor.executeSyncToTMDB();

            System.out.println("\nAll tests finished.");
            System.out.println("To complete the workflow:");
            System.out.println("  1. Approve the request token via the Auth URL");
            System.out.println("  2. Copy the approved token into the code");
            System.out.println("  3. Un-comment the relevant lines and re-run");

        } catch (Exception e) {
            System.err.println("An error occurred during testing: " + e.getMessage());
            e.printStackTrace();
        }
    }
}