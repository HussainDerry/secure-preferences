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
}
