package com.rental.exception;

public class DBException extends Exception{
    private static final long serialVersionUID = 4316833880959171415L;

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBException(Throwable cause) {
        super(cause);
    }

    public DBException() {
    }

    public DBException(String message) {
        super(message);
    }
}
