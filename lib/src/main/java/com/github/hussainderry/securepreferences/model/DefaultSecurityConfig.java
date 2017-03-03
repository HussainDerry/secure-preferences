package com.github.hussainderry.securepreferences.model;

/**
 * Configure security params with the default values.
 *
 * @author Hussain Al-Derry <hussain.derry@gmail.com>
 * @version 1.0
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
