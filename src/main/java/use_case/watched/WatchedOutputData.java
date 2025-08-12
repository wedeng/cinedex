package use_case.watched;

public class WatchedOutputData {
    private final boolean isWatched;

    public WatchedOutputData(boolean isWatched) {
        this.isWatched = isWatched;
    }

    public boolean isWatched() {
        return this.isWatched;
    }
}
