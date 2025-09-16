package com.kenanpalmer.super_secret_santa.exception;

public class UsernameAlreadyRegisteredException extends RuntimeException{
    public UsernameAlreadyRegisteredException(String message){
        super(message);
    }

    public UsernameAlreadyRegisteredException(String message, Throwable cause){
        super(message, cause);
    }

    public UsernameAlreadyRegisteredException(Exception e) {
        super(e);
    }
}
