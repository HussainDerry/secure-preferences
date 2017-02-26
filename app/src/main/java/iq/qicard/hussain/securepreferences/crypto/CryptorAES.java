package iq.qicard.hussain.securepreferences.crypto;

import android.util.Base64;

import java.security.SecureRandom;

import iq.qicard.hussain.securepreferences.model.SecurityConfig;

/**
 * @author Hussain Al-Derry
 * @version 1.0
 * */
public final class CryptorAES {

    /* AES configurations */
    private static final int AES_KEY_SIZE = 256;
    private static final int AES_IV_SIZE = 16;

    /* Indexes used for parsing the stored Base64 */
    private static final String SPLITTER = "\\.";
    private static final int INDEX_SALT = 0;
    private static final int INDEX_IV = 1;
    private static final int INDEX_CIPHER_TEXT = 2;

    /* Encryption Configurations */
    private final SecurityConfig mSecurityConfig;

    /**
     * Initializes the Cryptor with the provided {@link SecurityConfig}
     * @param config The security configurations to use
     * */
    public static CryptorAES initWithSecurityConfigurations(SecurityConfig config){
        return new CryptorAES(config);
    }

    private CryptorAES(SecurityConfig securityConfig){
        this.mSecurityConfig = securityConfig;
    }

    /**
     * Encrypts the given data and returns a formatted Base64 for storage.
     *
     * @param data The data to be encrypted.
     * @return Base64 String to be stored.
     * */
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

    /**
     * Decrypts data from a given Base64 String
     *
     * @param encryptedBase64 The Base64 string to be decrypted.
     * @return The data decrypted as byte array.
     * */
    public synchronized byte[] decryptFromBase64(String encryptedBase64){
        String[] parts = encryptedBase64.split(SPLITTER);
        if(parts.length != 3){
            throw new IllegalArgumentException("Malformed data string");
        }

        byte[] salt = fromBase64(parts[INDEX_SALT]);
        byte[] iv = fromBase64(parts[INDEX_IV]);
        byte[] cipherText = fromBase64(parts[INDEX_CIPHER_TEXT]);

        return CipherAES.decrypt(generateSecretKey(mSecurityConfig.getPassword(), salt), iv, cipherText);
    }

    /**
     * Generates an encryption key for AES based on the input and the security configurations.
     *
     * @param password The base password to derive from.
     * @param salt The salt to use for the
     * @return The encryption key to use for AES as byte array.
     * */
    private byte[] generateSecretKey(char[] password, byte[] salt){
        switch(mSecurityConfig.getDigestType()){

            case SHA1:{
                return PBKDF2Helper.hashUsingPBKDF2WithSHA1(password, salt,
                        mSecurityConfig.getPBKDF2Iterations(), AES_KEY_SIZE);
            }

            case SHA256:{
                return PBKDF2Helper.hashUsingPBKDF2WithSHA256(password, salt,
                        mSecurityConfig.getPBKDF2Iterations(), AES_KEY_SIZE);
            }

            case SHA512:{
                return PBKDF2Helper.hashUsingPBKDF2WithSHA512(password, salt,
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
