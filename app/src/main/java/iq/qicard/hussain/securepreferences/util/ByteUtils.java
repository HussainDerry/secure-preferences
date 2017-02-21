package iq.qicard.hussain.securepreferences.util;

public class ByteUtils {

    public static byte[] convertCharsToBytes(char[] secret){
        byte[] mBytes = new byte[secret.length];
        for(int i = 0; i < secret.length; i++) {
            mBytes[i] = (byte)(secret[i] & 0x00FF);
        }

        return mBytes;
    }

}
