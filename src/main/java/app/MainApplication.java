package app;

import data_access.MovieDataAceessObject;
import use_case.search.MovieSearchService;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

/**
 The main entry point of the application; it launches the program (e.g., starts the Swing UI)
 and invokes the AppBuilder to set up the app.
 */

public class MainApplication {

    public static void main(String[] args) {

        final AppBuilder builder = new AppBuilder();
        builder.addViews()
                .addSearchUseCase()
                .build().setVisible(true);

    }
}
