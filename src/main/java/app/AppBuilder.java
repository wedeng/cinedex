package app;

import entity.movie_fields.MovieFieldInterface;
import entity.movie_fields.MovieFieldRegister;
import entity.movie_fields.MovieFieldRegisterInterface;
import interface_adapter.view.CardType;
import interface_adapter.view.MovieDisplayViewModel;
import interface_adapter.view.MovieDisplayViewModels;
import interface_adapter.view.MovieDisplayViewModelsInterface;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.view.CardViewModel;
import interface_adapter.search.SearchViewModel;
import use_case.MovieDataAccessInterface;
import use_case.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import view.AppView;
import view.CardView;
import view.FilterView;
import view.NavigationMenuView;
import view.SearchView;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.util.ArrayList;
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
    private final List<String> searchFields = movieFieldRegister.getSearchFieldNames();

    // DAOs
    private SearchDataAccessInterface searchDataAccessObject;
    private WatchedIdDataAccessInterface watchedIdDataAccessObject;
    private RecommendationDataAccessInterface recommendationDataAccessObject;

    // Views and ViewModels
    private AppView appView;
    private FilterView filterView;
    private ToolBarView toolBarView;
    private final MovieDisplayViewModels movieDisplayViewModels = new MovieDisplayViewModels();

    private SearchView searchView;
    private SearchViewModel searchViewModel;
    private CardViewModel cardViewModel;

    /**
     * Creates the objects for the Search Use Case and connects the SearchView to its
     * controller.
     * <p>This method must be called after addViews!</p>
     * @return this builder
     * @throws RuntimeException if this method is called before addViews
     */
    public AppBuilder addSearchUseCase() {
        final SearchOutputBoundary searchPresenter = new SearchPresenter(movieDisplayViewModels, cardViewModel);

        SearchInteractor searchInteractor = new SearchInteractor(movieFieldRegister, searchPresenter, searchDataAccessObject);

        final SearchController controller = new SearchController(searchInteractor);

        if (searchView == null) {
            throw new RuntimeException("addSearchView must be called before addSearchUseCase");
        }
        searchView.setSearchController(controller);

        return this;
    }

    public AppBuilder addRecommendationUseCase() {
        final RecommendationOutputBoundary recommendationPresenter = new RecommendationPresenter(movieDisplayViewModels
                .getMovieDisplayViewModel(CardType.RECOMMENDED));

        RecommendationInteractor recommendationInteractor = new RecommendationInteractor(
                watchedIdDataAccessObject, recommendationDataAccessObject, recommendationPresenter);

        final RecommendationController recommendationController = new RecommendationController(recommendationInteractor);

        if (searchView == null) {
            throw new RuntimeException("addSearchView must be called before addSearchUseCase");
        }
        toolBarView.setRecommendationController(recommendationController);

        return this;
    }

    /**
     * Adds all the views for this app.
     * @return this builder
     */
    public AppBuilder addViews() {

        // View Models
        addMovieDisplayViewModels();

        // CardViewModel
        cardViewModel = new CardViewModel();

        // SearchView and SearchViewModel
        searchViewModel = new SearchViewModel(searchFields);
        searchView = new SearchView(searchViewModel, cardViewModel, searchFields);

        // FilterView
        List<String> filterFields = new ArrayList<>(searchFields);
        filterFields.remove(0);
        filterView = new FilterView(searchViewModel, filterFields);

        // CardView

        CardView cardView = new CardView(cardViewModel, movieDisplayViewModels, filterView);

        // NavigationMenuView
        NavigationMenuView navigationMenuView = new NavigationMenuView(cardViewModel);

        // ToolBarView
        toolBarView = new ToolBarView(searchView, cardViewModel, movieDisplayViewModels);

        appView = new AppView(navigationMenuView, cardView, searchView, toolBarView);

        return this;
    }

    /**
     * Creates and adds the movie display view models to the collection of movie display models.
     */
    private void addMovieDisplayViewModels() {
        movieDisplayViewModels.addViewModel(new MovieDisplayViewModel(CardType.DISCOVER));
        movieDisplayViewModels.addViewModel(new MovieDisplayViewModel(CardType.SAVED));
        movieDisplayViewModels.addViewModel(new MovieDisplayViewModel(CardType.WATCHED));
        movieDisplayViewModels.addViewModel(new MovieDisplayViewModel(CardType.RECOMMENDED));
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
