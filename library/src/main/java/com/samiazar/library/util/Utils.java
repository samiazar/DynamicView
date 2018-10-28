package com.samiazar.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by USER
 * on 10/28/2018.
 */

public class Utils {

    private static final String TAG = "util.Utils";

    public static void saveBitmap(Context context, Bitmap bitmap, String picName) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(picName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "file not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "io exception");
            e.printStackTrace();
        }

    }

    public static Bitmap loadBitmap(Context context, String picName) {

        Bitmap bitmap = null;
        FileInputStream fileInputStream;
        try {
            fileInputStream = context.openFileInput(picName);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();

        } catch (FileNotFoundException e) {
            Log.d(TAG, "file not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "io exception");
            e.printStackTrace();
        }
        return bitmap;
    }

}
