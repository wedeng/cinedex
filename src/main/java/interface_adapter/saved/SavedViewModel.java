package interface_adapter.saved;

import interface_adapter.view.ViewModel;

/**
 * The View Model for the saved View.
 */

public class SavedViewModel extends ViewModel<SavedState> {
    public SavedViewModel() {
        super("saved");
        setState(new SavedState());
    }
}
