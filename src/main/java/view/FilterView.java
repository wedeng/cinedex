package view;

import entity.MovieFieldInterface;
import interface_adapter.search.SearchArgument;
import interface_adapter.search.SearchField;
import interface_adapter.search.SearchViewModel;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterView extends JPanel {

    private final SearchViewModel searchViewModel;
    private final BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
    private final List<SearchFieldComponent> searchFieldComponents = new ArrayList<>();

    public FilterView(SearchViewModel searchViewModel, List<String> filterFields) {
        super();
        this.searchViewModel = searchViewModel;
        this.setLayout(layout);

        for (SearchField searchField : SearchField.values()) {

            SearchFieldComponent searchFieldComponent;
            if (searchField.hasLimitedArguments()) {
                searchFieldComponent = new SearchFieldComboBox(
                        searchViewModel,
                        searchField.getIdentifier(),
                        searchField.getDisplayName(),
                        searchField.getAllValidArguments());
            }
            else {
                searchFieldComponent = new SearchFieldTextBox(
                        searchViewModel,
                        searchField.getIdentifier(),
                        searchField.getDisplayName());
            }

            this.addSearchFieldComponent(searchFieldComponent);
        }
    }

    private void addSearchFieldComponent(SearchFieldComponent searchFieldComponent) {
        this.searchFieldComponents.add(searchFieldComponent);
        this.add(searchFieldComponent);
    }


}

