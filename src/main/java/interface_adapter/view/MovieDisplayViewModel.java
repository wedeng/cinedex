package interface_adapter.view;

public class MovieDisplayViewModel extends ViewModel<MovieDisplayState> {

    public MovieDisplayViewModel(CardType cardType) {
        super(cardType.getName().toLowerCase());
        setState(new MovieDisplayState(cardType));
    }

}
