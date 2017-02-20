package andras.com.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.net.URL;

import andras.com.popularmovies.data.MovieDataBase;
import andras.com.popularmovies.data.MovieInformation;
import andras.com.popularmovies.utils.MovieDataUtils;

public class MoviesMainActivity extends AppCompatActivity implements MoviesAdapter.MovieOnClickHandler{

    private RecyclerView mMoviesRecycleView;
    private RecyclerView.Adapter mMoviesAdapter;
    private RecyclerView.LayoutManager mMoviesLayoutManager;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_main);

        mMoviesRecycleView = (RecyclerView) findViewById(R.id.movies_recycle_view);
        mProgressBar = (ProgressBar) findViewById(R.id.movie_progress_bar);

        mMoviesLayoutManager = new GridLayoutManager(this,3);
        mMoviesRecycleView.setLayoutManager(mMoviesLayoutManager);

        new GetMovieDataTask().execute();
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

    private class GetMovieDataTask extends AsyncTask<URL,Void,MovieDataBase>{

        ProgressBar mProgressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
        }

        @Override
        protected MovieDataBase doInBackground(URL... params) {
            MovieDataBase movieDB;
            movieDB = MovieDataUtils.getMovieData();

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
