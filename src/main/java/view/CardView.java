package view;

import interface_adapter.view.CardViewModel;
import interface_adapter.MovieDisplayViewModel;
import interface_adapter.view.CardType;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CardView extends JPanel implements PropertyChangeListener {
    private final CardViewModel cardViewModel;
    private final CardLayout layout = new CardLayout();

    private final MovieDisplayViewModel discoverViewModel = new MovieDisplayViewModel(CardType.DISCOVER);
    private final MovieDisplayViewModel savedViewModel = new MovieDisplayViewModel(CardType.SAVED);
    private final MovieDisplayViewModel watchedViewModel = new MovieDisplayViewModel(CardType.WATCHED);

    private final MovieDisplayView discoverCard = new MovieDisplayView(discoverViewModel);
    private final MovieDisplayView savedCard = new MovieDisplayView(savedViewModel);
    private final MovieDisplayView watchedCard = new MovieDisplayView(watchedViewModel);

    public CardView(CardViewModel cardViewModel) {

        super();
        this.cardViewModel = cardViewModel;
        this.cardViewModel.addPropertyChangeListener(this);

        this.setLayout(layout);

        // add placeholder contents to each card so they're distinguishable
         discoverCard.add(new JLabel(CardType.DISCOVER.getName()));

        for (int i = 0; i < 16; i++) {
            discoverCard.add(new MovieComponent());
        }

        savedCard.add(new JLabel(CardType.SAVED.getName()));
        watchedCard.add(new JLabel(CardType.WATCHED.getName()));

        this.setupCard(discoverCard, CardType.DISCOVER.getName());
        this.setupCard(savedCard, CardType.SAVED.getName());
        this.setupCard(watchedCard, CardType.WATCHED.getName());
    }

    private void setupCard(JPanel card, String cardName) {
        this.add(card, cardName);
    }

    private void setActiveCard(CardType cardType) {
        layout.show(this, cardType.getName());
        // TODO: Delete this test print statement
        System.out.println("Set active card in AppCentralView to " + cardType.getName());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            System.out.println("Property Change: " + evt.getPropertyName());
            final CardType state = (CardType) evt.getNewValue();
            setActiveCard(state);
        }
    }

}
