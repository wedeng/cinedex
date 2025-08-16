package interface_adapter.authentication;

import interface_adapter.view.ViewManagerModel;
import use_case.authentication.AuthenticationOutputBoundary;
import use_case.authentication.AuthenticationOutputData;

/**
 * The presenter for our authentication use case of our application.
 */
public class AuthenticationPresenter implements AuthenticationOutputBoundary {
    private final AuthenticationViewModel authenticationViewModel;
    private final ViewManagerModel viewManagerModel;

    public AuthenticationPresenter(AuthenticationViewModel authenticationViewModel, ViewManagerModel viewManagerModel) {
        this.authenticationViewModel = authenticationViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(AuthenticationOutputData outputData) {
        final AuthenticationState authenticationState = this.authenticationViewModel.getState();

        authenticationState.setAuthenticationErrorMessage(null);
        authenticationState.setAuthenticationSuccess(true);

        this.authenticationViewModel.setState(authenticationState);
        this.authenticationViewModel.firePropertyChanged("state");

        this.viewManagerModel.setState("app");
        this.viewManagerModel.firePropertyChanged("state");

        System.out.println("prepareSuccessView in AuthenticationPresenter called");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final AuthenticationState authenticationState = this.authenticationViewModel.getState();

        authenticationState.setAuthenticationErrorMessage(errorMessage);
        authenticationState.setAuthenticationSuccess(false);

        this.authenticationViewModel.setState(authenticationState);
        this.authenticationViewModel.firePropertyChanged("state");

        System.out.println("prepareFailView in AuthenticationPresenter called");
        System.out.println("\tAuthenticationPresenter error message: " + errorMessage);
    }
}
