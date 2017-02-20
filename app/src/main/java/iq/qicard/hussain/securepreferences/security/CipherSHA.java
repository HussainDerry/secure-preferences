/*
 * Copyright (c) Hussain Yahia Al-Derry 2016. All Rights Reserved.
 */

package iq.qicard.hussain.securepreferences.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Provides a repo of hash functions, returns the hashed String in Base64 encoding.
 * @author Hussain Al-Derry
 * @version 0.1
 * */
public class CipherSHA {

    private static final String CHARSET = "UTF-8";

    /**
     * Hashes a given String using the SHA-256
     * @param data the byte data to be hashed
     * @return hashed byte array containing the hash
     * */
    public static byte[] hashUsingSHA256(byte[] data){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(data);
        }catch(NoSuchAlgorithmException e){
            throw new IllegalStateException("Unable to hash!");
        }
    }

    /**
     * Hashes a given String using the SHA-512
     * @param data the byte data to be hashed
     * @return hashed byte array containing the hash
     * */
    public static byte[] hashUsingSHA512(byte[] data){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            return digest.digest(data);
        }catch(NoSuchAlgorithmException e){
            throw new IllegalStateException("Unable to hash!");
        }
    }
}
