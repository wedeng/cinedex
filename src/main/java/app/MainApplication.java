package app;

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

    /**
     * The main entry point of the program.
     * @param args main arguments
     */
    public static void main(String[] args) {

        final AppBuilder builder = new AppBuilder();
        final JFrame appFrame = builder.addViews()
                .addSearchUseCase()
                .build();
        appFrame.pack();
        appFrame.setVisible(true);

    }
}