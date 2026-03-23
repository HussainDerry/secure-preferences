/*
 * Copyright 2017 Hussain Al-Derry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.hussainderry.securepreferences.crypto;

import com.github.hussainderry.securepreferences.exception.CipherOperationException;
import com.github.hussainderry.securepreferences.exception.DataIntegrityException;
import com.github.hussainderry.securepreferences.exception.InvalidConfigurationException;
import com.github.hussainderry.securepreferences.model.EncryptionAlgorithm;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/***
 * @author Hussain Al-Derry <hussain.derry@gmail.com>
 */
public final class CipherServiceImpl implements CipherService{

    private static final int GCM_TAG_LENGTH_BITS = 128;

    private final String mEncryptionAlgorithm;
    private final int ivSize;
    private final boolean isGcmMode;
    private final Cipher mCipher;

    static CipherService getInstance(EncryptionAlgorithm algorithm){
        if(algorithm == null){
            throw new InvalidConfigurationException("Algorithm cannot be null");
        }

        switch(algorithm){

            case AES:{
                return new CipherServiceImpl("AES", "GCM", "NoPadding", 12, true);
            }

            case TripleDES:{
                return new CipherServiceImpl("DESede", "CBC", "PKCS5Padding", 8, false);
            }

            default:{
                throw new InvalidConfigurationException("Unknown Algorithm: " + algorithm);
            }

        }
    }

    private CipherServiceImpl(String algorithm, String blockChaining, String paddingType, int ivSize, boolean isGcmMode) {
        this.mEncryptionAlgorithm = algorithm;
        this.ivSize = ivSize;
        this.isGcmMode = isGcmMode;

        try{
            String encryptionMode = String.format("%s/%s/%s", algorithm, blockChaining, paddingType);
            mCipher = Cipher.getInstance(encryptionMode);
        }catch(NoSuchAlgorithmException | NoSuchPaddingException e){
            throw new InvalidConfigurationException("Unable to initialize cipher, mode might not be supported", e);
        }
    }

    @Override
    public int getIVSize() {
        return ivSize;
    }

    @Override
    public byte[] encrypt(byte[] key, byte[] iv, byte[] data) {
        if(key == null || iv == null || data == null){
            throw new IllegalArgumentException("Key, IV, and data cannot be null");
        }

        synchronized(mCipher){
            try{
                mCipher.init(Cipher.ENCRYPT_MODE, generateSecretKeySpec(key), generateParameterSpec(iv));
                return mCipher.doFinal(data);
            }catch(InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e){
                throw new CipherOperationException("Encryption failed", e);
            }
        }
    }

    @Override
    public byte[] decrypt(byte[] key, byte[] iv, byte[] data) {
        if(key == null || iv == null || data == null){
            throw new IllegalArgumentException("Key, IV, and data cannot be null");
        }

        synchronized(mCipher){
            try{
                mCipher.init(Cipher.DECRYPT_MODE, generateSecretKeySpec(key), generateParameterSpec(iv));
                return mCipher.doFinal(data);
            }catch(AEADBadTagException e){
                throw new DataIntegrityException("Authentication failed: wrong password or tampered data", e);
            }catch(InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e){
                throw new CipherOperationException("Decryption failed", e);
            }
        }
    }

    private SecretKey generateSecretKeySpec(byte[] key){
        return new SecretKeySpec(key, mEncryptionAlgorithm);
    }

    private AlgorithmParameterSpec generateParameterSpec(byte[] iv){
        if(isGcmMode){
            return new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv);
        }
        return new IvParameterSpec(iv);
    }
}
