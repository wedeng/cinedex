package use_case.recommendation;

public interface MovieCountService {
    /**
     * Returns the count of the max number of movie recommendations desired by users
     */
    int getMovieCount();
}