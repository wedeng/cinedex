package interface_adapter.saved;

import interface_adapter.ViewModel;

public class SavedViewModel extends ViewModel<SavedState> {
    public SavedViewModel() {
        super("saved");
        setState(new SavedState());
    }
}