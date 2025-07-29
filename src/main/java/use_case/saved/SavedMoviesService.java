package use_case.saved;

import entity.Movie;
import java.util.List;

public interface SavedMoviesService {
    List<Movie> getSavedMovies(int userId);
    void addToSavedMovies(int userId, int movieId);
    void removeFromSavedMovies(int userId, int movieId);
    boolean isMovieSaved(int userId, int movieId);
}
