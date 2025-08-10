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
import java.util.List;

public class SearchView extends JPanel {

    private final SearchViewModel searchViewModel;
    private final CardViewModel cardViewModel;
    private SearchController searchController;

    private final List<String> fields;
    private final BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);

    private final int BUTTON_SIZE = 32;
    private final Icon SEARCH_ICON = new ImageIcon("src/main/resources/placeholder-icon.png");
    private final Icon FILTER_ICON = new ImageIcon("src/main/resources/placeholder-icon.png");

    final private JButton filterButton = new JButton(FILTER_ICON);
    final private JTextField searchField = new JTextField(20);
    final private JButton searchButton = new JButton(SEARCH_ICON);

    public SearchView(SearchViewModel searchViewModel, CardViewModel cardViewModel, List<String> fields) {
        super();
        this.searchViewModel = searchViewModel;
        this.cardViewModel = cardViewModel;
        this.searchController = searchController;
        this.fields = fields;

        filterButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(filterButton)) {
                        final CardType currentCardType = cardViewModel.getState();
                        final SearchState currentSearchState = searchViewModel.getState();

                        // Set the search state's search type to the current card, unless it's not a searchable card
                        if (currentCardType.isValidSearchType()) {
                            currentSearchState.setCardSearchType(currentCardType);
                        }

                        // Set the current ViewCard to the filter card
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

//            filterButton.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
//            searchButton.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));

        this.add(filterButton);
        this.add(searchField);
        this.add(searchButton);
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }
}