package iq.qicard.hussain.securepreferences.crypto;

import android.util.Base64;

import java.security.SecureRandom;

import iq.qicard.hussain.securepreferences.model.SecurityConfig;
import iq.qicard.hussain.securepreferences.util.PasswordHashHelper;

public final class CryptorAES {

    private static final int AES_KEY_SIZE = 256;
    private static final int AES_IV_SIZE = 16;

    private static final int INDEX_SALT = 0;
    private static final int INDEX_IV = 1;
    private static final int INDEX_CIPHER_TEXT = 2;

    private static final String SPLITTER = "\\.";
    private final SecurityConfig mSecurityConfig;

    public CryptorAES(SecurityConfig securityConfig){
        this.mSecurityConfig = securityConfig;
    }

    public synchronized String encryptToBase64(byte[] data){
        // Generating Random IV
        SecureRandom mRandom = new SecureRandom();
        byte[] iv = new byte[AES_IV_SIZE];
        mRandom.nextBytes(iv);

        // Generating Salt
        byte[] salt = new byte[mSecurityConfig.getSaltSize()];
        mRandom.nextBytes(salt);

        byte[] encrypted = CipherAES.encrypt(generateSecretKey(mSecurityConfig.getPassword(), salt), iv, data);
        return new StringBuilder()
                .append(toBase64(salt))
                .append(".")
                .append(toBase64(iv))
                .append(".")
                .append(toBase64(encrypted))
                .toString();
    }

    public synchronized byte[] decryptFromBase64(String encryptedBase64){
        String[] parts = encryptedBase64.split(SPLITTER);
        byte[] salt = fromBase64(parts[INDEX_SALT]);
        byte[] iv = fromBase64(parts[INDEX_IV]);
        byte[] cipherText = fromBase64(parts[INDEX_CIPHER_TEXT]);

        return CipherAES.decrypt(generateSecretKey(mSecurityConfig.getPassword(), salt), iv, cipherText);
    }

    private byte[] generateSecretKey(char[] password, byte[] salt){
        switch(mSecurityConfig.getDigestType()){

            case SHA1:{
                return PasswordHashHelper.hashUsingPBKDF2WithSHA1(password, salt,
                        mSecurityConfig.getPBKDF2Iterations(), AES_KEY_SIZE);
            }

            case SHA256:{
                return PasswordHashHelper.hashUsingPBKDF2WithSHA256(password, salt,
                        mSecurityConfig.getPBKDF2Iterations(), AES_KEY_SIZE);
            }

            case SHA512:{
                return PasswordHashHelper.hashUsingPBKDF2WithSHA512(password, salt,
                        mSecurityConfig.getPBKDF2Iterations(), AES_KEY_SIZE);
            }

            default:{
                throw new IllegalStateException("Unknown Digest Type!");
            }

        }
    }

    private String toBase64(byte[] data){
        return Base64.encodeToString(data, Base64.NO_WRAP);
    }

    private byte[] fromBase64(String base64){
        return Base64.decode(base64, Base64.NO_WRAP);
    }

}
