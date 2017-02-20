package andras.com.popularmovies.data;

public class MovieDataBase {
    private static MovieInformation[] movieData = null;

    public static boolean isInitialized() {
        return movieData!=null;
    }

    public static void initialize(int length) {
        movieData = new MovieInformation[length];
    }

    public static void set(int i, MovieInformation movieInformation) {
        movieData[i] = movieInformation;
    }

    public static int getLength() {
        return movieData.length;
    }

    public static MovieInformation get(int adapterPosition) {
        return movieData[adapterPosition];
    }
}
