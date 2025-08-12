package interface_adapter;

import interface_adapter.view.CardType;

/**
 * An interface for a class that contains the program's view models for the Movie Display view.
 */
public interface MovieDisplayViewModelsInterface {

    public MovieDisplayViewModel getMovieDisplayViewModel(CardType cardType);

    public void setViewModel(CardType cardType, MovieDisplayViewModel viewModel);
}
