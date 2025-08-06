package interface_adapter.watched;

import use_case.watched.WatchedOutputBoundary;
import use_case.watched.WatchedOutputData;

/**
 * The presenter for our watched use case of our application.
 */

public class WatchedPresenter implements WatchedOutputBoundary {

    private final WatchedViewModel watchedViewModel;

    public WatchedPresenter(WatchedViewModel watchedViewModel) {
        this.watchedViewModel = watchedViewModel;
    }

    @Override
    public void prepareSuccessView(WatchedOutputData watchedOutputData) {
        final WatchedState watchedState = watchedViewModel.getState();

        watchedState.setWatchedManagerErrorMessage(null);
        watchedState.setWatchedManagerSuccess(true);

        this.watchedViewModel.setState(watchedState);
        this.watchedViewModel.firePropertyChanged("watched");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final WatchedState watchedState = watchedViewModel.getState();

        watchedState.setWatchedManagerErrorMessage(errorMessage);
        watchedState.setWatchedManagerSuccess(false);

        this.watchedViewModel.setState(watchedState);
        this.watchedViewModel.firePropertyChanged("watched");
    }
}
