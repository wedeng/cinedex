package view;

import interface_adapter.saved.SavedController;
import interface_adapter.view.CardViewModel;
import interface_adapter.view.CardType;
import interface_adapter.view.MovieDisplayViewModels;
import interface_adapter.watched.WatchedController;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.LayoutManager;

public class NavigationMenuView extends JPanel {
    final private CardViewModel cardViewModel;

    private final int BUTTON_SIZE = 32;

    private final LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);

    private final JButton discoverButton = new JButton(CardType.DISCOVER.getName());
    private final JButton savedButton = new JButton(CardType.SAVED.getName());
    private final JButton watchedButton = new JButton(CardType.WATCHED.getName());
    private final JButton recommendedButton = new JButton(CardType.RECOMMENDED.getName());

    public NavigationMenuView(CardViewModel cardViewModel) {
        super();
        // SavedController savedController, WatchedController watchedController,
        this.setLayout(layout);
        this.cardViewModel = cardViewModel;

        // style
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        // Discover
        discoverButton.setIcon(AppIcon.DISCOVER_64.getIcon());
        discoverButton.addActionListener(
                evt3 -> {
                    if (evt3.getSource().equals(discoverButton)) {
                        this.cardViewModel.setState(CardType.DISCOVER);
                        System.out.println("Set active card in AppViewModel to " + CardType.DISCOVER.getName() + " in NavigationMenuView");
                    }
                }
        );

        // Saved
        savedButton.setIcon(AppIcon.SAVED_64.getIcon());
        savedButton.addActionListener(
                evt2 -> {
                    if (evt2.getSource().equals(savedButton)) {
                        this.cardViewModel.setState(CardType.SAVED);
                        System.out.println("Set active card in AppViewModel to " + CardType.SAVED.getName() + " in NavigationMenuView");

                    }
                }
        );

        // Watched
        watchedButton.setIcon(AppIcon.WATCHED_64.getIcon());
        watchedButton.addActionListener(
                evt1 -> {
                    if (evt1.getSource().equals(watchedButton)) {
                        this.cardViewModel.setState(CardType.WATCHED);
                        System.out.println("Set active card in AppViewModel to " + CardType.WATCHED.getName() + " in NavigationMenuView");
                    }
                }
        );

        // Recommended
        recommendedButton.setIcon(AppIcon.RECOMMENDED_64.getIcon());
        recommendedButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(recommendedButton)) {
                        this.cardViewModel.setState(CardType.RECOMMENDED);
                        System.out.println("Set active card in AppViewModel to " + CardType.RECOMMENDED.getName() + " in NavigationMenuView");
                    }
                }
        );

        this.add(discoverButton);
        this.add(savedButton);
        this.add(watchedButton);
        this.add(recommendedButton);
    }

}
