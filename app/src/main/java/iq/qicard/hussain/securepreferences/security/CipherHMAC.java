package iq.qicard.hussain.securepreferences.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import iq.qicard.hussain.securepreferences.util.ByteUtils;

public class CipherHMAC {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private final Mac hmac;

    public CipherHMAC(char[] password){
        try{
            byte[] tempKey = ByteUtils.convertCharsToBytes(password);
            hmac = Mac.getInstance(HMAC_ALGORITHM);
            hmac.init(new SecretKeySpec(tempKey, HMAC_ALGORITHM));
        }catch(InvalidKeyException | NoSuchAlgorithmException | NullPointerException exception){
            throw new IllegalStateException("Initialization Failed!");
        }
    }

    public synchronized byte[] createHmac(byte[] data){
        return hmac.doFinal(data);
    }

    public synchronized boolean verify(byte[] data, byte[] mac){
        return Arrays.equals(mac, createHmac(data));
    }

}
