package iq.qicard.hussain.securepreferences.util;

import org.spongycastle.crypto.RuntimeCryptoException;
import org.spongycastle.crypto.generators.BCrypt;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class BCryptHelper {

    public static byte[] hashPassword(byte[] password, byte[] salt, int cost){
        return BCrypt.generate(password, salt, cost);
    }

    public static String hashPasswordToBase64(byte[] password, byte[] salt, int cost){
        try {
            return new String(hashPassword(password, salt, cost), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("UTF-8 Unsupported !!!");
        }
    }

}
