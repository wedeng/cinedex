package app;

import data_access.AuthenticationDataAccessObject;
import data_access.CinedexDataAccessObject;
import entity.MovieFieldRegister;
import entity.MovieFieldRegisterInterface;
import interface_adapter.authentication.AuthenticationController;
import interface_adapter.authentication.AuthenticationPresenter;
import interface_adapter.authentication.AuthenticationViewModel;
import interface_adapter.recommendation.RecommendationController;
import interface_adapter.recommendation.RecommendationPresenter;
import interface_adapter.recommendation.RecommendationViewModel;
import interface_adapter.view.CardType;
import interface_adapter.view.MovieDisplayViewModel;
import interface_adapter.view.MovieDisplayViewModels;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.view.CardViewModel;
import interface_adapter.search.SearchViewModel;
import interface_adapter.view.ViewManagerModel;
import use_case.authentication.AuthenticationDataAccessInterface;
import use_case.authentication.AuthenticationInputBoundary;
import use_case.authentication.AuthenticationInteractor;
import use_case.authentication.AuthenticationOutputBoundary;
import use_case.authentication.CinedexMongoDataBaseInterface;
import use_case.authentication.OperationsDataAccessInterface;
import use_case.search.SearchDataAccessInterface;
import use_case.recommendation.RecommendationDataAccessInterface;
import use_case.recommendation.RecommendationInteractor;
import use_case.recommendation.RecommendationOutputBoundary;
import use_case.recommendation.WatchedIdDataAccessInterface;
import use_case.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import view.AppView;
import view.AuthenticationView;
import view.CardView;
import view.FilterView;
import view.NavigationMenuView;
import view.SearchView;
import view.ToolBarView;
import view.ViewManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.CardLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

/**
 Responsible for wiring together (constructing and connecting) all the components of the
 application (use cases, controllers, presenters, views, etc.) according to Clean Architecture.
 */

public class AppBuilder {
    public static final int HEIGHT = 300;
    public static final int WIDTH = 400;

    private final MovieFieldRegisterInterface movieFieldRegister = MovieFieldRegister.getInstance();
    private final List<String> searchFields = movieFieldRegister.getSearchFieldNames();

    // ViewManager and ViewManagerModel, and primary views
    CardLayout cardLayout = new CardLayout();
    private final JPanel views = new JPanel(cardLayout);

    private ViewManagerModel viewManagerModel;

    // DAOs
    private final AuthenticationDataAccessInterface authenticationDataAccessObject;
    private final CinedexMongoDataBaseInterface cinedexMongoDataBaseObject;
    private final OperationsDataAccessInterface operationsDataAccessObject;
    private final SearchDataAccessInterface searchDataAccessObject;
    private final WatchedIdDataAccessInterface watchedIdDataAccessObject;
    private final RecommendationDataAccessInterface recommendationDataAccessObject;

    // Authentication View/ViewModel
    AuthenticationViewModel authenticationViewModel;
    AuthenticationView authenticationView;

    // Views and ViewModels in AppView
    private AppView appView;
    private FilterView filterView;
    private ToolBarView toolBarView;
    private final MovieDisplayViewModels movieDisplayViewModels = new MovieDisplayViewModels();

    private SearchView searchView;
    private SearchViewModel searchViewModel;
    private CardViewModel cardViewModel;

    public AppBuilder(AuthenticationDataAccessInterface authenticationDataAccessObject,
                      CinedexMongoDataBaseInterface cinedexMongoDataBaseObject,
                      OperationsDataAccessInterface operationsDataAccessObject,
                      SearchDataAccessInterface searchDataAccessObject,
                      WatchedIdDataAccessInterface watchedIdDataAccessObject,
                      RecommendationDataAccessInterface recommendationDataAccessObject) {
        this.authenticationDataAccessObject = authenticationDataAccessObject;
        this.operationsDataAccessObject = operationsDataAccessObject;
        this.cinedexMongoDataBaseObject = cinedexMongoDataBaseObject;
        this.searchDataAccessObject = searchDataAccessObject;
        this.watchedIdDataAccessObject = watchedIdDataAccessObject;
        this.recommendationDataAccessObject = recommendationDataAccessObject;
    }

