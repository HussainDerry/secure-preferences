package iq.qicard.hussain.securepreferences.util;

import java.util.Arrays;

import iq.qicard.hussain.securepreferences.security.CipherAES;
import iq.qicard.hussain.securepreferences.security.CipherSHA;

public class AesCipherHelper {

    public static CipherAES generateFromSinglePassphrase(String passphrase){
        char[] mSecretChars = passphrase.toCharArray();
        return new CipherAES(mSecretChars, generateIvFromHash(ByteUtils.convertCharsToBytes(mSecretChars)));
    }

    private static byte[] generateIvFromHash(byte[] data){
        byte[] mHash = CipherSHA.hashUsingSHA512(data);
        return Arrays.copyOfRange(mHash, 0, 16);
    }

}
