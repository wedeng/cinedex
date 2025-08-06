package app;

import entity.movie_fields.MovieFieldInterface;
import entity.movie_fields.MovieFieldRegister;
import entity.movie_fields.MovieFieldRegisterInterface;
import interface_adapter.MovieDisplayViewModels;
import interface_adapter.MovieDisplayViewModelsInterface;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.view.CardViewModel;
import interface_adapter.view.SearchViewModel;
import use_case.MovieDataAccessInterface;
import use_case.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import view.AppView;
import view.CardView;
import view.NavigationMenuView;
import view.SearchView;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.util.List;
import java.util.stream.Collectors;

/**
 Responsible for wiring together (constructing and connecting) all the components of the
 application (use cases, controllers, presenters, views, etc.) according to Clean Architecture.
*/ 

public class AppBuilder {
    public static final int HEIGHT = 300;
    public static final int WIDTH = 400;

    private final MovieFieldRegisterInterface movieFieldRegister = MovieFieldRegister.getInstance();
    private List<String> searchFields;

    private MovieDataAccessInterface movieDataAccessObject;

    // Views and ViewModels
    private AppView appView;
    private final MovieDisplayViewModelsInterface movieDisplayViewModels = new MovieDisplayViewModels();

    private SearchView searchView;
    private SearchViewModel searchViewModel;
    private CardViewModel cardViewModel;

    public AppBuilder() {
        // Get searchFields from movieFieldRegister
        searchFields = movieFieldRegister.getSearchFields()
                .stream()
                .map(MovieFieldInterface::getName)
                .collect(Collectors.toList());
    }

    /**
     * Creates the objects for the Search Use Case and connects the SearchView to its
     * controller.
     * <p>This method must be called after addViews!</p>
     * @return this builder
     * @throws RuntimeException if this method is called before addViews
     */
    public AppBuilder addSearchUseCase() {
        final SearchOutputBoundary searchPresenter = new SearchPresenter(movieDisplayViewModels, cardViewModel);

        SearchInteractor searchInteractor = new SearchInteractor(movieFieldRegister, searchPresenter, movieDataAccessObject);

        final SearchController controller = new SearchController(searchInteractor);

        if (searchView == null) {
            throw new RuntimeException("addSearchView must be called before addSearchUseCase");
        }
        searchView.setSearchController(controller);
        return this;
    }

    /**
     * Adds all the views for this app.
     * @return this builder
     */
    public AppBuilder addViews() {

        // SearchView
        searchView = new SearchView(searchViewModel, cardViewModel, searchFields);

        // CardView
        cardViewModel = new CardViewModel();
        CardView cardView = new CardView(cardViewModel);

        // NavigationMenuView
        NavigationMenuView navigationMenuView = new NavigationMenuView(cardViewModel);

        appView = new AppView(navigationMenuView, cardView, searchView);

        return this;
    }

    /**
     * Builds the application.
     * @return the JFrame for the application
     */
    public JFrame build() {
        appView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        appView.setTitle("Cinedex");
        appView.setSize(WIDTH, HEIGHT);

        return appView;
    }

}