    /**
     * Adds the Authentication use case.
     * <p>This method must be called after addAuthenticationView!</p>
     * @return this builder
     */
    public AppBuilder addAuthenticationUseCase() {
        authenticationViewModel = new AuthenticationViewModel();
        final AuthenticationOutputBoundary authenticationPresenter = new AuthenticationPresenter(authenticationViewModel, viewManagerModel);

        final AuthenticationInputBoundary authenticationInteractor = new AuthenticationInteractor(
                authenticationDataAccessObject,
                operationsDataAccessObject,
                cinedexMongoDataBaseObject,
                authenticationPresenter);

        final AuthenticationController authenticationController =
                new AuthenticationController(authenticationInteractor);

        if (authenticationView == null) {
            throw new RuntimeException("addAuthenticationView must be called before addAuthenticationUseCase");
        }
        authenticationView.setAuthenticationController(authenticationController);

        return this;
    }

    /**
     * Adds the Recommendation use case.
     * @return this builder
     */
    public AppBuilder addRecommendationUseCase() {
        final RecommendationViewModel recommendationViewModel = new RecommendationViewModel();
        final RecommendationOutputBoundary recommendationPresenter = new RecommendationPresenter(
                recommendationViewModel);

        final RecommendationInteractor recommendationInteractor = new RecommendationInteractor(
                watchedIdDataAccessObject, recommendationDataAccessObject, recommendationPresenter);

        final RecommendationController recommendationController = new RecommendationController(
                recommendationInteractor);

        if (searchView == null) {
            throw new RuntimeException("addSearchView must be called before addSearchUseCase");
        }

        toolBarView.setRecommendationController(recommendationController);

        return this;
    }

    /**
     * Creates the objects for the Search Use Case and connects the SearchView to its
     * controller.
     * <p>This method must be called after addAppView!</p>
     * @return this builder
     * @throws RuntimeException if this method is called before addAppView
     */
    public AppBuilder addSearchUseCase() {
        final SearchOutputBoundary searchPresenter = new SearchPresenter(movieDisplayViewModels, cardViewModel);

        SearchInteractor searchInteractor = new SearchInteractor(searchPresenter, searchDataAccessObject);

        final SearchController controller = new SearchController(searchInteractor);

        if (searchView == null) {
            throw new RuntimeException("addSearchView must be called before addSearchUseCase");
        }
        searchView.setSearchController(controller);

        return this;
    }

    /**
     * Adds the AuthenticationView and underlying AuthenticationViewModel for this app.
     * @return this builder
     */
    public AppBuilder addAuthenticationView() {
        authenticationViewModel = new AuthenticationViewModel();
        authenticationView = new AuthenticationView(authenticationViewModel);

        // Add to views
        views.add(authenticationView, authenticationView.getViewName());

        return this;
    }

    /**
     * Adds the view manager and underlying view manager model for this app.
     * @return this builder
     */
    public AppBuilder addViewManager() {
        viewManagerModel = new ViewManagerModel();

        views.setLayout(cardLayout);
        final ViewManager viewManager = new ViewManager(views, cardLayout, viewManagerModel);

        return this;
    }

    /**
     * Adds the primary view, AppView, for this app.
     * @return this builder
     */
    public AppBuilder addAppView() {

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

        // Initialize AppView
        appView = new AppView(navigationMenuView, cardView, searchView, toolBarView);

        // Add to views
        views.add(appView, appView.getViewName());

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
        final JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Cinedex");
        frame.setSize(WIDTH, HEIGHT);

        frame.add(views);

        // Set the current view to AuthenticationView
        viewManagerModel.setState(authenticationViewModel.getViewName());
        viewManagerModel.firePropertyChanged("state");

        return frame;
    }

}