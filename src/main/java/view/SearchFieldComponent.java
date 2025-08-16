package view;

import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;

abstract class SearchFieldComponent extends JPanel {

    private final SearchViewModel searchViewModel;
    private final String identifier;
    private final JLabel label;

    private final BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);

    SearchFieldComponent(SearchViewModel searchViewModel, String identifier, String labelName) {
        this.searchViewModel = searchViewModel;
        this.identifier = identifier;
        this.label = new JLabel(labelName);

        this.setLayout(layout);

        this.add(label);
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getLabelName() {
        return label.getText();
    }

    protected abstract String getArgument();

    protected void updateSearchState() {
        final SearchState currentState = searchViewModel.getState();
        currentState.setSearchArgument(getIdentifier(), getArgument());
        searchViewModel.setState(currentState);

        System.out.println("updateSearchState() called in " + this.getClass().getName());
    }
}
