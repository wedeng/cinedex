package view;

import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;

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
    private final List<SearchFieldView> searchFields = new ArrayList<>();

    public FilterView(SearchViewModel searchViewModel, List<String> fieldNames) {
        super();
        this.searchViewModel = searchViewModel;

        for (String fieldName : fieldNames) {
            SearchFieldView searchField = new SearchFieldView(fieldName);

            searchFields.add(searchField);
            this.add(searchField);

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



    private class SearchFieldView extends JPanel {
        private BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        JTextField textField = new JTextField();

        private final String fieldName;

        public SearchFieldView(String fieldName) {
            super();
            this.fieldName = fieldName;

            this.add(new JLabel(this.fieldName));
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

