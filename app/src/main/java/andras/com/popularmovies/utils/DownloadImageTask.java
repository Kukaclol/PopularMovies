package andras.com.popularmovies.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {
    ImageView mImageView;

    public DownloadImageTask(ImageView mImageView) {
        this.mImageView = mImageView;
    }

    @Override
    protected void onPreExecute() {
        mImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        mImageView.setImageBitmap(result);
        mImageView.setVisibility(View.VISIBLE);
    }
}
