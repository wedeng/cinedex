import interface_adapter.view.AppViewModel;
import interface_adapter.view.ViewCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.AppView;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class AppViewTest {
    private AppView appView;
    private AppViewModel mockViewModel;

    @BeforeEach
    public void setUp() {
        mockViewModel = new AppViewModel();
        appView = new AppView(mockViewModel);
    }

    @Test
    void testComponentPlacement() {
        Component[]  components = appView.getContentPane().getComponents();
        Assertions.assertEquals(4, components.length);
        Assertions.assertInstanceOf(AppView.AppToolBar.class, components[0]);
        Assertions.assertInstanceOf(AppView.AppNavigationMenu.class, components[1]);
        Assertions.assertInstanceOf(AppView.AppCentralView.class, components[2]);
        Assertions.assertInstanceOf(AppView.AppStatusBar.class, components[3]);
    }

    @Test
    void testNavigationMenuButtons() {
        AppView.AppNavigationMenu menu = appView.new AppNavigationMenu();
        Assertions.assertEquals(4, menu.getComponentCount());

        for (Component c : menu.getComponents()) {
            Assertions.assertInstanceOf(JButton.class, c);
        }
    }

    @Test
    void testNavigateButtonActions() {
        AppView.AppNavigationMenu menu = appView.new AppNavigationMenu();
        AppView.AppCentralView centralView = appView.new AppCentralView();

        for (Component c : menu.getComponents()) {
            JButton button = (JButton) c;
            button.doClick();
            Assertions.assertTrue(centralView.getLayout().toString().contains(button.getText()));
        }

    }

    @Test
    void testCentralViewCards() {
        AppView.AppCentralView centralView = appView.new AppCentralView();
        Assertions.assertEquals(4, centralView.getComponentCount());

        CardLayout layout = (CardLayout) centralView.getLayout();
        for (ViewCard card : ViewCard.values()) {
            layout.show(centralView, card.getName());
            Component visible = centralView.getComponent(0);
            Assertions.assertTrue(visible.isVisible());
        }
    }

    @Test
    void testToolbarComponents() {
        AppView.AppToolBar toolbar = appView.new AppToolBar();
        Assertions.assertTrue(toolbar.getComponentCount() >= 5);
        Assertions.assertInstanceOf(AppView.AppSearchBar.class, toolbar.getComponent(1));
    }

    @Test
    void testSearchBarComponents() {
        AppView.AppSearchBar searchBar = appView.new AppSearchBar();
        Assertions.assertEquals(3, searchBar.getComponentCount());
        Assertions.assertInstanceOf(JButton.class, searchBar.getComponent(0));
        Assertions.assertInstanceOf(JTextField.class, searchBar.getComponent(1));
        Assertions.assertInstanceOf(JButton.class, searchBar.getComponent(2));
    }
}
