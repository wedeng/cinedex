package app;

import data_access.AuthenticationDataAccessObject;
import use_case.authentication.AuthenticationDataAccessInterface;
import use_case.authentication.CinedexMongoDataBaseInterface;
import use_case.authentication.OperationsDataAccessInterface;
import use_case.recommendation.RecommendationDataAccessInterface;
import use_case.recommendation.WatchedIdDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

import javax.swing.JFrame;

/**
 The main entry point of the application; it launches the program (e.g., starts the Swing UI)
 and invokes the AppBuilder to set up the app.
 */

public class MainApplication {

    /**
     * The main entry point of the program.
     * @param args main arguments
     */
    public static void main(String[] args) {

        // TODO: Initialize DAOs here (and inject into builder)
        // AuthenticationDataAccessInterface authenticationDataAccessObject = new AuthenticationDataAccessObject();
        // OperationsDataAccessInterface operationsDataAccessObject;
        // CinedexMongoDataBaseInterface cinedexMongoDataBaseObject;
        // SearchDataAccessInterface searchDataAccessObject;
        // WatchedIdDataAccessInterface watchedIdDataAccessObject;
        // RecommendationDataAccessInterface recommendationDataAccessObject;

        final AppBuilder builder = new AppBuilder();
        final JFrame appFrame = builder
                .addViewManager()
                .addAppView()
                .addAuthenticationView()
                .addAuthenticationUseCase()
                .addSearchUseCase()
                .build();
        appFrame.pack();
        appFrame.setVisible(true);

    }
}