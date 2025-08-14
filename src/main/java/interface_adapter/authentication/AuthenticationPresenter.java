package interface_adapter.authentication;

import use_case.authentication.AuthenticationOutputBoundary;
import use_case.authentication.AuthenticationOutputData;

/**
 * The presenter for our authentication use case of our application.
 */

public class AuthenticationPresenter implements AuthenticationOutputBoundary {
    private final AuthenticationViewModel authenticationViewModel;

    public AuthenticationPresenter(AuthenticationViewModel authenticationViewModel) {
        this.authenticationViewModel = authenticationViewModel;
    }

    @Override
    public void prepareSuccessView(AuthenticationOutputData outputData) {
        final AuthenticationState authenticationState = this.authenticationViewModel.getState();

        authenticationState.setAuthenticationErrorMessage(null);
        authenticationState.setAuthenticationSuccess(true);

        this.authenticationViewModel.setState(authenticationState);
        this.authenticationViewModel.firePropertyChanged("authentication");

    }

    @Override
    public void prepareFailView(String errorMessage) {
        final AuthenticationState authenticationState = this.authenticationViewModel.getState();

        authenticationState.setAuthenticationErrorMessage(errorMessage);
        authenticationState.setAuthenticationSuccess(false);

        this.authenticationViewModel.setState(authenticationState);
        this.authenticationViewModel.firePropertyChanged("authentication");
    }
}
