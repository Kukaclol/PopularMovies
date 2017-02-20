package andras.com.popularmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;

import andras.com.popularmovies.data.MovieDataBase;
import andras.com.popularmovies.data.MovieInformation;
import andras.com.popularmovies.utils.DownloadImageTask;


public class MoviesAdapter extends android.support.v7.widget.RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private MovieDataBase mMoviesInformation;

    MovieOnClickHandler mClickHandler;

    public MoviesAdapter(MovieDataBase movieInformation, MovieOnClickHandler handler) {
        this.mMoviesInformation = movieInformation;
        mClickHandler = handler;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieImage = inflater.inflate(layoutIdForListItem, parent, false);

        return new MovieViewHolder(movieImage);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        new DownloadImageTask(holder.mMovieView).execute(mMoviesInformation.get(position).getThumbnailURL());
    }

    @Override
    public int getItemCount() {
        return mMoviesInformation.getLength();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        public final ImageView mMovieView;

        public MovieViewHolder(View view) {
            super(view);
            mMovieView = (ImageView) view.findViewById(R.id.movie_image_view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    mClickHandler.onMovieClick(adapterPosition);
                }
            });
        }
    }

    public interface MovieOnClickHandler{
        void onMovieClick(int i);
    }
}
