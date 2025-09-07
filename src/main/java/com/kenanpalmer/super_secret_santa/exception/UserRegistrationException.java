package com.kenanpalmer.super_secret_santa.exception;

public class UserRegistrationException extends RuntimeException{
    public UserRegistrationException(String message){
        super(message);
    }

    public UserRegistrationException(String message, Throwable cause){
        super(message, cause);
    }

    public UserRegistrationException(Exception e) {
        super(e);
    }
}
