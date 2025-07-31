package interface_adapter.app;

import use_case.note.NoteOutputBoundary;

/**
 * The presenter for our Note viewing and editing program.
 */
public class NotePresenter implements NoteOutputBoundary {

    private final AppViewModel appViewModel;

    public NotePresenter(AppViewModel appViewModel) {
        this.appViewModel = appViewModel;
    }

    /**
     * Prepares the success view for the Note related Use Cases.
     *
     * @param note the output data
     */
    @Override
    public void prepareSuccessView(String note) {
//     appViewModel.getState().setNote(note);
        appViewModel.getState().setError(null);
        appViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the Note related Use Cases.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        appViewModel.getState().setError(errorMessage);
        // appViewModel.firePropertyChanged();
    }
}
