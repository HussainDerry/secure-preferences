package com.github.hussainderry.securepreferences.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Hussain Al-Derry <hussain.derry@gmail.com>
 * @version 1.0
 * */
final class CipherAES{

    /* Encryption Variables */
    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String BLOCK_OPERATION_MODE = "CBC";
    private static final String PADDING_TYPE = "PKCS5Padding";
    private static final String ENCRYPTION_MODE;

    private static final Cipher mCipher;

    static {
        ENCRYPTION_MODE = ENCRYPTION_ALGORITHM + "/" + BLOCK_OPERATION_MODE + "/" + PADDING_TYPE;
        try{
            mCipher = Cipher.getInstance(ENCRYPTION_MODE);
        }catch(NoSuchAlgorithmException | NoSuchPaddingException e){
            throw new IllegalStateException("Unable to initialize cipher, mode might not be supported");
        }
    }

    static byte[] encrypt(byte[] key, byte[] iv, byte[] data){
        synchronized(mCipher){
            try{
                mCipher.init(Cipher.ENCRYPT_MODE,
                        generateSecretKeySpec(key),
                        generateIvParameterSpec(iv));
                return mCipher.doFinal(data);
            }catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
                throw new IllegalStateException(e.getMessage());
            }
        }
    }

    static byte[] decrypt(byte[] key, byte[] iv, byte[] data){
        synchronized(mCipher){
            try{
                mCipher.init(Cipher.DECRYPT_MODE,
                        generateSecretKeySpec(key),
                        generateIvParameterSpec(iv));
                return mCipher.doFinal(data);
            }catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
                throw new IllegalStateException(e.getMessage());
            }
        }
    }

    private static SecretKeySpec generateSecretKeySpec(byte[] key){
        return new SecretKeySpec(key, ENCRYPTION_ALGORITHM);
    }

    private static IvParameterSpec generateIvParameterSpec(byte[] iv){
        return new IvParameterSpec(iv);
    }
}
