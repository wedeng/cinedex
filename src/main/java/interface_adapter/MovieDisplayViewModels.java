package interface_adapter;

import interface_adapter.view.CardType;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of MovieDisplayViewModelsInterface using a HashMap.
 */
public class MovieDisplayViewModels implements MovieDisplayViewModelsInterface {
    private final Map<CardType, MovieDisplayViewModel> viewModels = new HashMap<>();

    @Override
    public MovieDisplayViewModel getMovieDisplayViewModel(CardType cardType) {
        return null;
    }

    @Override
    public void setViewModel(CardType cardType, MovieDisplayViewModel viewModel) {

    }
}
