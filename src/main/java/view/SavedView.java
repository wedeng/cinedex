package view;

import entity.Movie;
import interface_adapter.saved.SavedController;
import interface_adapter.saved.SavedState;
import interface_adapter.saved.SavedViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class SavedView extends JPanel implements ActionListener, PropertyChangeListener {
    
    private final SavedViewModel savedViewModel;
    private SavedController savedController;
    
    // UI Components
    private final JTextField searchField = new JTextField(20);
    private final JButton searchButton = new JButton("Search Movies");
    private final JPanel searchResultsPanel = new JPanel();
    private final JPanel savedMoviesPanel = new JPanel();
    private final JLabel statusLabel = new JLabel("Ready to search");
    
    public SavedView(SavedViewModel savedViewModel) {
        this.savedViewModel = savedViewModel;
        this.savedViewModel.addPropertyChangeListener(this);
        
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search Movies:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        searchButton.addActionListener(this);
        
        // Results Panel with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Search Results Tab
        searchResultsPanel.setLayout(new BoxLayout(searchResultsPanel, BoxLayout.Y_AXIS));
        JScrollPane searchScrollPane = new JScrollPane(searchResultsPanel);
        tabbedPane.addTab("Search Results", searchScrollPane);
        
        // Saved Movies Tab
        savedMoviesPanel.setLayout(new BoxLayout(savedMoviesPanel, BoxLayout.Y_AXIS));
        JScrollPane savedScrollPane = new JScrollPane(savedMoviesPanel);
        tabbedPane.addTab("My Watchlist", savedScrollPane);
        
        // Status Panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(statusLabel);
        
        add(searchPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String query = searchField.getText().trim();
            if (!query.isEmpty() && savedController != null) {
                savedController.searchMovies(query);
            }
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SavedState state = (SavedState) evt.getNewValue();
        
        // Update search results
        displaySearchResults(state.getSearchResults());
        
        // Update saved movies
        displaySavedMovies(state.getSavedMovies());
        
        // Update status
        if (state.getError() != null) {
            statusLabel.setText("Error: " + state.getError());
            JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
        } else if (state.getMessage() != null && !state.getMessage().isEmpty()) {
            statusLabel.setText(state.getMessage());
        }
    }
    
    private void displaySearchResults(List<Movie> movies) {
        searchResultsPanel.removeAll();
        
        if (movies.isEmpty()) {
            searchResultsPanel.add(new JLabel("No movies found. Try searching for 'matrix' or 'action'"));
        } else {
            for (Movie movie : movies) {
                JPanel moviePanel = createMoviePanel(movie, true);
                searchResultsPanel.add(moviePanel);
                searchResultsPanel.add(Box.createVerticalStrut(5));
            }
        }
        
        searchResultsPanel.revalidate();
        searchResultsPanel.repaint();
    }
    
    private void displaySavedMovies(List<Movie> movies) {
        savedMoviesPanel.removeAll();
        
        if (movies.isEmpty()) {
            savedMoviesPanel.add(new JLabel("No saved movies yet. Search and add some movies!"));
        } else {
            for (Movie movie : movies) {
                JPanel moviePanel = createMoviePanel(movie, false);
                savedMoviesPanel.add(moviePanel);
                savedMoviesPanel.add(Box.createVerticalStrut(5));
            }
        }
        
        savedMoviesPanel.revalidate();
        savedMoviesPanel.repaint();
    }
    
    private JPanel createMoviePanel(Movie movie, boolean showAddButton) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        // Movie info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel("<html><b>" + movie.getTitle() + "</b></html>"));
        infoPanel.add(new JLabel("Year: " + movie.getReleaseDate().getYear()));
        infoPanel.add(new JLabel("Genre: " + movie.getGenre()));
        infoPanel.add(new JLabel("Runtime: " + movie.getRuntime() + " min"));
        
        panel.add(infoPanel, BorderLayout.CENTER);
        
        // Add to watchlist button (only for search results)
        if (showAddButton) {
            JButton addButton = new JButton("Add to Watchlist");
            addButton.addActionListener(e -> {
                if (savedController != null) {
                    savedController.addToSavedMovies(movie.getMovieId());
                }
            });
            panel.add(addButton, BorderLayout.EAST);
        }
        
        return panel;
    }
    
    public void setSavedController(SavedController controller) {
        this.savedController = controller;
    }
}