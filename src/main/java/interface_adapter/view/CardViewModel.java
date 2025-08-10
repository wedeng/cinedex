package interface_adapter.view;

/**
 * The View Model for the cards View.
 * The stored state is the currently displayed card.
 */
public class CardViewModel extends ViewModel<CardType> {

    public CardViewModel() {
        super("card");
        setState(CardType.DISCOVER);
    }

//    @Override
//    public void firePropertyChanged() {
//        super.firePropertyChanged();
//        System.out.println("Property changed");
//    }

    @Override
    public void setState(CardType state) {
        // TODO: Delete this temp code after troubleshooting
        super.setState(state);
        this.firePropertyChanged();
    }

}
