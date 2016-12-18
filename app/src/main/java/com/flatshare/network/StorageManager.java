package com.flatshare.network;

/**
 * Created by Arber on 11/12/2016.
 */

public interface StorageManager {

    boolean uploadImage(String userId, byte[] data);

    boolean uploadVideo(String userId, byte[] data);

    byte[] downloadImage(String userId, String imgName);

    byte[] downloadVideo(String userId, String vidName);
}
