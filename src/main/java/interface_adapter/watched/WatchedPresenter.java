package interface_adapter.watched;

import interface_adapter.view.MovieDisplayState;
import interface_adapter.view.MovieDisplayViewModel;
import use_case.watched.WatchedOutputBoundary;
import use_case.watched.WatchedOutputData;

/**
 * The presenter for our watched use case of our application.
 */

public class WatchedPresenter implements WatchedOutputBoundary {

    private final MovieDisplayViewModel watchedViewModel;

    public WatchedPresenter(MovieDisplayViewModel watchedViewModel) {
        this.watchedViewModel = watchedViewModel;
    }

    @Override
    public void prepareSuccessView(WatchedOutputData watchedOutputData) {
        final MovieDisplayState watchedState = watchedViewModel.getState();

        watchedState.setRetrievalError(null);

        this.watchedViewModel.setState(watchedState);
        this.watchedViewModel.firePropertyChanged("state");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final MovieDisplayState watchedState = watchedViewModel.getState();

        watchedState.setRetrievalError(errorMessage);

        this.watchedViewModel.setState(watchedState);
        this.watchedViewModel.firePropertyChanged("state");
    }
}
