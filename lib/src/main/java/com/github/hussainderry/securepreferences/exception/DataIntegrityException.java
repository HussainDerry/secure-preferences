package com.github.hussainderry.securepreferences.exception;

/**
 * Thrown when ciphertext is malformed or authentication fails (wrong password, tampered data).
 * @author Hussain Al-Derry
 */
public final class DataIntegrityException extends SecurePreferencesException{

    public DataIntegrityException(String message){
        super(message);
    }

    public DataIntegrityException(String message, Throwable cause){
        super(message, cause);
    }
}
