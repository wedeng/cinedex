package interface_adapter.watched;

import interface_adapter.view.ViewModel;

/**
 * The View Model for the watched View.
 */

public class WatchedViewModel extends ViewModel<WatchedState> {
    public WatchedViewModel() {
        super("watched");
        setState(new WatchedState());
    }
}
