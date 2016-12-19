package com.flatshare.domain.repository;

/**
 * Created by Arber on 10/12/2016.
 */

public interface StorageRepository {

    byte[] downloadMedia(boolean isTenant, int mediaType, String mediaName);

    boolean uploadMedia(boolean isTenant, int mediaType, String mediaName, byte[] data);


}
