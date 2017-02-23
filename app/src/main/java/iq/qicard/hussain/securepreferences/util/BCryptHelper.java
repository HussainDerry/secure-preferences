package iq.qicard.hussain.securepreferences.util;

import org.spongycastle.crypto.generators.BCrypt;

import android.util.Base64;

public class BCryptHelper {

    public static byte[] hashPassword(byte[] password, byte[] salt, int cost){
        return BCrypt.generate(password, salt, cost);
    }

    public static String hashPasswordToBase64(byte[] password, byte[] salt, int cost){
        return Base64.encodeToString(hashPassword(password, salt, cost), Base64.DEFAULT);
    }

}
