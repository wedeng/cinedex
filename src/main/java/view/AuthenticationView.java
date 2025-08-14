package view;

import interface_adapter.authentication.AuthenticationController;
import interface_adapter.authentication.AuthenticationState;
import interface_adapter.authentication.AuthenticationViewModel;
import interface_adapter.search.SearchState;
import interface_adapter.view.CardType;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AuthenticationView extends JPanel implements PropertyChangeListener {

    private final String viewName = "authentication";

    private final BorderLayout layout = new BorderLayout();

    private final AuthenticationViewModel authenticationViewModel;
    private AuthenticationController authenticationController;

    private final JButton loginButton = new JButton("Login to TMDB");
    private final JLabel failMessage = new JLabel();

    public AuthenticationView(AuthenticationViewModel authenticationViewModel) {
        System.out.println("AuthenticationView constructor called");
        this.authenticationViewModel = authenticationViewModel;
        this.authenticationViewModel.addPropertyChangeListener(this);

        this.setLayout(layout);

        loginButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(loginButton)) {
                        authenticationController.authenticate();
                        failMessage.setVisible(false);
                    }
                }
        );

        failMessage.setVisible(false);
        failMessage.setForeground(Color.RED);

        this.add(loginButton, BorderLayout.CENTER);
        this.add(failMessage, BorderLayout.SOUTH);
    }

    public void setAuthenticationController(AuthenticationController authenticationController) {
        this.authenticationController = authenticationController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            AuthenticationState state = (AuthenticationState) evt.getNewValue();

            if (!state.isAuthenticationSuccess()) {
                failMessage.setText("Authentication failed: " + state.getAuthenticationErrorMessage());
                failMessage.setVisible(true);
            }
        }
    }

    public String getViewName() {
        return viewName;
    }
}
