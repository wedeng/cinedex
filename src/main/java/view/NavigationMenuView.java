package view;

import interface_adapter.view.CardViewModel;
import interface_adapter.view.CardType;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.LayoutManager;

public class NavigationMenuView extends JPanel {
    final private CardViewModel cardViewModel;

    private final int BUTTON_SIZE = 32;
    private final Icon DISCOVER_ICON = new ImageIcon("src/main/resources/Discover.png");
    private final Icon SAVED_ICON = new ImageIcon("src/main/resources/Saved.png");
    private final Icon WATCHED = new ImageIcon("src/main/resources/Watched.png");
    private final Icon RECOMMENDED_ICON = new ImageIcon("src/main/resources/Recommend.png");

    private final LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);

    private final JButton discoverButton = new JButton(CardType.DISCOVER.getName());
    private final JButton savedButton = new JButton(CardType.SAVED.getName());
    private final JButton watchedButton = new JButton(CardType.WATCHED.getName());
    private final JButton recommendedButton = new JButton(CardType.RECOMMENDED.getName());

    public NavigationMenuView(CardViewModel cardViewModel) {
        super();
        this.setLayout(layout);
        this.cardViewModel = cardViewModel;

        // style
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        prepareButton(discoverButton, CardType.DISCOVER, DISCOVER_ICON);
        prepareButton(savedButton, CardType.SAVED, SAVED_ICON);
        prepareButton(watchedButton, CardType.WATCHED, WATCHED);
        prepareButton(recommendedButton, CardType.RECOMMENDED, RECOMMENDED_ICON);
    }

    /**
     * Configures the button's style, adds an action listener,
     * and adds the button to this menu.
     *
     * @param button The button to be prepared.
     */
    private void prepareButton(JButton button, CardType cardType, Icon icon) {
        // button.setMaximumSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE))
        button.setIcon(icon);

        button.addActionListener(
                evt -> {
                    if (evt.getSource().equals(button)) {
                        cardViewModel.setState(cardType);
                        System.out.println("Set active card in AppViewModel to " + cardType.getName() + " in NavigationMenuView");
                    }
                }
        );

        this.add(button);
    }
}
