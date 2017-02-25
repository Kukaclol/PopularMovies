package andras.com.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import andras.com.popularmovies.data.MovieDataBase;
import andras.com.popularmovies.data.MovieInformation;
import andras.com.popularmovies.utils.DownloadImageTask;

public class MovieDetailActivity extends Activity {

    ImageView mPoster;
    TextView mTitleTextView;
    TextView mDescriptionTextView;
    TextView mRatingTextView;
    TextView mReleaseDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mPoster = (ImageView) findViewById(R.id.poster_image_view);
        mTitleTextView = (TextView) findViewById(R.id.title_text_view);
        mDescriptionTextView = (TextView) findViewById(R.id.description_text_view);
        mRatingTextView = (TextView) findViewById(R.id.user_rating_text_view);
        mReleaseDateTextView = (TextView) findViewById(R.id.release_date_text_view);

        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("MovieID", 0);
        MovieInformation movie = MovieDataBase.get(intValue);

        new DownloadImageTask(mPoster).execute(movie.getPosterURL());
        mTitleTextView.setText(movie.getTitle());
        mDescriptionTextView.setText(movie.getOverview());
        mRatingTextView.setText(String.valueOf(R.string.rating_text + " " + movie.getRating()));
        mReleaseDateTextView.setText(R.string.release_date + " " + movie.getReleaseDate());
    }
}
