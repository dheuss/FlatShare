package com.flatshare.domain.interactors.media.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * TODO: use method in a asyncTask ande DELETE CLASS!
 * Created by Arber on 18/01/2017.
 */
public class MediaInteractorCompresser {


    private static final String TAG = "MediaInteractorCompresser";
    public static final int REQUIRED_SIZE = 150;

    // Decodes image and scales it to reduce memory consumption
    public static Bitmap compress(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "compress: Error!", e);
            return null;
        }
    }
}
