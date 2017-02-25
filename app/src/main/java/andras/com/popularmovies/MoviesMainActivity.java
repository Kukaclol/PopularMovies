package andras.com.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.net.URL;

import andras.com.popularmovies.data.MovieDataBase;
import andras.com.popularmovies.data.MovieInformation;
import andras.com.popularmovies.utils.MovieDataUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesMainActivity extends AppCompatActivity implements MoviesAdapter.MovieOnClickHandler{

    @BindView(R.id.movies_recycle_view)  RecyclerView mMoviesRecycleView;
    private RecyclerView.Adapter mMoviesAdapter;
    private RecyclerView.LayoutManager mMoviesLayoutManager;

    @BindView(R.id.movie_progress_bar)  ProgressBar mProgressBar;
    @BindView(R.id.api_toggle_button)  ToggleButton mToggleButton;
    @BindView(R.id.no_connection)  TextView mNoConnectionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_main);
        ButterKnife.bind(this);

        mMoviesLayoutManager = new GridLayoutManager(this,calculateNoOfColumns(this));
        mMoviesRecycleView.setLayoutManager(mMoviesLayoutManager);

        updatedDB();

        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedDB();
            }
        });
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 120);
        return noOfColumns;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void updatedDB(){
        if(isOnline()) {
            new GetMovieDataTask().execute(mToggleButton.isChecked());
            mNoConnectionTextView.setVisibility(View.INVISIBLE);
        }else{
            mNoConnectionTextView.setVisibility(View.VISIBLE);
        }

    }

    private void showContent(){
        mMoviesRecycleView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar(){
        mMoviesRecycleView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMovieClick(int i) {
        Context context = MoviesMainActivity.this;
        Class destinationClass = MovieDetailActivity.class;
        Intent intent = new Intent(context, destinationClass);
        intent.putExtra("MovieID",i);
        startActivity(intent);
    }

    private class GetMovieDataTask extends AsyncTask<Boolean,Void,MovieDataBase>{

        ProgressBar mProgressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
        }

        @Override
        protected MovieDataBase doInBackground(Boolean... params) {
            MovieDataBase movieDB;
            if(params[0].booleanValue()) {
                movieDB = MovieDataUtils.getTopRated();
            }else{
                movieDB = MovieDataUtils.getPopular();
            }

            return movieDB;
        }

        @Override
        protected void onPostExecute(MovieDataBase movieInformations) {
            mMoviesAdapter = new MoviesAdapter(movieInformations,MoviesMainActivity.this);
            mMoviesRecycleView.setAdapter(mMoviesAdapter);
            showContent();
        }
    }

}
