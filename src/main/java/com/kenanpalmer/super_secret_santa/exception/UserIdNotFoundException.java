package com.kenanpalmer.super_secret_santa.exception;

public class UserIdNotFoundException extends RuntimeException{
    public UserIdNotFoundException(String message){
        super(message);
    }

    public UserIdNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public UserIdNotFoundException(Exception e) {
        super(e);
    }
}
