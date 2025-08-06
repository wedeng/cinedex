import interface_adapter.view.AppViewModel;
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
}
