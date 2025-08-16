package view;

import interface_adapter.search.SearchViewModel;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SearchFieldTextBox extends SearchFieldComponent {

    private static final int TEXT_FIELD_COLUMNS = 20;
    private final JTextField textField;

    SearchFieldTextBox(SearchViewModel searchViewModel, String identifier, String labelName) {
        super(searchViewModel, identifier, labelName);
        this.textField = new JTextField(TEXT_FIELD_COLUMNS);

        addDocumentListenerToTextField();

        this.add(textField);
    }

    private void addDocumentListenerToTextField() {
        final SearchFieldTextBox searchFieldTextBox = this;
        this.textField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                searchFieldTextBox.updateSearchState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchFieldTextBox.updateSearchState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchFieldTextBox.updateSearchState();
            }
        });
    }

    @Override
    protected String getArgument() {
        return textField.getText();
    }
}
