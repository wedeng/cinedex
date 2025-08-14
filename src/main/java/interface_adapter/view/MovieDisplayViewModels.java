package interface_adapter.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An implementation of MovieDisplayViewModelsInterface using a HashMap.
 */
public class MovieDisplayViewModels {
    private final Map<CardType, MovieDisplayViewModel> viewModels = new HashMap<>();

    public MovieDisplayViewModel getMovieDisplayViewModel(CardType cardType) {
        return viewModels.get(cardType);
    }

    public MovieDisplayViewModel getMovieDisplayViewModel(String name) {
        return this.getMovieDisplayViewModel(CardType.getCardType(name));
    }

    public List<MovieDisplayViewModel> getMovieDisplayViewModels() {
        return new ArrayList<>(viewModels.values());
    }

    public void addViewModel(MovieDisplayViewModel viewModel) {
        viewModels.put(viewModel.getState().getCardType(), viewModel);
    }
}
