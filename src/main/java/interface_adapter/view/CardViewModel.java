package interface_adapter.view;

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
