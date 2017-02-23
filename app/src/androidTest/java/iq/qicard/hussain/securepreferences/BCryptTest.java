package iq.qicard.hussain.securepreferences;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import java.security.SecureRandom;

import iq.qicard.hussain.securepreferences.util.BCryptHelper;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class BCryptTest {

    @Test
    public void printTest() throws Exception{
        String key = "thisisastrongpassword";
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        String hashed = BCryptHelper.hashPasswordToBase64(key.getBytes("UTF-8"), salt, 12);
        assertNotNull(hashed);
        Log.d("BCrypt-Test", "Generated: " + hashed);
        Log.d("BCrypt-Test", "Size: " + hashed.length());
        throw new AssertionError("Size: " + hashed.length());
    }
}
