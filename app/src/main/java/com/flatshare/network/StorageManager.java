package com.flatshare.network;

/**
 * Created by Arber on 11/12/2016.
 */

public interface StorageManager {

    boolean uploadImage(String profileId, byte[] data);

    boolean uploadVideo(String profileId, byte[] data);

    byte[] downloadImage(String profileId, String imgName);

    byte[] downloadVideo(String profileId, String vidName);
}
