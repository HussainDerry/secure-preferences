package iq.qicard.hussain.securepreferences;

import java.security.SecureRandom;
import java.util.UUID;

import iq.qicard.hussain.securepreferences.security.CipherAES;

public class MainTest {

    public static void main(String[] args) throws Exception{

        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        CipherAES mEncryptor = new CipherAES("thisissupposedtobeastrongpassword".toCharArray(), iv);

        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 10000; i ++){
            String plainData = UUID.randomUUID().toString();
            byte[] encryptedBytes = mEncryptor.encrypt(plainData.getBytes("UTF-8"));
            byte[] decryptedBytes = mEncryptor.decrypt(encryptedBytes);
            String decrypedData = new String(decryptedBytes, "UTF-8");
            if (!decrypedData.equals(plainData)) throw new AssertionError("Doesn't Match!");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Encrypted/Decrypted 10000 Strings of length 36 in " + (endTime - startTime) + " millis");
    }

}
