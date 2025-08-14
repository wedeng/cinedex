package data_access;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import entity.Movie;
import entity.MovieInterface;
import use_case.authentication.AuthenticationException;
import use_case.authentication.OperationsDataAccessInterface;
import use_case.recommendation.RecommendationDataAccessInterface;
import use_case.recommendation.WatchedIdDataAccessInterface;
import use_case.saved.SavedMovieCheckerDataAccessInterface;
import use_case.saved.SavedMovieManagerDataAccessInterface;
import use_case.search.SearchDataAccessInterface;
import use_case.watched.WatchedMovieCheckerDataAccessInterface;
import use_case.watched.WatchedMovieManagerDataAccessInterface;

/**
 * Data access object for handling TMDB authentication/sync and recommendations.
 */

public class CinedexDataAccessObject implements OperationsDataAccessInterface, RecommendationDataAccessInterface,
        WatchedIdDataAccessInterface, SavedMovieCheckerDataAccessInterface, WatchedMovieCheckerDataAccessInterface,
        SavedMovieManagerDataAccessInterface, WatchedMovieManagerDataAccessInterface, SearchDataAccessInterface {
    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3";
    private static final String TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w92";
    private final String apiKey;
    private final OkHttpClient client;

    public CinedexDataAccessObject(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient().newBuilder().build();
    }

    // --- OperationsDataAccessInterface ---
    @Override
    public int getAccountId(String sessionId) throws AuthenticationException {
        try {
            final String endpoint = TMDB_BASE_URL + "/account";
            final String response = makeApiRequestWithSession(endpoint, "GET", null, sessionId);

            final JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getInt("id");
        }
        catch (IOException | JSONException exception) {
            throw new AuthenticationException("Could not retrieve account ID: " + exception.getMessage(), exception);
        }
    }

    @Override
    public String getUsername(String sessionId) throws AuthenticationException {
        try {
            final String endpoint = TMDB_BASE_URL + "/account";
            final String response = makeApiRequestWithSession(endpoint, "GET", null, sessionId);

            final JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getString("username");
        }
        catch (IOException | JSONException exception) {
            throw new AuthenticationException("Could not retrieve username: " + exception.getMessage(), exception);
        }
    }

    @Override
    public List<Integer> getSavedMovies(String sessionId) throws AuthenticationException {
        try {
            final List<Integer> savedMovies = new ArrayList<>();
            int page = 1;
            boolean hasMorePages = true;
            final int accountId = getAccountId(sessionId);

            while (hasMorePages) {
                final String endpoint = String.format("%s/account/%d/watchlist/movies?page=%d",
                        TMDB_BASE_URL, accountId, page);
                final String response = makeApiRequestWithSession(endpoint, "GET", null, sessionId);

                final JSONObject jsonResponse = new JSONObject(response);
                final JSONArray results = jsonResponse.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    final JSONObject movie = results.getJSONObject(i);
                    savedMovies.add(movie.getInt("id"));
                }

                hasMorePages = page < jsonResponse.getInt("total_pages");
                page++;
            }

            return savedMovies;
        }
        catch (IOException | JSONException exception) {
            throw new AuthenticationException(
                    "An error occurred while fetching saved movies: " + exception.getMessage(), exception);
        }
    }

    @Override
    public List<Integer> getWatchedMovies(String sessionId) throws AuthenticationException {
        try {
            final List<Integer> watchedMovies = new ArrayList<>();
            int page = 1;
            boolean hasMorePages = true;
            final int accountId = getAccountId(sessionId);

            while (hasMorePages) {
                final String endpoint = String.format("%s/account/%d/movies?page=%d", TMDB_BASE_URL, accountId, page);
                final String response = makeApiRequestWithSession(endpoint, "GET", null, sessionId);

                final JSONObject jsonResponse = new JSONObject(response);
                final JSONArray results = jsonResponse.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    final JSONObject movie = results.getJSONObject(i);
                    watchedMovies.add(movie.getInt("id"));
                }

                hasMorePages = page < jsonResponse.getInt("total_pages");
                page++;
            }

            return watchedMovies;
        }
        catch (IOException | JSONException exception) {
            throw new AuthenticationException(
                    "An error occurred while fetching watched movies: " + exception.getMessage(), exception);
        }
    }

    @Override
    public MovieInterface getMovieDetails(int movieId) throws AuthenticationException {
        try {
            final String endpoint = String.format("%s/movie/%d", TMDB_BASE_URL, movieId);
            final String response = makeApiRequest(endpoint, "GET", null);

            final JSONObject movieJson = new JSONObject(response);
            return convertJsonToMovie(movieJson);
        }
        catch (IOException | JSONException exception) {
            throw new AuthenticationException(
                    "An error occurred while retrieving movie details: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void updateSavedMovies(String sessionId, List<Integer> movieIds) throws AuthenticationException {
        try {
            final int accountId = getAccountId(sessionId);
            for (Integer movieId : movieIds) {
                final String endpoint = String.format("%s/account/%d/watchlist", TMDB_BASE_URL, accountId);
                final JSONObject requestBody = new JSONObject();
                requestBody.put("media_type", "movie");
                requestBody.put("media_id", movieId);
                requestBody.put("watchlist", true);

                makeApiRequestWithSession(endpoint, "POST", requestBody.toString(), sessionId);
            }
        }
        catch (IOException | JSONException exception) {
            throw new AuthenticationException(
                    "An error occurred while updating saved movies: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void updateWatchedMovies(String sessionId, List<Integer> watchedMovies) throws AuthenticationException {
        try {
            final int accountId = getAccountId(sessionId);
            for (Integer movieId : watchedMovies) {
                // Add to favorites (TMDB's closest equivalent to "watched")
                final String endpoint = String.format("%s/account/%d/favorite", TMDB_BASE_URL, accountId);
                final JSONObject requestBody = new JSONObject();
                requestBody.put("media_type", "movie");
                requestBody.put("media_id", movieId);
                requestBody.put("favorite", true);

                makeApiRequestWithSession(endpoint, "POST", requestBody.toString(), sessionId);
            }
        }
        catch (IOException | JSONException exception) {
            throw new AuthenticationException(
                    "An error occurred while updating watched movies: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void deleteSession(String sessionId) throws AuthenticationException {
        try {
            final String endpoint = TMDB_BASE_URL + "/authentication/session";
            final JSONObject requestBody = new JSONObject();
            requestBody.put("session_id", sessionId);

            final String response = makeApiRequest(endpoint, "DELETE", requestBody.toString());

            final JSONObject jsonResponse = new JSONObject(response);

            if (!jsonResponse.getBoolean("success")) {
                throw new AuthenticationException("Could not delete the session.");
            }
        }
        catch (IOException | JSONException exception) {
            throw new AuthenticationException(
                    "An error occurred while deleting the session: " + exception.getMessage(), exception);
        }
    }

    // --- RecommendationDataAccessInterface ---

    @Override
    public List<MovieInterface> recommendMovies(int movieId) {
        try {
            final ArrayList<MovieInterface> recommendedMovies = new ArrayList<>();
            final String sessionId = new CinedexMongoDataBase().getCurrentSessionId();
            int page = 1;
            boolean hasMorePages = true;

            while (hasMorePages) {
                final String endpoint = String.format("%s/movie/%d/recommendations?language=en-US&page=1",
                        TMDB_BASE_URL, movieId);
                final String response = makeApiRequestWithSession(endpoint, "GET", null, sessionId);

                final JSONObject jsonResponse = new JSONObject(response);
                final JSONArray results = jsonResponse.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    final JSONObject movie = results.getJSONObject(i);
                    final String backdropPath = movie.getString("backdrop_path");
                    final int movieIdentity = movie.getInt("id");
                    final String movieTitle = movie.getString("title");
                    final String synopsis = movie.getString("overview");
                    final String language = movie.getString("original_language");
                    final String releaseDate = movie.getString("release_date");
                    final LocalDate date = LocalDate.parse(releaseDate);
                    final MovieInterface movieObj = new Movie(movieIdentity, movieTitle, date, backdropPath,
                            synopsis, 115, "Horror", language, 3.99, 14.99);
                    recommendedMovies.add(movieObj);
                }
                hasMorePages = page < jsonResponse.getInt("total_pages");
                page++;

            }
            return recommendedMovies;
        }
        catch (IOException | JSONException exception) {
            return new ArrayList<>();
        }
    }

    // --- WatchedIdDataAccessInterface ---

    @Override
    public List<Integer> getMovieIds() {
        final ArrayList<Integer> movieIds = new ArrayList<>();
        final CinedexMongoDataBase dataBase = new CinedexMongoDataBase();
        final List<MovieInterface> movies = dataBase.getAllWatchedMovies();

        for (int i = 0; i < movies.toArray().length; i++) {
            movieIds.add(movies.get(i).getMovieId());
        }
        return movieIds;
    }

    // --- SavedMovieCheckerDataAccessInterface ---

    @Override
    public boolean checkSavedMovies(MovieInterface movie) {
        final CinedexMongoDataBase dataBase = new CinedexMongoDataBase();
        final List<MovieInterface> movies = dataBase.getAllSavedMovies();

        for (int i = 0; i < movies.toArray().length; i++) {
            if (movies.get(i).getMovieId() == movie.getMovieId()) {
                return false;
            }
        }
        return true;
    }

    // -- SavedMovieManagerDataAccessInterface --

    @Override
    public void addSavedMovie(MovieInterface movie) {
        final CinedexMongoDataBase dataBase = new CinedexMongoDataBase();
        dataBase.saveMovieAsSaved(movie);

    }

    @Override
    public void removeSavedMovie(MovieInterface movie) {
        final CinedexMongoDataBase dataBase = new CinedexMongoDataBase();
        dataBase.deleteMovie(movie.getMovieId());
    }

    // --- WatchedMovieCheckerDataAccessInterface ---

    @Override
    public boolean checkWatchedMovie(MovieInterface movie) {
        final CinedexMongoDataBase dataBase = new CinedexMongoDataBase();
        final List<MovieInterface> movies = dataBase.getAllWatchedMovies();

        for (int i = 0; i < movies.toArray().length; i++) {
            if (movies.get(i).getMovieId() == movie.getMovieId()) {
                return false;
            }
        }
        return true;
    }

    // --- WatchedMovieManagerDataAccessInterface ---

    @Override
    public void addWatchedMovie(MovieInterface movie) {
        final CinedexMongoDataBase dataBase = new CinedexMongoDataBase();
        dataBase.saveMovieAsWatched(movie);
    }

    @Override
    public void removeWatchedMovie(MovieInterface movie) {
        final CinedexMongoDataBase dataBase = new CinedexMongoDataBase();
        dataBase.deleteMovie(movie.getMovieId());
    }

    // --- SearchDataAccessInterface ---
    @Override
    public List<MovieInterface> searchMovies(Map<String, String> searchArguments) {
        try {
            final List<MovieInterface> searchedMovies = new ArrayList<>();
            final JSONArray results;

            // Construct endpoint
            final String endpoint = appendSearchArguments(TMDB_BASE_URL + "/discover/movie", searchArguments);

            // Make API request
            final String response = makeApiRequest(endpoint, "GET", null);

            // Parse response and return
            final JSONObject jsonResponse = new JSONObject(response);
            System.out.println(jsonResponse.toString());
            results = jsonResponse.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                final JSONObject movie = results.getJSONObject(i);
                searchedMovies.add(convertJsonToMovie(movie));
            }

            return searchedMovies;
        }
        catch (IOException | JSONException exception) {
            System.out.println(exception.getMessage());
            return new ArrayList<>();
        }
    }

    // --- SearchDataAccessInterface helper ---

    /**
     * Appends search parameters to the end of the URL endpoint.
     * @param endPoint The endpoint to be appended on to
     * @param searchArguments The search arguments to be appended
     * @return The new endPoint
     */
    private String appendSearchArguments(String endPoint, Map<String, String> searchArguments) {

        endPoint = endPoint + "?";
        String conjunction = "";

        for (Map.Entry<String, String> entry : searchArguments.entrySet()) {

            final String searchField = entry.getKey();
            final String searchArgument = entry.getValue();

            endPoint = endPoint + conjunction + searchField + "=" + searchArgument;
            conjunction = "&";
        }
        return endPoint;
    }

    // --- API helper methods ---

    private String makeApiRequestWithSession(
            String endpoint, String method, String requestBody, String sessionId) throws IOException {
        final String url = endpoint + "?api_key=" + apiKey + "&session_id=" + sessionId;
        final Request.Builder requestBuilder = new Request.Builder().url(url);

        if (requestBody != null && ("POST".equals(method) || "DELETE".equals(method))) {
            final RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody);
            requestBuilder.method(method, body);
        }
        else {
            requestBuilder.get();
        }

        requestBuilder.addHeader("Content-Type", "application/json");
        final Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            }
            throw new IOException("Received empty response from TMDB API.");
        }
    }

    private String makeApiRequest(String endpoint, String method, String requestBody) throws IOException {
        final String url = endpoint + "?api_key=" + apiKey;
        final Request.Builder requestBuilder = new Request.Builder().url(url);

        if (requestBody != null && ("POST".equals(method) || "DELETE".equals(method))) {
            final RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody);
            requestBuilder.method(method, body);
        }
        else {
            requestBuilder.get();
        }

        requestBuilder.addHeader("Content-Type", "application/json");
        final Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            }
            throw new IOException("Received empty response from TMDB API.");
        }
    }

    private MovieInterface convertJsonToMovie(JSONObject movieJson) throws JSONException {
        final int movieId = movieJson.getInt("id");
        final String title = movieJson.optString("title", "");

        final LocalDate releaseDate;
        final String releaseDateStr = movieJson.optString("release_date", "");

        if (!releaseDateStr.isEmpty()) {
            releaseDate = LocalDate.parse(releaseDateStr);
        }
        else {
            releaseDate = LocalDate.now();
        }

        final String posterPath = movieJson.optString("poster_path", "");
        final String poster = posterPath.isEmpty() ? null : TMDB_IMAGE_BASE_URL + posterPath;

        final String synopsis = movieJson.optString("overview", "");
        final int runtime = movieJson.optInt("runtime", 0);

        String genre = "";
        final JSONArray genresArray = movieJson.optJSONArray("genres");
        if (genresArray != null && genresArray.length() > 0) {
            genre = genresArray.getJSONObject(0).optString("name", "");
        }

        final String language = movieJson.optString("original_language", "").toUpperCase();

        final double rentPrice = 3.99;
        final double buyPrice = 14.99;

        return new Movie(movieId, title, releaseDate, poster, synopsis, runtime, genre, language, rentPrice, buyPrice);
    }

}
