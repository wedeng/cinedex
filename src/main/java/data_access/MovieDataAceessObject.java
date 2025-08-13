package data_access;

import entity.Movie;
import entity.MovieInterface;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.search.MovieSearchService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MovieDataAceessObject implements MovieSearchService {

    private static final String API_KEY = "";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public List<MovieInterface> searchMovies(String query, String genre, Integer year, Double minRating) {
        List<MovieInterface> movies = new ArrayList<>();

        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "/search/movie").newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .addQueryParameter("query", query);

            if (genre != null && !genre.equals("All")) {
                urlBuilder.addQueryParameter("with_genres", getGenreId(genre));
            }
            if (year != null) {
                urlBuilder.addQueryParameter("year", year.toString());
            }
            if (minRating != null) {
                urlBuilder.addQueryParameter("vote_average.gte", minRating.toString());
            }

            String url = urlBuilder.build().toString();

            Request request = new Request.Builder().url(url).build();

            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject movieJson = results.getJSONObject(i);

                int id = movieJson.getInt("id");
                String title = movieJson.getString("title");
                String releaseDateStr = movieJson.getString("release_date");
                LocalDate releaseDate = LocalDate.parse(releaseDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String posterPath = movieJson.getString("poster_path");
                String posterUrl = posterPath.isEmpty() ? "https://image.tmdb.org/t/p/w342" : posterPath;
                String overview = movieJson.getString("overview");
                Double rating = movieJson.optDouble("vote_average", 0);

                MovieInterface movie = new Movie(
                        id,
                        title,
                        releaseDate,
                        posterUrl,
                        overview,
                        0, // need api call
                        getGenreName(movieJson.getJSONArray("genre_ids").getInt(0)),
                        "English",
                        calculateRentPrice(rating),
                        calculateBuyPrice(rating)
                );
                movies.add(movie);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }

    private double calculateBuyPrice(Double rating) {
        // can replace this with other model
        return 2.0 * (rating / 10 * 3);
    }

    private double calculateRentPrice(Double rating) {
        return 10.0 * (rating / 10 * 9);
    }

    /**
     * Map TMDB genre IDs to names
     * @param genreIds TMDB genre IDs
     * @return genre name
     */
    private String getGenreName(int genreIds) {
        switch (genreIds) {
            case 28:
                return "Fantasy";
            case 29:
                return "Science Fiction";
            case 30:
                return "Action";
            case 31:
                return "Comedy";
            case 32:
                return "Drama";
            case 33:
                return "Horror";
            case 34:
                return "Sports";
            default:
                return "";
        }
    }

    /**
     * Map TMDB genre names to IDs
     * @param genre TMDB genre
     * @return id
     */
    private String getGenreId(String genre) {
        switch (genre) {
            case "Fantasy": return "28";
            case "Science Fiction": return "29";
            case "Action": return "30";
            case "Comedy": return "31";
            case "Drama": return "32";
            case "Horror": return "33";
            case "Sports": return "34";
            default: return "";
        }
    }
}
