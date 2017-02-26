package iq.qicard.hussain.securepreferences.model;

public class SecurityConfig {

    private final char[] mPassword;
    private final int iPBKDF2_Iterations;
    private final int iSaltSize;
    private final DigestType mDigestType;

    public SecurityConfig(char[] password, int PBKDF2_Iterations, int saltSize, DigestType digestType) {
        this.mPassword = password;
        this.iPBKDF2_Iterations = PBKDF2_Iterations;
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
