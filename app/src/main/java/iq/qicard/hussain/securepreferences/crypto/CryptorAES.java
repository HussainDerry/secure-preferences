package iq.qicard.hussain.securepreferences.crypto;

import android.util.Base64;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import iq.qicard.hussain.securepreferences.util.PasswordHashHelper;

public class CryptorAES {

    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int PBKDF2_ITERATIONS = 65536;
    private static final int PBKDF2_HASH_SIZE_BYTES = 32;
    private static final int PBKDF2_SALT_SIZE_BYTES = 32;

    private static final int INDEX_SALT = 0;
    private static final int INDEX_IV = 1;
    private static final int INDEX_CIPHER_TEXT = 2;

    private static final String SPLITTER = "\\.";
    private char[] mSekrt;

    public CryptorAES(char[] secretKey){
        mSekrt = Arrays.copyOf(secretKey, secretKey.length);
    }

    public synchronized String encryptToBase64(byte[] data){
        // Generating Random IV
        SecureRandom mRandom = new SecureRandom();
        byte[] iv = new byte[16];
        mRandom.nextBytes(iv);

        // Generating Salt
        byte[] salt = new byte[PBKDF2_SALT_SIZE_BYTES];
        mRandom.nextBytes(salt);

        byte[] encrypted = CipherAES.encrypt(generateSecretKey(mSekrt, salt), iv, data);
        return toBase64(salt) + SPLITTER + toBase64(iv) + SPLITTER + toBase64(encrypted);
    }

    public synchronized byte[] decryptFromBase64(String encryptedBase64){
        String[] parts = encryptedBase64.split(SPLITTER);
        byte[] salt = fromBase64(parts[INDEX_SALT]);
        byte[] iv = fromBase64(parts[INDEX_IV]);
        byte[] cipherText = fromBase64(parts[INDEX_CIPHER_TEXT]);

        return CipherAES.decrypt(generateSecretKey(mSekrt, salt), iv, cipherText);
    }

    private byte[] generateSecretKey(char[] password, byte[] salt){
        try{
            return PasswordHashHelper.pbkdf2(password, salt, PBKDF2_ITERATIONS, PBKDF2_HASH_SIZE_BYTES);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String toBase64(byte[] data){
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    private byte[] fromBase64(String base64){
        return Base64.decode(base64, Base64.DEFAULT);
    }

}
