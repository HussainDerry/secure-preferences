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

package com.github.hussainderry.securepreferences.model;

import java.util.Arrays;

/**
 * Used to configure encryption parameters.
 * @author Hussain Al-Derry <hussain.derry@gmail.com>
 * @version 1.0
 * */
public class SecurityConfig {

    private final char[] mPassword;
    private final int iPBKDF2_Iterations;
    private final int iSaltSize;
    private final DigestType mDigestType;
    private final int aesKeySize;

    /**
     * @param password The base password to use
     * @param aesKeySize The size of the AES key
     * @param iPBKDF2Iterations Number of iterations for PBKDF2
     * @param saltSize The size of the salt for PBKDF2
     * @param digestType The digest type to use with PBKDF2
     * */
    public SecurityConfig(char[] password, int aesKeySize, int iPBKDF2Iterations, int saltSize, DigestType digestType) {
        this.mPassword = Arrays.copyOf(password, password.length);
        this.iPBKDF2_Iterations = iPBKDF2Iterations;
        this.mDigestType = digestType;
        this.iSaltSize = saltSize;
        if(aesKeySize == 128 || aesKeySize == 192 || aesKeySize == 256){
            this.aesKeySize = aesKeySize;
        }else{
            throw new IllegalArgumentException("Invalid AES key size");
        }
    }

    public char[] getPassword() {
        return mPassword;
    }

    public int getPBKDF2Iterations() {
        return iPBKDF2_Iterations;
    }

    public DigestType getDigestType() {
        return mDigestType;
    }

    public int getSaltSize() {
        return iSaltSize;
    }

    public int getAesKeySize(){
        return aesKeySize;
    }

    public static class Builder{

        private static final int DEFAULT_ITERATIONS = 1000;
        private static final int DEFAULT_SALT_SIZE = 32;
        private static final int DEFAULT_AES_KEY_SIZE = 128;
        private static final DigestType DEFAULT_DIGEST = DigestType.SHA256;

        private char[] password;
        private int saltSize = -1;
        private int iterations = -1;
        private DigestType digest = null;
        private int aesKeySize = -1;

        public Builder(String password){
            if(password == null){
                throw new IllegalArgumentException("Password cannot be null!");
            }
            this.password = password.toCharArray();
        }

        /**
         * Set the PBKDF2 iterations
         * @param iterations The number of iterations
         * */
        public Builder setPbkdf2Iterations(int iterations){
            if(iterations < 0){
                throw new IllegalArgumentException("Iterations cannot be less than zero!");
            }
            this.iterations = iterations;
            return this;
        }

        /**
         * Set the PBKDF2 salt size in bytes
         * @param saltSize The salt size (in bytes)
         * */
        public Builder setPbkdf2SaltSize(int saltSize){
            if(saltSize < 8 || (saltSize % 8) != 0){
                throw new IllegalArgumentException("Illegal salt size!");
            }

            this.saltSize = saltSize;
            return this;
        }

        public Builder setDigestType(DigestType digestType){
            this.digest = digestType;
            return this;
        }

        /**
         * Set the AES key size in bits
         * @param keySize The key size (in bits)
         * */
        public Builder setAesKeySize(int keySize){
            this.aesKeySize = keySize;
            return this;
        }

        public SecurityConfig build(){
            int finalIterations = iterations != -1 ? iterations : DEFAULT_ITERATIONS;
            int finalSaltSize = saltSize != -1 ? saltSize : DEFAULT_SALT_SIZE;
            DigestType finalDigest = digest != null ? digest : DEFAULT_DIGEST;
            int finalKeySize = aesKeySize != -1 ? aesKeySize : DEFAULT_AES_KEY_SIZE;

            return new SecurityConfig(password, finalKeySize, finalIterations, finalSaltSize, finalDigest);
        }
    }
}
