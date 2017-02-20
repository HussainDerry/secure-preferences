package iq.qicard.hussain.securepreferences.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherAES{

    /* Encryption Variables */
    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String BLOCK_OPERATION_MODE = "CBC";
    private static final String PADDING_TYPE = "PKCS5Padding";
    private static final String ENCRYPTION_MODE;

    static {
        ENCRYPTION_MODE = ENCRYPTION_ALGORITHM + "/" + BLOCK_OPERATION_MODE + "/" + PADDING_TYPE;
    }

    private byte[] mKeyBytes;
    private byte[] mIvBytes;
    private Cipher mCipher;

    public CipherAES(char[] key, byte[] iv){
        convertSecretToBytes(key);
        this.mIvBytes = iv;
    }

    public byte[] encrypt(byte[] data){
        try{
            initCipherObject();
            mCipher.init(Cipher.ENCRYPT_MODE,
                    generateSecretKeySpec(CipherSHA.hashUsingSHA256(mKeyBytes)),
                    generateIvParameterSpec());
            return mCipher.doFinal(data);
        }catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public byte[] decrypt(byte[] data){
        try{
            initCipherObject();
            mCipher.init(Cipher.DECRYPT_MODE,
                    generateSecretKeySpec(CipherSHA.hashUsingSHA256(mKeyBytes)),
                    generateIvParameterSpec());
            return mCipher.doFinal(data);
        }catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private void initCipherObject(){
        try{
            mCipher = Cipher.getInstance(ENCRYPTION_MODE);
        }catch(NoSuchAlgorithmException | NoSuchPaddingException e){
            throw new IllegalStateException("Unable to initialize cipher");
        }
    }

    private SecretKeySpec generateSecretKeySpec(byte[] key){
        return new SecretKeySpec(key, ENCRYPTION_ALGORITHM);
    }

    private IvParameterSpec generateIvParameterSpec(){
        return new IvParameterSpec(mIvBytes);
    }

    private void convertSecretToBytes(char[] secret){
        mKeyBytes = new byte[secret.length];
        for(int i = 0; i < secret.length; i++) {
           mKeyBytes[i] = (byte)(secret[i] & 0x00FF);
        }
    }
}
