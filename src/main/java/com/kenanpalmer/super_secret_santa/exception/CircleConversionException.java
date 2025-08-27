package com.kenanpalmer.super_secret_santa.exception;

public class CircleConversionException  extends RuntimeException{
    public CircleConversionException(String message){
        super(message);
    }

    public CircleConversionException(String message, Throwable cause){
        super(message, cause);
    }

    public CircleConversionException(Exception e) {
    }
}
