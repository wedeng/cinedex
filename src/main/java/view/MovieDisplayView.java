package view;

import entity.MovieInterface;
import interface_adapter.view.MovieDisplayState;
import interface_adapter.view.MovieDisplayViewModel;

import javax.swing.JPanel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class MovieDisplayView extends JPanel implements PropertyChangeListener {

    private final MovieDisplayViewModel movieDisplayViewModel;

    public MovieDisplayView(MovieDisplayViewModel movieDisplayViewModel) {
        this.movieDisplayViewModel = movieDisplayViewModel;
        this.movieDisplayViewModel.addPropertyChangeListener(this);
    }

    public void setDisplayedMovies(List<MovieInterface> movies) {
        final MovieDisplayState state =  movieDisplayViewModel.getState();
        state.setDisplayedMovies(movies);
        movieDisplayViewModel.setState(state);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final MovieDisplayState state = (MovieDisplayState) evt.getNewValue();
        setFields(state);
    }

    private void setFields(MovieDisplayState state) {
        this.removeAll();
        for (MovieInterface movie : state.getDisplayedMovies()) {
            this.add(new MovieComponent(movie));
        }
    }
}
