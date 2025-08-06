package interface_adapter.search;

import entity.MovieInterface;
import interface_adapter.view.CardType;
import interface_adapter.MovieDisplayViewModel;
import interface_adapter.MovieDisplayViewModels;
import interface_adapter.MovieDisplayViewModelsInterface;
import interface_adapter.view.CardViewModel;
import interface_adapter.view.MovieDisplayState;
import interface_adapter.view.SearchState;
import interface_adapter.view.SearchViewModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;
import view.CardView;
import view.SearchView;

import java.util.List;

public class SearchPresenter implements SearchOutputBoundary {

    private final MovieDisplayViewModelsInterface movieDisplayViewModels;
    private final CardViewModel cardViewModel;

    public SearchPresenter(MovieDisplayViewModelsInterface movieDisplayViewModels, CardViewModel cardViewModel) {
        this.movieDisplayViewModels = movieDisplayViewModels;
        this.cardViewModel = cardViewModel;
    }

    @Override
    public void prepareSuccessView(SearchOutputData searchOutputData) {
        List<MovieInterface> movies = searchOutputData.getMovies();

        // Get appropriate MovieDisplayViewModel matching the type in the output data
        CardType cardType = CardType.getCardType(searchOutputData.getResultType());
        MovieDisplayViewModel viewModel = movieDisplayViewModels.getMovieDisplayViewModel(cardType);

        // Get the view model's state and replace its contents with the outputted movies
        final MovieDisplayState state = viewModel.getState();
        state.setDisplayedMovies(movies);
        viewModel.setState(state);

        // Set view to that
        cardViewModel.setState(cardType);

    }

    @Override
    public void prepareFailView(String error) {
        // TODO
    }
}
