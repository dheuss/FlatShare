package com.flatshare.utils.exceptions;

/**
 * Created by Arber on 10/12/2016.
 */

public class CreateFailureException extends Exception {

    public CreateFailureException() {
        super();
//        super("There was no data found for your query");
    }

    public CreateFailureException(String message) {
        super(message);
    }

    public CreateFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateFailureException(Throwable cause) {
        super(cause);
    }

}
