package view;

import interface_adapter.search.SearchController;
import interface_adapter.view.CardViewModel;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;
import interface_adapter.view.CardType;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class SearchView extends JPanel implements PropertyChangeListener {

    private final SearchViewModel searchViewModel;
    private final CardViewModel cardViewModel;
    private SearchController searchController;

    private final List<String> fields;
    private final BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);

    private final int BUTTON_SIZE = 32;

    final private JButton filterButton = new JButton(AppIcon.FILTER_32.getIcon());
    final private JTextField searchField = new JTextField(20);
    final private JButton searchButton = new JButton(AppIcon.SEARCH_32.getIcon());

    public SearchView(SearchViewModel searchViewModel, CardViewModel cardViewModel, List<String> fields) {
        super();
        this.searchViewModel = searchViewModel;
        this.cardViewModel = cardViewModel;
        this.cardViewModel.addPropertyChangeListener(this);
        this.searchViewModel.getState().setCardSearchType(this.cardViewModel.getState());
        this.fields = fields;

        filterButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(filterButton)) {
                        cardViewModel.setState(CardType.FILTER);
                    }
                }
        );

        searchButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(searchButton)) {
                        final SearchState currentState = searchViewModel.getState();
                        searchController.execute(
                                searchField.getText(),
                                currentState.getSearchFields(),
                                currentState.getCardSearchType().getName());
                    }
                }
        );

        this.add(filterButton);
        this.add(searchField);
        this.add(searchButton);
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            System.out.println("Property Change: " + evt.getPropertyName() + " (in SearchView)");
            CardType cardState = (CardType) evt.getNewValue();

            // Update search state if the new card is a searchable option
            if (cardState.isValidSearchType()) {
                SearchState searchState = searchViewModel.getState();
                searchState.setCardSearchType(cardState);
            }
        }
    }
}