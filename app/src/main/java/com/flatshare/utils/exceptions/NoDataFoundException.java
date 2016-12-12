package com.flatshare.utils.exceptions;

/**
 * Created by Arber on 10/12/2016.
 */

public class NoDataFoundException extends Exception {

    public NoDataFoundException() {
        super();
//        super("There was no data found for your query");
    }

    public NoDataFoundException(String message) {
        super(message);
    }

    public NoDataFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDataFoundException(Throwable cause) {
        super(cause);
    }
}
