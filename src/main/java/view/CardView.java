package view;

import interface_adapter.view.CardViewModel;
import interface_adapter.view.MovieDisplayViewModel;
import interface_adapter.view.CardType;
import interface_adapter.view.MovieDisplayViewModelsInterface;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CardView extends JPanel implements PropertyChangeListener {
    private final CardViewModel cardViewModel;
    private final CardLayout layout = new CardLayout();

    private final MovieDisplayViewModel discoverViewModel;
    private final MovieDisplayViewModel savedViewModel;
    private final MovieDisplayViewModel watchedViewModel;
    private final MovieDisplayViewModel recommendationViewModel;

    private final MovieDisplayView discoverCard;
    private final MovieDisplayView savedCard;
    private final MovieDisplayView watchedCard;
    private final MovieDisplayView recommendationCard;



    public CardView(CardViewModel cardViewModel, MovieDisplayViewModelsInterface movieDisplayViewModels) {

        super();
        this.cardViewModel = cardViewModel;
        this.cardViewModel.addPropertyChangeListener(this);

        this.setLayout(layout);

        // Initialize view model and view fields
        discoverViewModel = movieDisplayViewModels.getMovieDisplayViewModel(CardType.DISCOVER);
        savedViewModel = movieDisplayViewModels.getMovieDisplayViewModel(CardType.SAVED);
        watchedViewModel = movieDisplayViewModels.getMovieDisplayViewModel(CardType.WATCHED);
        recommendationViewModel = movieDisplayViewModels.getMovieDisplayViewModel(CardType.RECOMMENDED);

        discoverCard = new MovieDisplayView(discoverViewModel);
        savedCard = new MovieDisplayView(savedViewModel);
        watchedCard = new MovieDisplayView(watchedViewModel);
        recommendationCard = new MovieDisplayView(recommendationViewModel);

        // add placeholder contents to each card so they're distinguishable
         discoverCard.add(new JLabel(CardType.DISCOVER.getName()));

        for (int i = 0; i < 16; i++) {
            discoverCard.add(new MovieComponent());
        }

        savedCard.add(new JLabel(CardType.SAVED.getName()));
        watchedCard.add(new JLabel(CardType.WATCHED.getName()));
        recommendationCard.add(new JLabel(CardType.RECOMMENDED.getName()));

        this.setupCard(discoverCard, CardType.DISCOVER.getName());
        this.setupCard(savedCard, CardType.SAVED.getName());
        this.setupCard(watchedCard, CardType.WATCHED.getName());
        this.setupCard(recommendationCard, CardType.RECOMMENDED.getName());
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
