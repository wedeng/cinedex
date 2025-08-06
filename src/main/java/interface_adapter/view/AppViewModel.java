package interface_adapter.view;

import interface_adapter.app.AppState;

/**
 * The ViewModel for the AppView.
 */
public class AppViewModel extends ViewModel<AppState> {

    public AppViewModel() {
        super("app");
        setState(new AppState());
    }

}
