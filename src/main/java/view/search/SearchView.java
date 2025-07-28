package view.search;

import entity.Movie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchView extends JPanel {

    private final JTextField searchField;
    private final JComboBox<String> genreFilter;
    private final JComboBox<Integer> yearFilter;
    private final JComboBox<Double> ratingFilter;

    private final JButton searchButton;
    private final JPanel resultsPanel;

    public SearchView() {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new GridLayout(2, 1));

        // search field
        JPanel searchBarPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // filters
        JPanel filterPanel = new JPanel();

        // Genre filter
        String[] genres = {"All", "Action", "Comedy", "Drama", "Horror", "Fantasy", "Science Fiction", "Sports"};
        genreFilter = new JComboBox<>(genres);

        Integer[] years = new Integer[50];
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = 0; i < years.length; i++) {
            years[i] = currentYear - i;
        }
        yearFilter = new JComboBox<>(years);
        yearFilter.insertItemAt(null, 0);
        yearFilter.setSelectedIndex(0);

        Double[] ratings = {null, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        ratingFilter = new JComboBox<>(ratings);
        ratingFilter.setSelectedIndex(0);

        filterPanel.add(new JLabel("Genre:"));
        filterPanel.add(genreFilter);
        filterPanel.add(new JLabel("Year:"));
        filterPanel.add(yearFilter);
        filterPanel.add(new JLabel("Rating:"));
        filterPanel.add(ratingFilter);

        searchPanel.add(searchBarPanel);
        searchPanel.add(filterPanel);

        // results
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultsPanel);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

    }

    public void addSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public String getSearchQuery() {
        return searchField.getText();
    }

    public String getSelectedGenre() {
        return (String) genreFilter.getSelectedItem();
    }

    public Integer geetSelectedYear() {
        return (Integer) yearFilter.getSelectedItem();
    }

    public Double geetSelectedRating() {
        return (Double) ratingFilter.getSelectedItem();
    }

    public void displayResults(List<Movie> movies) {
        resultsPanel.removeAll();

        if (movies == null || movies.isEmpty()) {
            resultsPanel.add(new JLabel("No results found for this movie query.!"));
        } else {
            for (Movie movie : movies) {
                JPanel moviePanel = new JPanel(new BorderLayout());

                JPanel infoPanel = new JPanel(new BorderLayout());
                JLabel posterLabel = new JLabel(new ImageIcon(movie.getPoster()));
                JLabel titleLabel = new JLabel(movie.getTitle() + " - " + movie.getReleaseDate().getYear() + ")");
                JLabel genreLabel = new JLabel(movie.getGenre());

                JPanel textPanel = new JPanel();
                textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
                textPanel.add(titleLabel);
                textPanel.add(genreLabel);

                infoPanel.add(posterLabel, BorderLayout.WEST);
                infoPanel.add(textPanel, BorderLayout.CENTER);

                // add to watch list
                JButton toWatchButton = new JButton("Add to Watchlist");
                moviePanel.add(infoPanel, BorderLayout.CENTER);
                moviePanel.add(toWatchButton, BorderLayout.EAST);

                resultsPanel.add(moviePanel);
                resultsPanel.add(new JSeparator());
            }
        }
    }

    public void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
