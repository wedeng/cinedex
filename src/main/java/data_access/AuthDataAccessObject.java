package data_access;

import use_case.authentication.AuthDataAccessInterface;
import use_case.authentication.AuthException;
import entity.Movie;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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

/**
 * Data access object for handling TMDB authentication and data sync.
 * Implements the API calls for auth and synchronization.
 */
public class AuthDataAccessObject implements AuthDataAccessInterface {

    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3";
    private static final String TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private final String apiKey;
    private final OkHttpClient client;

    public AuthDataAccessObject(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient().newBuilder().build();
    }

    @Override
    public String createRequestToken() throws AuthException {
        try {
            String endpoint = TMDB_BASE_URL + "/authentication/token/new";
            String response = makeApiRequest(endpoint, "GET", null);

            JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.getBoolean("success")) {
                return jsonResponse.getString("request_token");
            } else {
                throw new AuthException("Could not generate a new request token.");
            }
        } catch (IOException | JSONException e) {
            throw new AuthException("An error occurred while generating request token: " + e.getMessage(), e);
        }
    }

    @Override
    public String createSession(String requestToken) throws AuthException {
        try {
            String endpoint = TMDB_BASE_URL + "/authentication/session/new";
            JSONObject requestBody = new JSONObject();
            requestBody.put("request_token", requestToken);

            String response = makeApiRequest(endpoint, "POST", requestBody.toString());

            JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.getBoolean("success")) {
                return jsonResponse.getString("session_id");
            } else {
                throw new AuthException("Could not create a session with the provided token.");
            }
        } catch (IOException | JSONException e) {
            throw new AuthException("An error occurred while creating session: " + e.getMessage(), e);
        }
    }

    @Override
    public int getAccountId(String sessionId) throws AuthException {
        try {
            String endpoint = TMDB_BASE_URL + "/account";
            String response = makeApiRequestWithSession(endpoint, "GET", null, sessionId);

            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getInt("id");
        } catch (IOException | JSONException e) {
            throw new AuthException("Could not retrieve account ID: " + e.getMessage(), e);
        }
    }

    @Override
    public String getUsername(String sessionId) throws AuthException {
        try {
            String endpoint = TMDB_BASE_URL + "/account";
            String response = makeApiRequestWithSession(endpoint, "GET", null, sessionId);

            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getString("username");
        } catch (IOException | JSONException e) {
            throw new AuthException("Could not retrieve username: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Integer> getSavedMovies(String sessionId) throws AuthException {
        try {
            List<Integer> savedMovies = new ArrayList<>();
            int page = 1;
            boolean hasMorePages = true;

            while (hasMorePages) {
                String endpoint = String.format("%s/account/{account_id}/watchlist/movies?page=%d", TMDB_BASE_URL, page);
                String response = makeApiRequestWithSession(endpoint, "GET", null, sessionId);

                JSONObject jsonResponse = new JSONObject(response);
                JSONArray results = jsonResponse.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject movie = results.getJSONObject(i);
                    savedMovies.add(movie.getInt("id"));
                }

                hasMorePages = page < jsonResponse.getInt("total_pages");
                page++;
            }

            return savedMovies;
        } catch (IOException | JSONException e) {
            throw new AuthException("An error occurred while fetching saved movies: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<Integer, Integer> getRatedMovies(String sessionId) throws AuthException {
        try {
            Map<Integer, Integer> ratedMovies = new HashMap<>();
            int page = 1;
            boolean hasMorePages = true;

            while (hasMorePages) {
                String endpoint = String.format("%s/account/{account_id}/rated/movies?page=%d", TMDB_BASE_URL, page);
                String response = makeApiRequestWithSession(endpoint, "GET", null, sessionId);

                JSONObject jsonResponse = new JSONObject(response);
                JSONArray results = jsonResponse.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject movie = results.getJSONObject(i);
                    ratedMovies.put(movie.getInt("id"), movie.getInt("rating"));
                }

                hasMorePages = page < jsonResponse.getInt("total_pages");
                page++;
            }

            return ratedMovies;
        } catch (IOException | JSONException e) {
            throw new AuthException("An error occurred while fetching rated movies: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getPreferredGenres(String sessionId) throws AuthException {
        try {
            Map<Integer, Integer> ratedMovies = getRatedMovies(sessionId);
            Map<String, Integer> genreCounts = new HashMap<>();

            for (Integer movieId : ratedMovies.keySet()) {
                Movie movie = getMovieDetails(movieId);
                if (movie != null) {
                    String genre = movie.getGenre();
                    genreCounts.put(genre, genreCounts.getOrDefault(genre, 0) + 1);
                }
            }

            return genreCounts.entrySet().stream()
                    .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                    .limit(3)
                    .map(Map.Entry::getKey)
                    .collect(java.util.stream.Collectors.toList());
        } catch (AuthException e) {
            throw e; // preserve specific auth errors
        } catch (Exception e) {
            throw new AuthException("An error occurred while determining preferred genres: " + e.getMessage(), e);
        }
    }

    @Override
    public Movie getMovieDetails(int movieId) throws AuthException {
        try {
            String endpoint = String.format("%s/movie/%d", TMDB_BASE_URL, movieId);
            String response = makeApiRequest(endpoint, "GET", null);

            JSONObject movieJson = new JSONObject(response);
            return convertTMDBJsonToMovie(movieJson);
        } catch (IOException | JSONException e) {
            throw new AuthException("An error occurred while retrieving movie details: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateSavedMovies(String sessionId, List<Integer> movieIds) throws AuthException {
        try {
            for (Integer movieId : movieIds) {
                String endpoint = String.format("%s/account/{account_id}/watchlist", TMDB_BASE_URL);
                JSONObject requestBody = new JSONObject();
                requestBody.put("media_type", "movie");
                requestBody.put("media_id", movieId);
                requestBody.put("watchlist", true);

                makeApiRequestWithSession(endpoint, "POST", requestBody.toString(), sessionId);
            }
        } catch (IOException | JSONException e) {
            throw new AuthException("An error occurred while updating saved movies: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateRatedMovies(String sessionId, Map<Integer, Integer> ratedMovies) throws AuthException {
        try {
            for (Map.Entry<Integer, Integer> entry : ratedMovies.entrySet()) {
                String endpoint = String.format("%s/movie/%d/rating", TMDB_BASE_URL, entry.getKey());
                JSONObject requestBody = new JSONObject();
                requestBody.put("value", entry.getValue());

                makeApiRequestWithSession(endpoint, "POST", requestBody.toString(), sessionId);
            }
        } catch (IOException | JSONException e) {
            throw new AuthException("An error occurred while updating rated movies: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteSession(String sessionId) throws AuthException {
        try {
            String endpoint = TMDB_BASE_URL + "/authentication/session";
            JSONObject requestBody = new JSONObject();
            requestBody.put("session_id", sessionId);

            String response = makeApiRequest(endpoint, "DELETE", requestBody.toString());

            JSONObject jsonResponse = new JSONObject(response);
            if (!jsonResponse.getBoolean("success")) {
                throw new AuthException("Could not delete the session.");
            }
        } catch (IOException | JSONException e) {
            throw new AuthException("An error occurred while deleting the session: " + e.getMessage(), e);
        }
    }

    /**
     * Makes a basic API request to TMDB.
     */
    private String makeApiRequest(String endpoint, String method, String requestBody) throws IOException {
        String url = endpoint + "?api_key=" + apiKey;
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if (requestBody != null && ("POST".equals(method) || "DELETE".equals(method))) {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody);
            requestBuilder.method(method, body);
        } else {
            requestBuilder.get();
        }

        requestBuilder.addHeader("Content-Type", "application/json");
        Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            }
            throw new IOException("Received empty response from TMDB API.");
        }
    }

    /**
     * Makes an API request to TMDB with session authentication.
     */
    private String makeApiRequestWithSession(String endpoint, String method, String requestBody, String sessionId) throws IOException {
        String url = endpoint + "?api_key=" + apiKey + "&session_id=" + sessionId;
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if (requestBody != null && ("POST".equals(method) || "DELETE".equals(method))) {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody);
            requestBuilder.method(method, body);
        } else {
            requestBuilder.get();
        }

        requestBuilder.addHeader("Content-Type", "application/json");
        Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            }
            throw new IOException("Received empty response from TMDB API.");
        }
    }

    /**
     * Converts TMDB JSON response to Movie entity.
     */
    private Movie convertTMDBJsonToMovie(JSONObject movieJson) throws JSONException {
        int movieId = movieJson.getInt("id");
        String title = movieJson.optString("title", "");

        LocalDate releaseDate;
        String releaseDateStr = movieJson.optString("release_date", "");
        if (!releaseDateStr.isEmpty()) {
            releaseDate = LocalDate.parse(releaseDateStr);
        } else {
            releaseDate = LocalDate.now();
        }

        String posterPath = movieJson.optString("poster_path", "");
        String poster = posterPath.isEmpty() ? null : TMDB_IMAGE_BASE_URL + posterPath;

        String synopsis = movieJson.optString("overview", "");
        int runtime = movieJson.optInt("runtime", 0);

        String genre = "";
        JSONArray genresArray = movieJson.optJSONArray("genres");
        if (genresArray != null && genresArray.length() > 0) {
            genre = genresArray.getJSONObject(0).optString("name", "");
        }

        String language = movieJson.optString("original_language", "").toUpperCase();

        double rentPrice = 3.99;
        double buyPrice = 14.99;

        return new Movie(movieId, title, releaseDate, poster, synopsis, runtime, genre, language, rentPrice, buyPrice);
    }
}
