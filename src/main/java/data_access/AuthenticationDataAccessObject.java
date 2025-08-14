package data_access;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import entity.Session;
import entity.SessionInterface;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import use_case.authentication.AuthenticationException;
import use_case.authentication.AuthenticationDataAccessInterface;

public class AuthenticationDataAccessObject implements AuthenticationDataAccessInterface {

    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3";
    private static final CinedexMongoDataBase database = new CinedexMongoDataBase();
    private final String apiKey;
    private final OkHttpClient client;

    public AuthenticationDataAccessObject(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient().newBuilder().build();
    }

    @Override
    public String makeRequestToken() throws AuthenticationException {
        try {
            final String endpoint = TMDB_BASE_URL + "/authentication/token/new";
            final String response = makeApiRequest(endpoint, "GET", null);

            final JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.getBoolean("success")) {
                return jsonResponse.getString("request_token");
            }
            else {
                throw new AuthenticationException("Could not generate a new request token.");
            }
        }
        catch (IOException | JSONException exception) {
            throw new AuthenticationException("An error occurred while generating request token: " + exception.getMessage(),
                    exception);
        }
    }

    @Override
    public void redirectWeb(String requestToken) throws AuthenticationException {
        final String redirectUrl = "https://www.themoviedb.org/authenticate/" + requestToken;

        // TODO: Need to Add Redirect!

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(redirectUrl));
            }
            else {
                System.out.println("Please visit this URL to authenticate: " + redirectUrl);
            }
        }
        catch (IOException | URISyntaxException exception) {
            throw new AuthenticationException("An error occurred while redirecting: " + exception.getMessage(), exception);
        }
    }

    @Override
    public String makeSession(String requestToken) throws AuthenticationException {
        try {
            final String endpoint = TMDB_BASE_URL + "/authentication/session/new";
            final JSONObject requestBody = new JSONObject();
            requestBody.put("request_token", requestToken);

            final String response = makeApiRequest(endpoint, "POST", requestBody.toString());

            final JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.getBoolean("success")) {

                final SessionInterface session = new Session(jsonResponse.getString("session_id"), 0);
                database.saveSession(session);

                return jsonResponse.getString("session_id");
            }
            else {
                throw new AuthenticationException("Could not create a session with the provided token.");
            }
        }
        catch (IOException | JSONException exception) {
            throw new AuthenticationException("An error occurred while creating session: " + exception.getMessage(), exception);
        }
    }

    /**
     * Makes a basic API request to TMDB.
     * @param endpoint the API endpoint.
     * @param method the network request type.
     * @param requestBody the body of the API request.
     * @throws IOException input output exception.
     */
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
}
