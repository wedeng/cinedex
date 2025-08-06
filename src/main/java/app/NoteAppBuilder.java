// TODO: DELETE THIS FILE AFTER COMPLETING OUR APPLICATION!
package app;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import interface_adapter.app.AppPageController;
import interface_adapter.app.NotePresenter;
import interface_adapter.view.AppViewModel;
import interface_adapter.view.CardViewModel;
import use_case.note.NoteDataAccessInterface;
import use_case.note.NoteInteractor;
import use_case.note.NoteOutputBoundary;
import view.AppView;
import view.CardView;
import view.NavigationMenuView;

/**
 * Builder for the Note Application.
 */
public class NoteAppBuilder {
    public static final int HEIGHT = 300;
    public static final int WIDTH = 400;
    private NoteDataAccessInterface noteDAO;
    private AppViewModel appViewModel = new AppViewModel();
    private CardViewModel cardViewModel = new CardViewModel();
    private AppView appView;
    private NoteInteractor noteInteractor;

    /**
     * Sets the NoteDAO to be used in this application.
     * @param noteDataAccess the DAO to use
     * @return this builder
     */
    public NoteAppBuilder addNoteDAO(NoteDataAccessInterface noteDataAccess) {
        noteDAO = noteDataAccess;
        return this;
    }

    /**
     * Creates the objects for the Note Use Case and connects the NoteView to its
     * controller.
     * <p>This method must be called after addNoteView!</p>
     * @return this builder
     * @throws RuntimeException if this method is called before addNoteView
     */
    public NoteAppBuilder addNoteUseCase() {
        final NoteOutputBoundary noteOutputBoundary = new NotePresenter(appViewModel);
        noteInteractor = new NoteInteractor(
                noteDAO, noteOutputBoundary);

        final AppPageController controller = new AppPageController(noteInteractor);
        if (appView == null) {
            throw new RuntimeException("addNoteView must be called before addNoteUseCase");
        }
        appView.setAppController(controller);
        return this;
    }

    /**
     * Creates the AppView, its component views, and its associated view models.
     * @return this builder
     */
    public NoteAppBuilder addAppView() {
        cardViewModel = new CardViewModel();
        CardView cardView = new CardView(cardViewModel);
        NavigationMenuView navigationMenuView = new NavigationMenuView(cardViewModel);


        appViewModel = new AppViewModel();

//        appView = new AppView(appViewModel, navigationMenuView, cardView);
        return this;
    }

    /**
     * Builds the application.
     * @return the JFrame for the application
     */
    public JFrame build() {
        appView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        appView.setTitle("Movie App");
        appView.setSize(WIDTH, HEIGHT);

        // refresh so that the note will be visible when we start the program
        noteInteractor.executeRefresh();

        return appView;

    }
}
