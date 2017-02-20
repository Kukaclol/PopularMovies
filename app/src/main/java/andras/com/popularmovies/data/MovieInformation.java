package andras.com.popularmovies.data;

import java.net.URL;
import java.util.Date;

public class MovieInformation {
    private String title;
    private String overview;
    private String thumbnailURL;
    private String posterURL;
    private Double rating;
    private String releaseDate;



    public MovieInformation(String title) {
        this.title = title;
    }

    public MovieInformation(String title, String thumbnailURL) {
        this.title = title;
        this.thumbnailURL = thumbnailURL;
    }

    public MovieInformation(String title, String overview, String thumbnailURL, String posterURL, Double rating, String releaseDate) {
        this.title = title;
        this.overview = overview;
        this.thumbnailURL = thumbnailURL;
        this.posterURL = posterURL;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }
}
