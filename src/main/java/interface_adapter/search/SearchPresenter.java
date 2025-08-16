package interface_adapter.search;

import entity.MovieInterface;
import interface_adapter.view.CardType;
import interface_adapter.view.MovieDisplayViewModel;
import interface_adapter.view.CardViewModel;
import interface_adapter.view.MovieDisplayState;
import interface_adapter.view.MovieDisplayViewModels;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

import javax.swing.JOptionPane;
import java.util.List;

public class SearchPresenter implements SearchOutputBoundary {

    private final MovieDisplayViewModels movieDisplayViewModels;
    private final CardViewModel cardViewModel;

    public SearchPresenter(MovieDisplayViewModels movieDisplayViewModels, CardViewModel cardViewModel) {
        this.movieDisplayViewModels = movieDisplayViewModels;
        this.cardViewModel = cardViewModel;
    }

    @Override
    public void prepareSuccessView(SearchOutputData searchOutputData) {
        List<MovieInterface> movies = searchOutputData.getMovies();

        // Replace the contents of the Discover MovieDisplayModel with the outputted movies
        MovieDisplayViewModel discoverViewModel = movieDisplayViewModels.getMovieDisplayViewModel(CardType.DISCOVER);
        final MovieDisplayState state = discoverViewModel.getState();
        state.setDisplayedMovies(movies);
        discoverViewModel.setState(state);
        discoverViewModel.firePropertyChanged("state");

        // Set view to Discover
        cardViewModel.setState(CardType.DISCOVER);

    }

    @Override
    public void prepareFailView(String error) {
        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
