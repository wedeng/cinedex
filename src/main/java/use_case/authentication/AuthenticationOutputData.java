package use_case.authentication;

public class AuthenticationOutputData {
    private final boolean isAuthenticated;

    public AuthenticationOutputData(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }
}
