package com.flatshare.utils.exceptions;

/**
 * Created by Arber on 10/12/2016.
 */

public class WrongLoginDetailsException extends Exception {

    public WrongLoginDetailsException() {
        super();
    }

    public WrongLoginDetailsException(String message) {
        super(message);
    }

    public WrongLoginDetailsException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongLoginDetailsException(Throwable cause) {
        super(cause);
    }
}
