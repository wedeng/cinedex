package data_access;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Movie;
import entity.MovieInterface;
import entity.Session;
import entity.SessionInterface;
import entity.User;
import entity.UserInterface;

import use_case.authentication.CinedexMongoDataBaseInterface;


/**
 * Native MongoDB implementation for movie, user, and session data.
 * Handles CRUD operations via HTTP to a MongoDB REST interface.
 */

public class CinedexMongoDataBase implements CinedexMongoDataBaseInterface {
    private static final String MONGODB_URL = "http://localhost:27017";
    private static final String DATABASE = "cinedex";
    private static final String MOVIES_COLL = "movie";
    private static final String USERS_COLL = "users";
    private static final String SESSIONS_COLL = "sessions";

    private final OkHttpClient client;

    public CinedexMongoDataBase() {
        this.client = new OkHttpClient.Builder().build();
    }

    // --- Movie Operations ---

    @Override
    public boolean saveMovieAsSaved(MovieInterface movie) {
        try {
            final JSONObject json = convertMovieToJson(movie);
            json.put("saved", true);
            final String url = String.format("%s/%s/%s", MONGODB_URL, DATABASE, MOVIES_COLL);
            final RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json.toString()
            );
            final Request req = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            final Response res = client.newCall(req).execute();
            if (!res.isSuccessful()) {
                System.err.println("Failed to save movie: HTTP " + res.code());
            }
            return res.isSuccessful();
        }
        catch (IOException | JSONException exception) {
            System.err.println("Error saving movie '" + movie.getTitle() + "': " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean saveMovieAsWatched(MovieInterface movie) {
        try {
            final JSONObject json = convertMovieToJson(movie);
            json.put("saved", false);
            final String url = String.format("%s/%s/%s", MONGODB_URL, DATABASE, MOVIES_COLL);
            final RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json.toString()
            );
            final Request req = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            final Response res = client.newCall(req).execute();
            if (!res.isSuccessful()) {
                System.err.println("Failed to save movie: HTTP " + res.code());
            }
            return res.isSuccessful();
        }
        catch (IOException | JSONException exception) {
            System.err.println("Error saving movie '" + movie.getTitle() + "': " + exception.getMessage());
            return false;
        }
    }

    @Override
    public MovieInterface getMovie(int movieId) {
        try {
            final String url = String.format("%s/%s/%s/%d", MONGODB_URL, DATABASE, MOVIES_COLL, movieId);
            final Request req = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();

            final Response res = client.newCall(req).execute();
            if (res.isSuccessful() && res.body() != null) {
                final JSONObject json = new JSONObject(res.body().string());
                return convertJsonToMovie(json);
            }
            else {
                System.err.println("Movie not found or HTTP " + res.code() + " for ID " + movieId);
            }
        }
        catch (IOException | JSONException exception) {
            System.err.println("Error retrieving movie ID " + movieId + ": " + exception.getMessage());
        }
        return null;
    }

    @Override
    public List<MovieInterface> getAllSavedMovies() {
        final List<MovieInterface> list = new ArrayList<>();
        try {
            final String url = String.format("%s/%s/%s", MONGODB_URL, DATABASE, MOVIES_COLL);
            final Request req = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();

            final Response res = client.newCall(req).execute();
            if (res.isSuccessful() && res.body() != null) {
                final JSONArray arr = new JSONArray(res.body().string());
                for (int i = 0; i < arr.length(); i++) {
                    if (arr.getJSONObject(i).getString("saved").equals(true)) {
                        list.add(convertJsonToMovie(arr.getJSONObject(i)));
                    }
                }
            }
            else {
                System.err.println("Failed to fetch movies: HTTP " + res.code());
            }
        }
        catch (IOException | JSONException exception) {
            System.err.println("Error fetching all movies: " + exception.getMessage());
        }
        return list;
    }

    @Override
    public List<MovieInterface> getAllWatchedMovies() {
        final List<MovieInterface> list = new ArrayList<>();
        try {
            final String url = String.format("%s/%s/%s", MONGODB_URL, DATABASE, MOVIES_COLL);
            final Request req = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();

            final Response res = client.newCall(req).execute();
            if (res.isSuccessful() && res.body() != null) {
                final JSONArray arr = new JSONArray(res.body().string());
                for (int i = 0; i < arr.length(); i++) {
                    if (arr.getJSONObject(i).getString("saved").equals(false)) {
                        list.add(convertJsonToMovie(arr.getJSONObject(i)));
                    }
                }
            }
            else {
                System.err.println("Failed to fetch movies: HTTP " + res.code());
            }
        }
        catch (IOException | JSONException exception) {
            System.err.println("Error fetching all movies: " + exception.getMessage());
        }
        return list;

    }

    @Override
    public boolean deleteMovie(int movieId) {
        try {
            final String url = String.format("%s/%s/%s/%d", MONGODB_URL, DATABASE, MOVIES_COLL, movieId);
            final Request req = new Request.Builder()
                    .url(url)
                    .delete()
                    .addHeader("Content-Type", "application/json")
                    .build();

            final Response res = client.newCall(req).execute();
            if (!res.isSuccessful()) {
                System.err.println("Failed to delete movie " + movieId + ": HTTP " + res.code());
            }
            return res.isSuccessful();
        }
        catch (IOException exception) {
            System.err.println("Error deleting movie ID " + movieId + ": " + exception.getMessage());
            return false;
        }
    }

    // --- Session Operations ---

    @Override
    public boolean saveSession(SessionInterface session) {
        try {
            final JSONObject json = convertSessionToJson(session);
            final String url = String.format("%s/%s/%s", MONGODB_URL, DATABASE, SESSIONS_COLL);
            final RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json.toString()
            );
            final Request req = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            final Response res = client.newCall(req).execute();
            if (!res.isSuccessful()) {
                System.err.println("Failed to save session: HTTP " + res.code());
            }
            return res.isSuccessful();
        }
        catch (IOException | JSONException exception) {
            System.err.println("Error saving session '" + session.getSessionId() + "': " + exception.getMessage());
            return false;
        }

    }

