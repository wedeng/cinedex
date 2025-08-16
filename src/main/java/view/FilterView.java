package view;

import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.List;

public class FilterView extends JPanel {

    private final SearchViewModel searchViewModel;
    private final BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
    private final List<SearchFieldComponent> searchFields = new ArrayList<>();

    public FilterView(SearchViewModel searchViewModel, List<String> filterFields) {
        super();
        this.searchViewModel = searchViewModel;
        this.setLayout(layout);

        for (String fieldName : filterFields) {
            SearchFieldComponent searchField = new SearchFieldComponent(fieldName);

            this.addSearchField(searchField);

            searchField.getDocument().addDocumentListener(new DocumentListener() {

                private void documentListenerHelper() {
                    final SearchState currentState = searchViewModel.getState();
                    currentState.setSearchArgument(searchField.getFieldName(), searchField.getContents());
                    searchViewModel.setState(currentState);
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    documentListenerHelper();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    documentListenerHelper();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    documentListenerHelper();
                }
            });
        }
    }

    private void addSearchField(SearchFieldComponent searchField) {
        this.searchFields.add(searchField);
        this.add(searchField);
    }

    private class SearchFieldComponent extends JPanel {
        private BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        private final JLabel nameLabel;
        private final JTextField textField = new JTextField(20);

        private final String fieldName;

        public SearchFieldComponent(String fieldName) {
            super();
            this.fieldName = fieldName;
            this.nameLabel = new JLabel(this.fieldName);
            this.setLayout(layout);

            // Style
            this.setBorder(BorderFactory.createLoweredBevelBorder());

            this.add(nameLabel);
            this.add(textField);
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getContents() {
            return textField.getText();
        }

        public Document getDocument() {
            return textField.getDocument();
        }
    }
}

