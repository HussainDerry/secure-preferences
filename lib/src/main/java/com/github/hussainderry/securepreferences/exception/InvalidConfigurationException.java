package com.github.hussainderry.securepreferences.exception;

/**
 * Thrown when encryption configuration is invalid (bad key size, iterations, etc.).
 * @author Hussain Al-Derry
 */
public final class InvalidConfigurationException extends SecurePreferencesException{

    public InvalidConfigurationException(String message){
        super(message);
    }

    public InvalidConfigurationException(String message, Throwable cause){
        super(message, cause);
    }
}
