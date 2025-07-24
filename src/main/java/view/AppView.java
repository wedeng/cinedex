package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import interface_adapter.app.AppPageController;
import interface_adapter.app.AppPageState;
import interface_adapter.app.AppViewModel;

/**
 * The View for when the user is viewing a note in the program.
 */
public class AppView extends JFrame implements ActionListener, PropertyChangeListener {

    private final AppViewModel appViewModel;

    private final AppToolBar toolBar = new AppToolBar();
    private final AppNavigationMenu navigationMenu = new AppNavigationMenu();
    private final AppCentralView centralView = new AppCentralView();

    private AppPageController appPageController;

    public AppView(AppViewModel appViewModel) {

        this.appViewModel = appViewModel;
        this.appViewModel.addPropertyChangeListener(this);


        this.add(toolBar, BorderLayout.PAGE_START);
        this.add(navigationMenu, BorderLayout.LINE_START);
        this.add(centralView, BorderLayout.CENTER);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final AppPageState state = (AppPageState) evt.getNewValue();
//        setFields(state);
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

//    private void setFields(NoteState state) {
//        noteInputField.setText(state.getNote());
//    }

    public void setAppController(AppPageController controller) {
        this.appPageController = controller;
    }

    private class AppNavigationMenu extends JPanel {
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

            prepareButton(discoverButton, PLACEHOLDER_ICON);
            prepareButton(savedButton, PLACEHOLDER_ICON);
            prepareButton(watchedButton, PLACEHOLDER_ICON);
            prepareButton(settingsButton, PLACEHOLDER_ICON);
        }

        /**
         * Configures the button's style, adds an action listener,
         * and adds the button to this menu.
         * @param button The button to be prepared.
         */
        private void prepareButton(JButton button, Icon icon) {
            // button.setMaximumSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE))
            button.setIcon(icon);

            button.addActionListener(
                    evt -> {
                        if (evt.getSource().equals(button)) {
                            // appPageController.execute(noteInputField.getText());
                            ;
                        }
                    }
            );

            this.add(button);
        }
    }

    private class AppToolBar extends JToolBar {
        private final int BUTTON_SIZE = 32;
        private final Icon PLACEHOLDER_ICON = new ImageIcon("src/main/resources/placeholder-icon.png");

        private final JButton recommendButton = new JButton();
        private final JButton saveButton = new JButton();
        private final JButton watchedButton = new JButton();
        private final JButton rateButton = new JButton();

        public AppToolBar() {
            super();

            prepareButton(recommendButton, PLACEHOLDER_ICON);
            prepareButton(saveButton, PLACEHOLDER_ICON);
            prepareButton(watchedButton, PLACEHOLDER_ICON);
            prepareButton(rateButton, PLACEHOLDER_ICON);
        }

        /**
         * Configures the button's style, adds an action listener,
         * and adds the button to this toolbar.
         * @param button The button to be prepared.
         * @param icon The icon of the button.
         */
        private void prepareButton(JButton button, Icon icon) {
            // button.setMaximumSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
            button.setIcon(icon);

            button.addActionListener(
                    evt -> {
                        if (evt.getSource().equals(button)) {
                            // appPageController.execute(noteInputField.getText());
                            ;
                        }
                    }
            );

            this.add(button);
        }
    }

    private class AppCentralView extends JPanel {
        private final LayoutManager layout = new CardLayout();

        private final String DISCOVER_CARD_NAME = "Discover";
        private final String SAVED_CARD_NAME = "Saved";
        private final String WATCHED_CARD_NAME = "Watched";
        private final String SETTINGS_CARD_NAME = "Settings";

        private final JPanel discoverCard = new JPanel();
        private final JPanel savedCard = new JPanel();
        private final JPanel watchedCard = new JPanel();
        private final JPanel settingsCard = new JPanel();


        public AppCentralView() {
            super();
            this.setLayout(layout);

            // add placeholder contents to each card so they're distinguishable
            // discoverCard.add(new JLabel(DISCOVER_CARD_NAME));

            for (int i = 0; i < 9; i++) {
                discoverCard.add(new MovieComponent());
            }

            savedCard.add(new JLabel(SAVED_CARD_NAME));
            watchedCard.add(new JLabel(WATCHED_CARD_NAME));
            settingsCard.add(new JLabel(SETTINGS_CARD_NAME));


            this.add(discoverCard);
            this.add(savedCard);
            this.add(watchedCard);
            this.add(settingsCard);
        }
    }
}

