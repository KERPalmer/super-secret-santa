package com.kenanpalmer.super_secret_santa.exception;

public class CircleNotFoundException extends RuntimeException{
    public CircleNotFoundException(String message){
        super(message);
    }

    public CircleNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public CircleNotFoundException(Exception e) {
        super(e);
    }
}
