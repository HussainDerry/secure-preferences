package iq.qicard.hussain.securepreferences.crypto;

import android.util.Base64;

import java.security.SecureRandom;
import java.util.Arrays;

public class CryptorAES {

    private static final String SPLITTER = "\\.";
    private byte[] mSekrt;

    public CryptorAES(byte[] secretKey){
        mSekrt = Arrays.copyOf(secretKey, secretKey.length);
    }

    public String encryptToBase64(byte[] data){
        // Generating Random IV
        SecureRandom mRandom = new SecureRandom();
        byte[] iv = new byte[16];
        mRandom.nextBytes(iv);

        byte[] encrypted = CipherAES.encrypt(mSekrt, iv, data);
        return toBase64(iv) + SPLITTER + toBase64(encrypted);
    }

    public byte[] decryptFromBase64(String encryptedBase64){
        String[] parts = encryptedBase64.split(SPLITTER);
        byte[] iv = fromBase64(parts[0]);
        byte[] cipherText = fromBase64(parts[1]);

        return CipherAES.decrypt(mSekrt, iv, cipherText);
    }

    private String toBase64(byte[] data){
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    private byte[] fromBase64(String base64){
        return Base64.decode(base64, Base64.DEFAULT);
    }

}
