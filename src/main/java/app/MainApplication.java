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

import io.github.cdimascio.dotenv.Dotenv;


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

        Dotenv dotenv = Dotenv.load();

        String accessToken = dotenv.get("API_READ_ACCESS_TOKEN");
        String apiKey = dotenv.get("API_KEY");

        // DAOs
        AuthenticationDataAccessInterface authenticationDataAccessObject = new AuthenticationDataAccessObject(apiKey);
        CinedexDataAccessObject cinedexDataAccessObject = new CinedexDataAccessObject(apiKey);
        CinedexMongoDataBaseInterface cinedexMongoDataBaseObject = new CinedexMongoDataBase();

        final AppBuilder builder = new AppBuilder(
                authenticationDataAccessObject,
                cinedexMongoDataBaseObject,
                cinedexDataAccessObject,
                cinedexDataAccessObject,
                cinedexDataAccessObject,
                cinedexDataAccessObject,
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
                .addSavedUseCase()
                .addWatchedUseCase()
                .addRecommendationUseCase()
                .addSearchUseCase()
                .build();
        appFrame.pack();
        appFrame.setVisible(true);
    }
}