package view;

import interface_adapter.recommendation.RecommendationController;
import interface_adapter.saved.SavedController;
import interface_adapter.view.CardType;
import interface_adapter.view.CardViewModel;
import interface_adapter.view.MovieDisplayState;
import interface_adapter.view.MovieDisplayViewModel;
import interface_adapter.view.MovieDisplayViewModels;
import interface_adapter.watched.WatchedController;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import java.awt.Component;

public class ToolBarView extends JToolBar {
    private final int BUTTON_SIZE = 32;
    private final Icon PLACEHOLDER_ICON = new ImageIcon("src/main/resources/placeholder-icon.png");

    private RecommendationController recommendationController;

    private SavedController savedController;
    private WatchedController watchedController;
    private final CardViewModel cardViewModel;
    private final MovieDisplayViewModels movieDisplayViewModels;

    private final JButton recommendButton = new JButton();
    private final JButton addToSavedButton = new JButton();
    private final JButton removeFromSavedButton = new JButton();
    private final JButton addToWatchedButton = new JButton();
    private final JButton removeFromWatchedButton = new JButton();

    public ToolBarView (SearchView searchView, CardViewModel cardViewModel,
                        MovieDisplayViewModels movieDisplayViewModels) {
        super();
        this.cardViewModel = cardViewModel;
        this.movieDisplayViewModels = movieDisplayViewModels;

        recommendButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(recommendButton)) {
                        recommendationController.execute();
                        System.out.println("Recommend button pressed; Recommendation controller executed");
                    }
                }
        );

        addToSavedButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(addToSavedButton)) {
                        // Get currently displayed MovieDisplayViewModel, then get current selected movie from it
                        CardType cardState = cardViewModel.getState();
                        MovieDisplayViewModel currentMovieDisplay = movieDisplayViewModels.
                                getMovieDisplayViewModel(cardState);
                        MovieDisplayState movieDisplayState = currentMovieDisplay.getState();

                        savedController.executeAddToSaved(movieDisplayState.getSelectedMovie());
                    }
                }
        );

        removeFromSavedButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(removeFromSavedButton)) {
                        // Get currently displayed MovieDisplayViewModel, then get current selected movie from it
                        CardType cardState = cardViewModel.getState();
                        MovieDisplayViewModel currentMovieDisplay = movieDisplayViewModels.
                                getMovieDisplayViewModel(cardState);
                        MovieDisplayState movieDisplayState = currentMovieDisplay.getState();

                        savedController.executeRemoveFromSaved(movieDisplayState.getSelectedMovie());
                    }
                }
        );

        addToWatchedButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(addToWatchedButton)) {
                        // Get currently displayed MovieDisplayViewModel, then get current selected movie from it
                        CardType cardState = cardViewModel.getState();
                        MovieDisplayViewModel currentMovieDisplay = movieDisplayViewModels.
                                getMovieDisplayViewModel(cardState);
                        MovieDisplayState movieDisplayState = currentMovieDisplay.getState();

                        watchedController.executeAddToWatched(movieDisplayState.getSelectedMovie());
                    }
                }
        );

        removeFromWatchedButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(removeFromWatchedButton)) {
                        // Get currently displayed MovieDisplayViewModel, then get current selected movie from it
                        CardType cardState = cardViewModel.getState();
                        MovieDisplayViewModel currentMovieDisplay = movieDisplayViewModels.
                                getMovieDisplayViewModel(cardState);
                        MovieDisplayState movieDisplayState = currentMovieDisplay.getState();

                        watchedController.executeRemoveFromWatched(movieDisplayState.getSelectedMovie());
                    }
                }
        );

        setupButton(recommendButton, AppIcon.RECOMMENDED_32.getIcon());
        setupButton(addToSavedButton, AppIcon.SAVED_32.getIcon());
        setupButton(removeFromSavedButton, AppIcon.SAVED_REMOVE_32.getIcon());
        setupButton(addToWatchedButton, AppIcon.WATCHED_32.getIcon());
        setupButton(removeFromWatchedButton, AppIcon.WATCHED_REMOVE_32.getIcon());

        Component spacer = Box.createHorizontalStrut((int) Math.round(BUTTON_SIZE * 4));

        this.add(spacer);
        this.add(searchView);
    }

    /**
     * Configures the button's style and adds the button to this toolbar.
     * @param button The button to be prepared.
     * @param icon The icon of the button.
     */
    private void setupButton(JButton button, Icon icon) {
        // button.setMaximumSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        button.setIcon(icon);

        this.add(button);
    }

    public void setRecommendationController(RecommendationController recommendationController) {
        this.recommendationController = recommendationController;
    }

    public void setSavedController(SavedController savedController) {
        this.savedController = savedController;
    }

    public void setWatchedController(WatchedController watchedController) {
        this.watchedController = watchedController;
    }
}