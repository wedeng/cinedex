package app;

import data_access.InMemoryMovieDataAccessObject;
import interface_adapter.saved.*;
import use_case.saved.*;
import view.SavedView;

import javax.swing.*;

public class SavedAppBuilder {
    public static JFrame buildSavedApp() {
        // Data Access
        SavedDataAccessInterface dataAccess = new InMemoryMovieDataAccessObject();
        
        // View Model
        SavedViewModel viewModel = new SavedViewModel();
        
        // Use Case
        SavedOutputBoundary presenter = new SavedPresenter(viewModel);
        SavedInputBoundary interactor = new SavedInteractor(dataAccess, presenter);
        
        // Controller
        SavedController controller = new SavedController(interactor);
        
        // View
        SavedView view = new SavedView(viewModel);
        view.setSavedController(controller);
        
        // Frame
        JFrame frame = new JFrame("Movie Watchlist");
        frame.add(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        return frame;
    }
}