package app;

import data_access.AuthenticationDataAccessObject;
import data_access.CinedexDataAccessObject;
import data_access.CinedexMongoDataBase;
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

        String key = "d14c8b3240616174d718e3f575657829";

        // DAOs
        AuthenticationDataAccessInterface authenticationDataAccessObject = new AuthenticationDataAccessObject(key);
        CinedexDataAccessObject cinedexDataAccessObject = new CinedexDataAccessObject(key);
        CinedexMongoDataBaseInterface cinedexMongoDataBaseObject = new CinedexMongoDataBase();

        final AppBuilder builder = new AppBuilder(
                authenticationDataAccessObject,
                cinedexMongoDataBaseObject,
                cinedexDataAccessObject,
                cinedexDataAccessObject,
                cinedexDataAccessObject,
                cinedexDataAccessObject
                );

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