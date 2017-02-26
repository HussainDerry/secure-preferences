package iq.qicard.hussain.securepreferences.crypto;

import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;

/**
 * Generates password hashes using PBKDF2.
 *
 * @author Hussain Al-Derry
 * @version 0.1
 */
public class PBKDF2Helper {

    /**
     * Computes the PBKDF2 hash of a password with SHA1 HMAC.
     *
     * @param password   the password to hash.
     * @param salt       the salt
     * @param iterations the iteration count (slowness factor)
     * @param size      the length of the hash to compute in bits
     * @return the PBDKF2 hash of the password
     */
    public static byte[] hashUsingPBKDF2WithSHA1(char[] password, byte[] salt, int iterations, int size){
        byte[] passwordBytes = PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(password);
        PKCS5S2ParametersGenerator mGenerator = new PKCS5S2ParametersGenerator(new SHA1Digest());
        mGenerator.init(passwordBytes, salt, iterations);
        return ((KeyParameter) mGenerator.generateDerivedParameters(size)).getKey();
    }

    /**
     * Computes the PBKDF2 hash of a password with SHA256 HMAC.
     *
     * @param password   the password to hash.
     * @param salt       the salt
     * @param iterations the iteration count (slowness factor)
     * @param size      the length of the hash to compute in bits
     * @return the PBDKF2 hash of the password
     */
    public static byte[] hashUsingPBKDF2WithSHA256(char[] password, byte[] salt, int iterations, int size){
        byte[] passwordBytes = PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(password);
        PKCS5S2ParametersGenerator mGenerator = new PKCS5S2ParametersGenerator(new SHA256Digest());
        mGenerator.init(passwordBytes, salt, iterations);
        return ((KeyParameter) mGenerator.generateDerivedParameters(size)).getKey();
    }

    /**
     * Computes the PBKDF2 hash of a password with SHA512 HMAC.
     *
     * @param password   the password to hash.
     * @param salt       the salt
     * @param iterations the iteration count (slowness factor)
     * @param size      the length of the hash to compute in bits
     * @return the PBDKF2 hash of the password
     */
    public static byte[] hashUsingPBKDF2WithSHA512(char[] password, byte[] salt, int iterations, int size){
        byte[] passwordBytes = PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(password);
        PKCS5S2ParametersGenerator mGenerator = new PKCS5S2ParametersGenerator(new SHA512Digest());
        mGenerator.init(passwordBytes, salt, iterations);
        return ((KeyParameter) mGenerator.generateDerivedParameters(size)).getKey();
    }

}
