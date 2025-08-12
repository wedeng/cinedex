package interface_adapter.view;

import java.util.List;

/**
 * An interface for a class that contains the program's view models for the Movie Display view.
 */
public interface MovieDisplayViewModelsInterface {

    public MovieDisplayViewModel getMovieDisplayViewModel(CardType cardType);

    public MovieDisplayViewModel getMovieDisplayViewModel(String name);

    public List<MovieDisplayViewModel> getMovieDisplayViewModels();

    public void addViewModel(MovieDisplayViewModel viewModel);
}
