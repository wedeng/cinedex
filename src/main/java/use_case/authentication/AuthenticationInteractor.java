package use_case.authentication;

import entity.Session;
import entity.SessionInterface;

public class AuthenticationInteractor implements AuthenticationInputBoundary {
    private final AuthenticationDataAccessInterface authenticationDataAccessInterface;
    private final OperationsDataAccessInterface operationsDataAccessInterface;
    private final CinedexMongoDataBaseInterface movieDataBase;
    private final AuthenticationOutputBoundary outputBoundary;

    public AuthenticationInteractor(AuthenticationDataAccessInterface authenticationDataAccessInterface,
                                    OperationsDataAccessInterface operationsDataAccessInterface,
                                    CinedexMongoDataBaseInterface movieDataBase,
                                    AuthenticationOutputBoundary outputBoundary) {
        this.authenticationDataAccessInterface = authenticationDataAccessInterface;
        this.operationsDataAccessInterface = operationsDataAccessInterface;
        this.movieDataBase = movieDataBase;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void executeAuthentication() {
        try {
            final String requestToken = authenticationDataAccessInterface.makeRequestToken();
            authenticationDataAccessInterface.redirectWeb(requestToken);

            final String sessionId = authenticationDataAccessInterface.makeSession(requestToken);
            final int accountId = operationsDataAccessInterface.getAccountId(sessionId);

            final SessionInterface session = new Session(sessionId, accountId);
            movieDataBase.saveSession(session);

            // TODO: Acquire all data from TMDB and save it to database.

            final AuthenticationOutputData authenticationOutputData = new AuthenticationOutputData(true);
            outputBoundary.prepareSuccessView(authenticationOutputData);
        }
        catch (AuthenticationException exception) {
            outputBoundary.prepareFailView(exception.getMessage());

        }
    }
}
