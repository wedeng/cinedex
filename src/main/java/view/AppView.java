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

import interface_adapter.app.AppPageController;

/**
 * The primary View for the program. Contains all other views.
 */
public class AppView extends JFrame implements ActionListener, PropertyChangeListener {

    private final ToolBarView toolBarView;
    private final SearchView searchView;
    private final NavigationMenuView navigationMenu;
    private final CardView cardView;
    private final AppStatusBar statusBar = new AppStatusBar();


    public AppView(NavigationMenuView navigationMenu, CardView cardView, SearchView searchView) {
        super();

        this.navigationMenu = navigationMenu;
        this.cardView = cardView;
        this.searchView = searchView;
        this.toolBarView = new ToolBarView(searchView);

        // Add components
        this.add(toolBarView, BorderLayout.PAGE_START);
        this.add(navigationMenu, BorderLayout.LINE_START);
        this.add(cardView, BorderLayout.CENTER);
        this.add(statusBar, BorderLayout.PAGE_END);
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
//        final AppState state = (AppState) evt.getNewValue();
//        setFields(state);
//        if (state.getError() != null) {
//            JOptionPane.showMessageDialog(this, state.getError(),
//                    "Error", JOptionPane.ERROR_MESSAGE);
//        }
    }

    public void setAppController(AppPageController controller) {

    }

    private class AppStatusBar extends JPanel {

        private final LayoutManager layout = new BoxLayout(this, BoxLayout.X_AXIS);

        private final Icon RELOADING_ICON = new ImageIcon("src/main/resources/placeholder-icon.png");
        private final Icon CHECKMARK_ICON = new ImageIcon("src/main/resources/placeholder-icon.png");

        JLabel statusLabel = new JLabel(CHECKMARK_ICON);

        public AppStatusBar() {
            super();
            this.setLayout(layout);

            // style
            this.setBorder(BorderFactory.createLoweredBevelBorder());

            statusLabel.setText("Status: Up to date");

            this.add(statusLabel);
        }
    }
}

