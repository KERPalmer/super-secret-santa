package com.kenanpalmer.super_secret_santa.exception;

public class UsernameNotFoundException extends RuntimeException{
    public UsernameNotFoundException(String message){
        super(message);
    }

    public UsernameNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public UsernameNotFoundException(Exception e) {
        super(e);
    }
}
