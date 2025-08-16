package interface_adapter.view;

import entity.MovieInterface;

import java.util.List;

public class MovieDisplayState {
    private final CardType viewType;
    private List<MovieInterface> displayedMovies;
    private int page = 1;
    private int selectionIndex = -1;
    private String retrievalError;

    public MovieDisplayState(CardType viewType) {
        this.viewType = viewType;
    }

    public CardType getCardType() {
        return viewType;
    }

    public void setDisplayedMovies(List<MovieInterface> displayedMovies) {
        this.displayedMovies = displayedMovies;
        this.selectionIndex = 0;
    }

    public List<MovieInterface> getDisplayedMovies() {
        return displayedMovies;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getRetrievalError() {
        return retrievalError;
    }

    public void setRetrievalError(String retrievalError) {
        this.retrievalError = retrievalError;
    }

    /**
     * Get the index for the current selected movie.
     * Returns -1 if no movie is selected.
     * @return the index
     */
    public int getSelectionIndex() {
        return selectionIndex;
    }

    public void setSelectionIndex(int selectionIndex) {
        this.selectionIndex = selectionIndex;
    }

    public MovieInterface getSelectedMovie() {
        if  (selectionIndex == -1) {
            return null;
        }
        return displayedMovies.get(selectionIndex);
    }
}
