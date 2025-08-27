package com.kenanpalmer.super_secret_santa.exception;

public class CircleSaveException extends RuntimeException{
    public CircleSaveException(String message){
        super(message);
    }

    public CircleSaveException(String message, Throwable cause){
        super(message, cause);
    }

    public CircleSaveException(Exception e) {
        super(e);
    }
}