    @Override
    public SessionInterface getCurrentSession() {
        try {
            final String url = String.format("%s/%s/%s/current", MONGODB_URL, DATABASE, SESSIONS_COLL);
            final Request req = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();

            final Response res = client.newCall(req).execute();
            if (res.isSuccessful() && res.body() != null) {
                return convertJsonToSession(new JSONObject(res.body().string()));
            }
            else {
                System.err.println("No active session found or HTTP " + res.code());
            }
        }
        catch (IOException | JSONException e) {
            System.err.println("Error fetching current session: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String getCurrentSessionId() {
        final SessionInterface currentSession = getCurrentSession();
        if (currentSession != null) {
            return currentSession.getSessionId();
        }
        return null;
    }

    // --- User Operations ---

    @Override
    public boolean saveUser(User user) {
        try {
            final JSONObject json = convertUserToJson(user);
            final String url = String.format("%s/%s/%s", MONGODB_URL, DATABASE, USERS_COLL);
            final RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json.toString()
            );
            final Request req = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            final Response res = client.newCall(req).execute();
            if (!res.isSuccessful()) {
                System.err.println("Failed to save user: HTTP " + res.code());
            }
            return res.isSuccessful();
        }
        catch (IOException | JSONException e) {
            System.err.println("Error saving user '" + user.getUsername() + "': " + e.getMessage());
            return false;
        }
    }

    @Override
    public UserInterface getUser(int accountId) {
        try {
            final String url = String.format("%s/%s/%s/%d", MONGODB_URL, DATABASE, USERS_COLL, accountId);
            final Request req = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();

            final Response res = client.newCall(req).execute();
            if (res.isSuccessful() && res.body() != null) {
                return convertJsonToUser(new JSONObject(res.body().string()));
            }
            else {
                System.err.println("User not found or HTTP " + res.code() + " for account " + accountId);
            }
        }
        catch (IOException | JSONException exception) {
            System.err.println("Error retrieving user " + accountId + ": " + exception.getMessage());
        }
        return null;
    }

    // --- JSON Conversion Helpers ---
    private JSONObject convertMovieToJson(MovieInterface movie) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("movieId", movie.getMovieId());
        jsonObject.put("title", movie.getTitle());
        jsonObject.put("releaseDate", movie.getReleaseDate().toString());
        jsonObject.put("poster", movie.getPoster());
        jsonObject.put("synopsis", movie.getSynopsis());
        jsonObject.put("runtime", movie.getRuntime());
        jsonObject.put("genre", movie.getGenre());
        jsonObject.put("language", movie.getLanguage());
        jsonObject.put("rentPrice", movie.getRentPrice());
        jsonObject.put("buyPrice", movie.getBuyPrice());
        return jsonObject;
    }

    private MovieInterface convertJsonToMovie(JSONObject json) throws JSONException {
        final int id = json.getInt("movieId");
        final String title = json.getString("title");
        final LocalDate date = LocalDate.parse(json.getString("releaseDate"));
        final String poster = json.optString("poster", null);
        final String synopsis = json.optString("synopsis", "");
        final int runtime = json.optInt("runtime", 0);
        final String genre = json.optString("genre", "");
        final String lang = json.optString("language", "");
        final double rent = json.optDouble("rentPrice", 0.0);
        final double buy = json.optDouble("buyPrice", 0.0);
        return new Movie(id, title, date, poster, synopsis, runtime, genre, lang, rent, buy);
    }

    private JSONObject convertSessionToJson(SessionInterface sessionInterface) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionId", sessionInterface.getSessionId());
        jsonObject.put("accountId", sessionInterface.getAccountId());
        jsonObject.put("createdAt", sessionInterface.getCreatedAt().toString());
        jsonObject.put("expiresAt", sessionInterface.getExpiresAt().toString());
        return jsonObject;
    }

    private Session convertJsonToSession(JSONObject json) throws JSONException {
        final String sid = json.getString("sessionId");
        final int aid = json.getInt("accountId");
        final LocalDateTime created = LocalDateTime.parse(json.getString("createdAt"));
        final LocalDateTime expires = LocalDateTime.parse(json.getString("expiresAt"));
        final Session s = new Session(sid, aid);
        s.setCreatedAt(created);
        s.setExpiresAt(expires);
        return s;
    }

    private JSONObject convertUserToJson(UserInterface u) throws JSONException {
        final JSONObject j = new JSONObject();
        j.put("accountId", u.getAccountId());
        j.put("username", u.getUsername());
        return j;
    }

    private User convertJsonToUser(JSONObject json) throws JSONException {
        final int id = json.getInt("accountId");
        final String name = json.getString("username");

        return new User(id, name);
    }
}
