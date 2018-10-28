package com.samiazar.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by USER
 * on 10/28/2018.
 */

public abstract class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private final String TAG = getClass().getName();
    private WeakReference<View> viewWeakReference;
    abstract public void onLoadSource(WeakReference<View> weakReference, Bitmap bitmap);

    public ImageDownloader(View view) {
        this.viewWeakReference = new WeakReference<>(view);
    }

    @Override
    protected Bitmap doInBackground(String... URL) {
        String imageURL = URL[0];
        Bitmap bitmap = null;
        try {
            InputStream input = new java.net.URL(imageURL).openStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            Log.e(TAG, "exception load image: " + e.toString());
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null)
            onLoadSource(viewWeakReference, bitmap);
    }


}