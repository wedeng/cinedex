package view;

import interface_adapter.search.SearchArgument;
import interface_adapter.search.SearchViewModel;
import interface_adapter.view.CardType;

import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchFieldComboBox extends SearchFieldComponent {

    private final JComboBox<String> comboBox;
    private final Map<String, String> labelToIdentifier = new HashMap<>();

    SearchFieldComboBox(SearchViewModel searchViewModel, String identifier, String labelName,
                        List<SearchArgument> searchArguments) {
        super(searchViewModel, identifier, labelName);

        // Create map of label to identifier from searchArguments
        for (SearchArgument searchArgument : searchArguments) {
            labelToIdentifier.put(searchArgument.getDisplayName(), searchArgument.getDisplayName());
        }
        comboBox = new JComboBox<>(searchArguments.stream().map(SearchArgument::getDisplayName).toArray(String[]::new));

        comboBox.addActionListener(
                evt -> {
                    if (evt.getSource().equals(comboBox)) {
                        updateSearchState();
                    }
                }
        );

        comboBox.setEditable(false);
        this.add(comboBox);
    }

    @Override
    protected String getArgument() {
        return labelToIdentifier.get(comboBox.getSelectedItem().toString());
    }
}
