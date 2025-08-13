package data_access;

import entity.Movie;
import entity.MovieInterface;
import entity.AppUser;
import entity.Session;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
 * Native MongoDB implementation for movie, user, and session data.
 * Handles CRUD operations via HTTP to a MongoDB REST interface.
 */
public class MongoMovieDataBase {
    private static final String MONGODB_URL = "http://localhost:27017";
    private static final String DATABASE = "cinedex";
    private static final String MOVIES_COLL = "movies";
    // Add differentiability column 
    private static final String USERS_COLL = "users";
    private static final String SESSIONS_COLL = "sessions";

    private final OkHttpClient client;

    public MongoMovieDataBase() {
        this.client = new OkHttpClient.Builder().build();
    }

    // --- Movie Operations ---

    public boolean saveMovie(MovieInterface movie) {
        try {
            JSONObject json = convertMovieToJson(movie);
            String url = String.format("%s/%s/%s", MONGODB_URL, DATABASE, MOVIES_COLL);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json.toString()
            );
            Request req = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response res = client.newCall(req).execute();
            if (!res.isSuccessful()) {
                System.err.println("Failed to save movie: HTTP " + res.code());
            }
            return res.isSuccessful();
        } catch (IOException | JSONException e) {
            System.err.println("Error saving movie '" + movie.getTitle() + "': " + e.getMessage());
            return false;
        }
    }

    public MovieInterface getMovie(int movieId) {
        try {
            String url = String.format("%s/%s/%s/%d", MONGODB_URL, DATABASE, MOVIES_COLL, movieId);
            Request req = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response res = client.newCall(req).execute();
            if (res.isSuccessful() && res.body() != null) {
                JSONObject json = new JSONObject(res.body().string());
                return convertJsonToMovie(json);
            } else {
                System.err.println("Movie not found or HTTP " + res.code() + " for ID " + movieId);
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error retrieving movie ID " + movieId + ": " + e.getMessage());
        }
        return null;
    }

    public List<MovieInterface> getAllMovies() {
        List<MovieInterface> list = new ArrayList<>();
        try {
            String url = String.format("%s/%s/%s", MONGODB_URL, DATABASE, MOVIES_COLL);
            Request req = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response res = client.newCall(req).execute();
            if (res.isSuccessful() && res.body() != null) {
                JSONArray arr = new JSONArray(res.body().string());
                for (int i = 0; i < arr.length(); i++) {
                    list.add(convertJsonToMovie(arr.getJSONObject(i)));
                }
            } else {
                System.err.println("Failed to fetch movies: HTTP " + res.code());
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error fetching all movies: " + e.getMessage());
        }
        return list;
    }

    public List<MovieInterface> searchMoviesByTitle(String title) {
        List<MovieInterface> list = new ArrayList<>();
        try {
            String url = String.format("%s/%s/%s/search?title=%s",
                    MONGODB_URL, DATABASE, MOVIES_COLL, title);
            Request req = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response res = client.newCall(req).execute();
            if (res.isSuccessful() && res.body() != null) {
                JSONArray arr = new JSONArray(res.body().string());
                for (int i = 0; i < arr.length(); i++) {
                    list.add(convertJsonToMovie(arr.getJSONObject(i)));
                }
            } else {
                System.err.println("Search failed for title '" + title + "': HTTP " + res.code());
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error searching movies by '" + title + "': " + e.getMessage());
        }
        return list;
    }

    public boolean updateMovie(MovieInterface movie) {
        try {
            JSONObject json = convertMovieToJson(movie);
            String url = String.format("%s/%s/%s/%d", MONGODB_URL, DATABASE, MOVIES_COLL, movie.getMovieId());
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json.toString()
            );
            Request req = new Request.Builder()
                    .url(url)
                    .put(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response res = client.newCall(req).execute();
            if (!res.isSuccessful()) {
                System.err.println("Failed to update movie " + movie.getMovieId() + ": HTTP " + res.code());
            }
            return res.isSuccessful();
        } catch (IOException | JSONException e) {
            System.err.println("Error updating movie '" + movie.getTitle() + "': " + e.getMessage());
            return false;
        }
    }

    public boolean deleteMovie(int movieId) {
        try {
            String url = String.format("%s/%s/%s/%d", MONGODB_URL, DATABASE, MOVIES_COLL, movieId);
            Request req = new Request.Builder()
                    .url(url)
                    .delete()
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response res = client.newCall(req).execute();
            if (!res.isSuccessful()) {
                System.err.println("Failed to delete movie " + movieId + ": HTTP " + res.code());
            }
            return res.isSuccessful();
        } catch (IOException e) {
            System.err.println("Error deleting movie ID " + movieId + ": " + e.getMessage());
            return false;
        }
    }

    // --- User Operations ---

    public boolean saveUser(AppUser user) {
        try {
            JSONObject json = convertUserToJson(user);
            String url = String.format("%s/%s/%s", MONGODB_URL, DATABASE, USERS_COLL);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json.toString()
            );
            Request req = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response res = client.newCall(req).execute();
            if (!res.isSuccessful()) {
                System.err.println("Failed to save user: HTTP " + res.code());
            }
            return res.isSuccessful();
        } catch (IOException | JSONException e) {
            System.err.println("Error saving user '" + user.getUsername() + "': " + e.getMessage());
            return false;
        }
    }

    public AppUser getUser(int accountId) {
        try {
            String url = String.format("%s/%s/%s/%d", MONGODB_URL, DATABASE, USERS_COLL, accountId);
            Request req = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response res = client.newCall(req).execute();
            if (res.isSuccessful() && res.body() != null) {
                return convertJsonToUser(new JSONObject(res.body().string()));
            } else {
                System.err.println("User not found or HTTP " + res.code() + " for account " + accountId);
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error retrieving user " + accountId + ": " + e.getMessage());
        }
        return null;
    }

    public boolean updateUser(AppUser user) {
        try {
            JSONObject json = convertUserToJson(user);
            String url = String.format("%s/%s/%s/%d", MONGODB_URL, DATABASE, USERS_COLL, user.getAccountId());
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json.toString()
            );
            Request req = new Request.Builder()
                    .url(url)
                    .put(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response res = client.newCall(req).execute();
            if (!res.isSuccessful()) {
                System.err.println("Failed to update user " + user.getAccountId() + ": HTTP " + res.code());
            }
            return res.isSuccessful();
        } catch (IOException | JSONException e) {
            System.err.println("Error updating user '" + user.getUsername() + "': " + e.getMessage());
            return false;
        }
    }

    // --- Session Operations ---

    public boolean saveSession(Session session) {
        try {
            JSONObject json = convertSessionToJson(session);
            String url = String.format("%s/%s/%s", MONGODB_URL, DATABASE, SESSIONS_COLL);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json.toString()
            );
            Request req = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response res = client.newCall(req).execute();
            if (!res.isSuccessful()) {
                System.err.println("Failed to save session: HTTP " + res.code());
            }
            return res.isSuccessful();
        } catch (IOException | JSONException e) {
            System.err.println("Error saving session '" + session.getSessionId() + "': " + e.getMessage());
            return false;
        }
    }

    public Session getCurrentSession() {
        try {
            String url = String.format("%s/%s/%s/current", MONGODB_URL, DATABASE, SESSIONS_COLL);
            Request req = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response res = client.newCall(req).execute();
            if (res.isSuccessful() && res.body() != null) {
                return convertJsonToSession(new JSONObject(res.body().string()));
            } else {
                System.err.println("No active session found or HTTP " + res.code());
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error fetching current session: " + e.getMessage());
        }
        return null;
    }

    // --- JSON Conversion Helpers ---

    private JSONObject convertMovieToJson(MovieInterface m) throws JSONException {
        JSONObject j = new JSONObject();
        j.put("movieId", m.getMovieId());
        j.put("title", m.getTitle());
        j.put("releaseDate", m.getReleaseDate().toString());
        j.put("poster", m.getPoster());
        j.put("synopsis", m.getSynopsis());
        j.put("runtime", m.getRuntime());
        j.put("genre", m.getGenre());
        j.put("language", m.getLanguage());
        j.put("rentPrice", m.getRentPrice());
        j.put("buyPrice", m.getBuyPrice());
        return j;
    }

    private MovieInterface convertJsonToMovie(JSONObject json) throws JSONException {
        int id = json.getInt("movieId");
        String title = json.getString("title");
        LocalDate date = LocalDate.parse(json.getString("releaseDate"));
        String poster = json.optString("poster", null);
        String synopsis = json.optString("synopsis", "");
        int runtime = json.optInt("runtime", 0);
        String genre = json.optString("genre", "");
        String lang = json.optString("language", "");
        double rent = json.optDouble("rentPrice", 0.0);
        double buy = json.optDouble("buyPrice", 0.0);
        return new Movie(id, title, date, poster, synopsis, runtime, genre, lang, rent, buy);
    }

    private JSONObject convertUserToJson(AppUser u) throws JSONException {
        JSONObject j = new JSONObject();
        j.put("accountId", u.getAccountId());
        j.put("username", u.getUsername());
        j.put("preferredGenres", new JSONArray(u.getPreferredGenres()));
        j.put("savedMovies", new JSONArray(u.getSavedMovies()));
        JSONObject rated = new JSONObject();
        for (Map.Entry<Integer, Integer> e : u.getRatedMovies().entrySet()) {
            rated.put(String.valueOf(e.getKey()), e.getValue());
        }
        j.put("ratedMovies", rated);
        return j;
    }

    private AppUser convertJsonToUser(JSONObject json) throws JSONException {
        int id = json.getInt("accountId");
        String name = json.getString("username");
        List<String> prefs = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("preferredGenres").length(); i++) {
            prefs.add(json.getJSONArray("preferredGenres").getString(i));
        }
        List<Integer> saved = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("savedMovies").length(); i++) {
            saved.add(json.getJSONArray("savedMovies").getInt(i));
        }
        Map<Integer, Integer> ratedMap = new HashMap<>();
        JSONObject ratedObj = json.getJSONObject("ratedMovies");
        for (String key : ratedObj.keySet()) {
            ratedMap.put(Integer.parseInt(key), ratedObj.getInt(key));
        }
        return new AppUser(id, name, prefs, saved, new ArrayList<>(), ratedMap);
    }

    private JSONObject convertSessionToJson(Session s) throws JSONException {
        JSONObject j = new JSONObject();
        j.put("sessionId", s.getSessionId());
        j.put("accountId", s.getAccountId());
        j.put("createdAt", s.getCreatedAt().toString());
        j.put("expiresAt", s.getExpiresAt().toString());
        return j;
    }

    private Session convertJsonToSession(JSONObject json) throws JSONException {
        String sid = json.getString("sessionId");
        int aid = json.getInt("accountId");
        LocalDateTime created = LocalDateTime.parse(json.getString("createdAt"));
        LocalDateTime expires = LocalDateTime.parse(json.getString("expiresAt"));
        Session s = new Session(sid, aid);
        s.setCreatedAt(created);
        s.setExpiresAt(expires);
        return s;
    }

    /**
     * Clears all data from the local database.
     * Used when user logs out to prepare for next user.
     */
    public void clearAllData() {
        try {
            // Clear movies collection
            String moviesUrl = String.format("%s/%s/%s", MONGODB_URL, DATABASE, MOVIES_COLL);
            Request moviesReq = new Request.Builder()
                    .url(moviesUrl)
                    .delete()
                    .addHeader("Content-Type", "application/json")
                    .build();
            client.newCall(moviesReq).execute();

            // Clear users collection
            String usersUrl = String.format("%s/%s/%s", MONGODB_URL, DATABASE, USERS_COLL);
            Request usersReq = new Request.Builder()
                    .url(usersUrl)
                    .delete()
                    .addHeader("Content-Type", "application/json")
                    .build();
            client.newCall(usersReq).execute();

            // Clear sessions collection
            String sessionsUrl = String.format("%s/%s/%s", MONGODB_URL, DATABASE, SESSIONS_COLL);
            Request sessionsReq = new Request.Builder()
                    .url(sessionsUrl)
                    .delete()
                    .addHeader("Content-Type", "application/json")
                    .build();
            client.newCall(sessionsReq).execute();

            System.out.println("All local data cleared successfully");
        } catch (IOException e) {
            System.err.println("Error clearing local data: " + e.getMessage());
        }
    }
}