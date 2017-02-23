package iq.qicard.hussain.securepreferences;

import iq.qicard.hussain.securepreferences.crypto.CipherAES;

public class AesEncryptorTest {

    private static CipherAES mEncryptor;
    
    public static void main(String[] args) {
        mEncryptor = new CipherAES("hussain_is_testing*@#".toCharArray());
    }
}
