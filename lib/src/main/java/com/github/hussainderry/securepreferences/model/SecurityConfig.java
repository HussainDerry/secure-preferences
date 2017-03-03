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

    /**
     * @param password The base password to use
     * @param iPBKDF2Iterations Number of iterations for PBKDF2
     * @param saltSize The size of the salt for PBKDF2
     * @param digestType The digest type to use with PBKDF2
     * */
    public SecurityConfig(char[] password, int iPBKDF2Iterations, int saltSize, DigestType digestType) {
        this.mPassword = Arrays.copyOf(password, password.length);
        this.iPBKDF2_Iterations = iPBKDF2Iterations;
        this.mDigestType = digestType;
        this.iSaltSize = saltSize;
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

    public static class Builder{

        private static final int DEFAULT_ITERATIONS = 10000;
        private static final int DEFAULT_SALT_SIZE = 64;
        private static final DigestType DEFAULT_DIGEST = DigestType.SHA256;

        private char[] password;
        private int saltSize = -1;
        private int iterations = -1;
        private DigestType digest = null;

        public Builder(String password){
            this.password = password.toCharArray();
        }

        public Builder setPbkdf2Iterations(int iterations){
            this.iterations = iterations;
            return this;
        }

        public Builder setPbkdf2SaltSize(int saltSize){
            this.saltSize = saltSize;
            return this;
        }

        public Builder setDigestType(DigestType digestType){
            this.digest = digestType;
            return this;
        }

        public SecurityConfig build(){
            int finalIterations = iterations != -1 ? iterations : DEFAULT_ITERATIONS;
            int finalSaltSize = saltSize != -1 ? saltSize : DEFAULT_SALT_SIZE;
            DigestType finalDigest = digest != null ? digest : DEFAULT_DIGEST;

            return new SecurityConfig(password, finalIterations, finalSaltSize, finalDigest);
        }
    }
}
