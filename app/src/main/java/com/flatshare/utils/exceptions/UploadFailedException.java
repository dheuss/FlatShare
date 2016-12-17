package com.flatshare.utils.exceptions;

import com.google.firebase.FirebaseException;

/**
 * Created by Arber on 10/12/2016.
 */

public class UploadFailedException extends FirebaseException {

    public UploadFailedException(String message) {
        super(message);
    }

    public UploadFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
