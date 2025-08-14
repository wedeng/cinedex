package interface_adapter.authentication;

import interface_adapter.view.ViewModel;

/**
 * The View Model for the Authentication View.
 */

public class AuthenticationViewModel extends ViewModel<AuthenticationState> {
    public AuthenticationViewModel() {
        super("authentication");
        setState(new AuthenticationState());
    }
}
