package use_case.search;

public class SearchInputData {
    private final String query;
    private final String genre;
    private final Integer year;
    private final Double minRating;

    public SearchInputData(String query, String genre, Integer year, Double minRating) {
        this.query = query;
        this.genre = genre;
        this.year = year;
        this.minRating = minRating;
    }

    public String getQuery() {
        return query;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getYear() {
        return year;
    }

    public Double getMinRating() {
        return minRating;
    }
}
