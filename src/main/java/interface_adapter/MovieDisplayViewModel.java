package interface_adapter;

import interface_adapter.view.MovieDisplayState;
import interface_adapter.view.CardType;
import interface_adapter.view.ViewModel;

public class MovieDisplayViewModel extends ViewModel<MovieDisplayState> {

    public MovieDisplayViewModel(CardType cardType) {
        super("movie display");
        setState(new MovieDisplayState(cardType));
    }

}
