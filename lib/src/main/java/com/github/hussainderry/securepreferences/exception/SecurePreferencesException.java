package com.github.hussainderry.securepreferences.exception;

/**
 * Base exception for all secure-preferences errors.
 * @author Hussain Al-Derry
 */
public class SecurePreferencesException extends RuntimeException{

    public SecurePreferencesException(String message){
        super(message);
    }

    public SecurePreferencesException(String message, Throwable cause){
        super(message, cause);
    }
}
