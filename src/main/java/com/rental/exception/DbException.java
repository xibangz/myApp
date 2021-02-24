package com.rental.exception;

public class DbException extends Exception{
    private static final long serialVersionUID = 4316833880959171415L;

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbException(String message) {
        super(message);
    }
}
