package com.github.hussainderry.securepreferences.exception;

/**
 * Thrown when a cipher operation (encrypt/decrypt) fails due to provider or algorithm issues.
 * @author Hussain Al-Derry
 */
public final class CipherOperationException extends SecurePreferencesException{

    public CipherOperationException(String message){
        super(message);
    }

    public CipherOperationException(String message, Throwable cause){
        super(message, cause);
    }
}
