package view;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * The main view for the program. Contains all other views except for AuthenticationView.
 */
public class AppView extends JPanel {

    private final String viewName = "app";

    private final BorderLayout borderLayout = new BorderLayout();

    private final ToolBarView toolBarView;
    private final SearchView searchView;
    private final NavigationMenuView navigationMenu;
    private final CardView cardView;

    private final AppViewModel appViewModel;
    private final AppToolBar toolBar = new AppToolBar();
    private final AppSearchBar searchBar = new AppSearchBar();
    private final AppNavigationMenu navigationMenu = new AppNavigationMenu();
    private final AppCentralView centralView = new AppCentralView();
    private final AppStatusBar statusBar = new AppStatusBar();


    public AppView(NavigationMenuView navigationMenu, CardView cardView, SearchView searchView, ToolBarView toolBarView) {

        this.navigationMenu = navigationMenu;
        this.cardView = cardView;
        this.searchView = searchView;
        this.toolBarView = toolBarView;

        this.setLayout(borderLayout);

        // Add components
        this.add(toolBarView, BorderLayout.PAGE_START);
        this.add(navigationMenu, BorderLayout.LINE_START);
        this.add(cardView, BorderLayout.CENTER);
    }

    public String getViewName() {
        return viewName;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final AppState state = (AppState) evt.getNewValue();
        setFields(state);
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setFields(AppState state) {
        ;
    }

    public void setAppController(AppPageController controller) {

    }

    public class AppNavigationMenu extends JPanel {
        private final int BUTTON_SIZE = 32;
        private final Icon PLACEHOLDER_ICON = new ImageIcon("src/main/resources/placeholder-icon.png");

        private final LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);

        private final JButton discoverButton = new JButton("Discover");
        private final JButton savedButton = new JButton("Saved");
        private final JButton watchedButton = new JButton("Watched");
        private final JButton settingsButton = new JButton("Settings");

        public AppNavigationMenu() {
            super();
            this.setLayout(layout);

            // style
            this.setBorder(BorderFactory.createLineBorder(Color.black));

            prepareButton(discoverButton, ViewCard.DISCOVER, PLACEHOLDER_ICON);
            prepareButton(savedButton, ViewCard.SAVED, PLACEHOLDER_ICON);
            prepareButton(watchedButton, ViewCard.WATCHED, PLACEHOLDER_ICON);
            prepareButton(settingsButton, ViewCard.SETTINGS, PLACEHOLDER_ICON);
        }

        /**
         * Configures the button's style, adds an action listener,
         * and adds the button to this menu.
         *
         * @param button The button to be prepared.
         */
        private void prepareButton(JButton button, ViewCard viewCard, Icon icon) {
            // button.setMaximumSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE))
            button.setIcon(icon);

            button.addActionListener(
                    evt -> {
                        if (evt.getSource().equals(button)) {
                            // TODO: Delete this test print statement
                            centralView.setActiveCard(viewCard);
                            System.out.println("Set active card in AppViewModel to " + viewCard.getName());
                        }
                    }
            );

            this.add(button);
        }
    }

    public class AppCentralView extends JPanel {
        private final CardLayout layout = new CardLayout();

        private final JPanel discoverCard = new JPanel();
        private final JPanel savedCard = new JPanel();
        private final JPanel watchedCard = new JPanel();
        private final JPanel settingsCard = new JPanel();

        public AppCentralView() {
            super();
            this.setLayout(layout);

            // add placeholder contents to each card so they're distinguishable
            // discoverCard.add(new JLabel(DISCOVER_CARD_NAME));

            for (int i = 0; i < 16; i++) {
                discoverCard.add(new MovieComponent());
            }

            savedCard.add(new JLabel(ViewCard.SAVED.getName()));
            watchedCard.add(new JLabel(ViewCard.WATCHED.getName()));
            settingsCard.add(new JLabel(ViewCard.SETTINGS.getName()));

            this.setupCard(discoverCard, ViewCard.DISCOVER.getName());
            this.setupCard(savedCard, ViewCard.SAVED.getName());
            this.setupCard(watchedCard, ViewCard.WATCHED.getName());
            this.setupCard(settingsCard, ViewCard.SETTINGS.getName());
        }

