package iq.qicard.hussain.securepreferences.util;

import java.util.Arrays;

import iq.qicard.hussain.securepreferences.crypto.CipherAES;
import iq.qicard.hussain.securepreferences.crypto.CipherSHA;

public class AesCipherHelper {

    public static CipherAES generateFromSinglePassphrase(String passphrase){
        char[] mSecretChars = passphrase.toCharArray();
        return null;
    }

    private static byte[] generateIvFromHash(byte[] data){
        byte[] mHash = CipherSHA.hashUsingSHA512(data);
        return Arrays.copyOfRange(mHash, 0, 16);
    }

    private byte[] generateHashedPassword(){
        return null;
    }
}
