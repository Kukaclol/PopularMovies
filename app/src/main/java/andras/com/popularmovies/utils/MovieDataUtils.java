package andras.com.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import andras.com.popularmovies.data.MovieDataBase;
import andras.com.popularmovies.data.MovieInformation;

public class MovieDataUtils {
    static final String tag = MovieDataUtils.class.getSimpleName();

    static MovieDataBase movieData = null;

    static final String topRatedUrl = "https://api.themoviedb.org/3/movie/top_rated";

    static final String apiKey = "api_key";
    //TODO: Add your API key here
    static final String apiKeyValue = "";

    static final String thumbnailBaselUrl = "http://image.tmdb.org/t/p/w300/";
    static final String posterBaselUrl = "http://image.tmdb.org/t/p/w780/";

    public static MovieDataBase getMovieData() {
        if(!movieData.isInitialized()){
            downloadMovieData();
        }
        return movieData;
    }

    public static void refresh(){
        downloadMovieData();
    }

    private static void downloadMovieData(){
        String response = null;
        try {
            response = getResponseFromHttpUrl(buildUrl());
        } catch (IOException e) {
            Log.e(tag, e.getMessage());
            return;
        }
        Log.v(tag, response);
        try {
            parseMovieJSON(response);
        } catch (JSONException e) {
            Log.e(tag, e.getMessage());
        }
    }

    private static URL buildUrl() {
        Uri builtUri = Uri.parse(topRatedUrl).buildUpon()
                .appendQueryParameter(apiKey, apiKeyValue)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(tag, e.getMessage());
        }

        Log.v(tag, "Built URI " + url);

        return url;
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private static void parseMovieJSON(String json) throws JSONException {
        JSONObject response = new JSONObject(json);
        JSONArray movies = (JSONArray) response.get("results");
        movieData.initialize(movies.length());
        for(int i=0; i < movies.length(); i++){
            JSONObject movie = movies.getJSONObject(i);
            String title = movie.getString("title");
            String overview = movie.getString("overview");
            String thumbnailUrl = buildPosterURL(thumbnailBaselUrl,movie.getString("poster_path"));
            String posterlUrl = buildPosterURL(posterBaselUrl,movie.getString("poster_path"));
            Double rating = movie.getDouble("vote_average");
            String releaseDate = movie.getString("release_date");

            movieData.set(i,new MovieInformation(title,overview,thumbnailUrl,posterlUrl,rating,releaseDate));
        }

    }

    private static String buildPosterURL(String baselUrl,String poster_path) {
        Uri builtUri = Uri.parse(baselUrl + poster_path).buildUpon()
                .appendQueryParameter(apiKey, apiKeyValue)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(tag, e.getMessage());
        }
        return url.toString();
    }

}