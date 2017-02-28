package com.gmail.derry.hussain.securepreferences.model;

/**
 * Configure security params with the default values.
 *
 * @author Hussain Al-Derry
 * @version 0.1
 * @since 2/26/17
 */
public class DefaultSecurityConfig extends SecurityConfig{

    private static final int DEFAULT_ITERATIONS = 10000;
    private static final int DEFAULT_SALT_SIZE = 64;
    private static final DigestType DEFAULT_DIGEST = DigestType.SHA256;

    /**
     * @param password The base password to use
     * */
    public DefaultSecurityConfig(char[] password) {
        super(password, DEFAULT_ITERATIONS, DEFAULT_SALT_SIZE, DEFAULT_DIGEST);
    }
}
