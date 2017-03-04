package com.github.hussainderry.sample;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.github.hussainderry.securepreferences.SecurePreferences;
import com.github.hussainderry.securepreferences.model.DigestType;
import com.github.hussainderry.securepreferences.model.SecurityConfig;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Hussain Al-Derry <hussain.derry@gmail.com>
 */
@RunWith(AndroidJUnit4.class)
public class SecurityConfigTest {

    private static final String PASSWORD = "temp_pa$$word";
    private static final String MSG = "Hello World!";

    @Test
    public void testAes128Sha512() throws Exception{
        SecurityConfig mConfig = new SecurityConfig.Builder(PASSWORD)
                .setDigestType(DigestType.SHA512)
                .setAesKeySize(128)
                .build();

        Context context = InstrumentationRegistry.getTargetContext();
        SecurePreferences mPreferences = SecurePreferences.getInstance(context, "myfile", mConfig);
        SecurePreferences.Editor mEditor = mPreferences.edit();
        mEditor.putString("msg", MSG).commit();

        String decrypted = mPreferences.getString("msg", null);
        Assert.assertEquals(MSG, decrypted);
    }

    @Test
    public void testAes192Sha256() throws Exception{
        SecurityConfig mConfig = new SecurityConfig.Builder(PASSWORD)
                .setDigestType(DigestType.SHA256)
                .setAesKeySize(256)
                .build();

        Context context = InstrumentationRegistry.getTargetContext();
        SecurePreferences mPreferences = SecurePreferences.getInstance(context, "myfile", mConfig);
        SecurePreferences.Editor mEditor = mPreferences.edit();
        mEditor.putString("msg", MSG).commit();

        String decrypted = mPreferences.getString("msg", null);
        Assert.assertEquals(MSG, decrypted);
    }

    @Test
    public void testAes256Sha512() throws Exception{
        SecurityConfig mConfig = new SecurityConfig.Builder(PASSWORD)
                .setDigestType(DigestType.SHA512)
                .setAesKeySize(256)
                .setPbkdf2Iterations(80000)
                .build();

        Context context = InstrumentationRegistry.getTargetContext();
        SecurePreferences mPreferences = SecurePreferences.getInstance(context, "myfile", mConfig);
        SecurePreferences.Editor mEditor = mPreferences.edit();
        mEditor.putString("msg", MSG).commit();

        String decrypted = mPreferences.getString("msg", null);
        Assert.assertEquals(MSG, decrypted);
    }

    @Test
    public void testAes256Sha1() throws Exception{
        SecurityConfig mConfig = new SecurityConfig.Builder(PASSWORD)
                .setDigestType(DigestType.SHA1)
                .setAesKeySize(256)
                .build();

        Context context = InstrumentationRegistry.getTargetContext();
        SecurePreferences mPreferences = SecurePreferences.getInstance(context, "myfile", mConfig);
        SecurePreferences.Editor mEditor = mPreferences.edit();
        mEditor.putString("msg", MSG).commit();

        String decrypted = mPreferences.getString("msg", null);
        Assert.assertEquals(MSG, decrypted);
    }

    @Test
    public void testAes128Sha1() throws Exception{
        SecurityConfig mConfig = new SecurityConfig.Builder(PASSWORD)
                .setDigestType(DigestType.SHA1)
                .setAesKeySize(128)
                .build();

        Context context = InstrumentationRegistry.getTargetContext();
        SecurePreferences mPreferences = SecurePreferences.getInstance(context, "myfile", mConfig);
        SecurePreferences.Editor mEditor = mPreferences.edit();
        mEditor.putString("msg", MSG).commit();

        String decrypted = mPreferences.getString("msg", null);
        Assert.assertEquals(MSG, decrypted);
    }

    @Test
    public void testDefault() throws Exception{
        SecurityConfig mConfig = new SecurityConfig.Builder(PASSWORD)
                .build();

        Context context = InstrumentationRegistry.getTargetContext();
        SecurePreferences mPreferences = SecurePreferences.getInstance(context, "myfile", mConfig);
        SecurePreferences.Editor mEditor = mPreferences.edit();
        mEditor.putString("msg", MSG).commit();

        String decrypted = mPreferences.getString("msg", null);
        Assert.assertEquals(MSG, decrypted);
    }

}
