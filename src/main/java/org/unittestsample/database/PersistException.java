package org.unittestsample.database;

/**
 * Created by max on 3/24/15.
 */
public class PersistException extends Exception {

    private static final String MESSAGE = "Problem with save in datastore";

    public PersistException(Exception cause) {
        super(MESSAGE, cause);
    }

    public PersistException(String cause) {
        super(MESSAGE + "(" + cause + ")");
    }

}
