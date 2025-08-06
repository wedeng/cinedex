package use_case.saved;

public class SavedOutputData {
    private final boolean isSaved;

    public SavedOutputData(boolean isSaved) {
        this.isSaved = isSaved;
    }

    public boolean isSaved() {
        return this.isSaved;
    }
}
