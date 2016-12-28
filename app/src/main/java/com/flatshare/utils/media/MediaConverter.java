package com.flatshare.utils.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;

/**
 * Created by Arber on 18/12/2016.
 */

public class MediaConverter {

    public ImageView byteToImageView(byte[] data, Context context) {

        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        ImageView image = new ImageView(context);
        image.setImageBitmap(bmp);
        return image;
    }

    public byte[] imageViewToByte(ImageView imageView) {

        imageView.setDrawingCacheEnabled(true);

        imageView.buildDrawingCache();

        Bitmap bm = imageView.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public byte[] videoViewToByte(VideoView videoView) {

        //TODO: implement conversion

        return null;
    }
}
