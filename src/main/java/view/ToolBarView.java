package view;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import java.awt.Component;

public class ToolBarView extends JToolBar {
    private final int BUTTON_SIZE = 32;
    private final Icon SAVED_ICON = new ImageIcon("src/main/resources/savedicon.png");
    private final Icon WATCHED = new ImageIcon("src/main/resources/watchedicon.png");
    private final Icon RECOMMENDED_ICON = new ImageIcon("src/main/resources/recommendicon.png");

    private final JButton recommendButton = new JButton();
    private final JButton saveButton = new JButton();
    private final JButton watchedButton = new JButton();

    public ToolBarView (SearchView searchView) {
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

        setupButton(recommendButton, RECOMMENDED_ICON);
        setupButton(saveButton, SAVED_ICON);
        setupButton(watchedButton, WATCHED);

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
}
