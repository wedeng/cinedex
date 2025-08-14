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
    }
}