        private void setupCard(JPanel card, String cardName) {
            this.add(card, cardName);
        }

        public void setActiveCard(ViewCard activeViewCard) {
            layout.show(this, activeViewCard.getName());
            // TODO: Delete this test print statement
            System.out.println("Set active card in AppCentralView to " + activeViewCard.getName());
        }
    }

    public class AppToolBar extends JToolBar {
        private final int BUTTON_SIZE = 32;
        private final Icon PLACEHOLDER_ICON = new ImageIcon("src/main/resources/placeholder-icon.png");

        private final JButton recommendButton = new JButton();
        private final JButton saveButton = new JButton();
        private final JButton watchedButton = new JButton();
        private final JButton rateButton = new JButton();

        private final AppSearchBar searchBar = new AppSearchBar();

        public AppToolBar() {
            super();

            recommendButton.addActionListener(
                    evt -> {
                        if (evt.getSource().equals(recommendButton)) {
                            // appPageController.execute(noteInputField.getText());
                            ;
                        }
                    }
            );

            saveButton.addActionListener(
                    evt -> {
                        if (evt.getSource().equals(saveButton)) {
                            // appPageController.execute(noteInputField.getText());
                            ;
                        }
                    }
            );

            watchedButton.addActionListener(
                    evt -> {
                        if (evt.getSource().equals(watchedButton)) {
                            // appPageController.execute(noteInputField.getText());
                            ;
                        }
                    }
            );

            rateButton.addActionListener(
                    evt -> {
                        if (evt.getSource().equals(rateButton)) {
                            // appPageController.execute(noteInputField.getText());
                            ;
                        }
                    }
            );

            setupButton(recommendButton, PLACEHOLDER_ICON);
            setupButton(saveButton, PLACEHOLDER_ICON);
            setupButton(watchedButton, PLACEHOLDER_ICON);
            setupButton(rateButton, PLACEHOLDER_ICON);

            Component spacer = Box.createHorizontalStrut((int) Math.round(BUTTON_SIZE * 4));

            this.add(spacer);
            this.add(searchBar);
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
    }

    public class AppSearchBar extends JPanel {

        LayoutManager layout = new BoxLayout(this, BoxLayout.X_AXIS);

        private final int BUTTON_SIZE = 32;
        private final Icon SEARCH_ICON = new ImageIcon("src/main/resources/placeholder-icon.png");
        private final Icon FILTER_ICON = new ImageIcon("src/main/resources/placeholder-icon.png");

        final private JButton filterButton = new JButton(FILTER_ICON);
        final private JTextField searchField = new JTextField();
        final private JButton searchButton = new JButton(SEARCH_ICON);

        public AppSearchBar() {
            super();
            this.setLayout(layout);

            filterButton.addActionListener(
                    evt -> {
                        if (evt.getSource().equals(filterButton)) {
                            // appPageController.execute(noteInputField.getText());
                            ;
                        }
                    }
            );

            searchButton.addActionListener(
                    evt -> {
                        if (evt.getSource().equals(searchButton)) {
                            // appPageController.execute(noteInputField.getText());
                            ;
                        }
                    }
            );

//            filterButton.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
//            searchButton.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));

            this.add(filterButton);
            this.add(searchField);
            this.add(searchButton);
        }
    }

    public class AppStatusBar extends JPanel {

        private final LayoutManager layout = new BoxLayout(this, BoxLayout.X_AXIS);

        private final Icon RELOADING_ICON = new ImageIcon("src/main/resources/placeholder-icon.png");
        private final Icon CHECKMARK_ICON = new ImageIcon("src/main/resources/placeholder-icon.png");

        JLabel statusLabel = new JLabel(CHECKMARK_ICON);

        public StatusBarView() {
            super();
            this.setLayout(layout);

            // Style
            this.setBorder(BorderFactory.createLoweredBevelBorder());

            statusLabel.setText("Status: Up to date");

            this.add(statusLabel);
        }
    }
}
