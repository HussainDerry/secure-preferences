package iq.qicard.hussain.securepreferences;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.primitives.Booleans;
import android.support.test.filters.MediumTest;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestSecurePreferences {

    private static final String FILE_NAME = "securefile";
    private static final String PASSWORD = "!@#password#@!";
    private static SecurePreferences mPreferences;

    public TestSecurePreferences(){

    }

    @BeforeClass
    public static void testLoadContext() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("iq.qicard.hussain.securepreferences", appContext.getPackageName());
        mPreferences = SecurePreferences.getInstance(appContext, FILE_NAME, PASSWORD);
    }

    @Test
    public void testPrefsInitialization() throws Exception {
        assertNotNull(mPreferences);
    }

    @Test
    public void testString() throws Exception{
        String key = "string_key";
        String data = UUID.randomUUID().toString();
        SecurePreferences.Editor mEditor = mPreferences.edit();
        mEditor.putString(key, data).commit();

        String retData = mPreferences.getString(key, null);
        assertEquals(retData, data);
    }

    @Test
    public void testInt() throws Exception{
        String key = "int_key";
        int data = new SecureRandom().nextInt();
        SecurePreferences.Editor mEditor = mPreferences.edit();
        mEditor.putInt(key, data).commit();

        int retData = mPreferences.getInt(key, -1);
        assertEquals(retData, data);
    }

    @Test
    public void testLong() throws Exception{
        String key = "long_key";
        long data = new SecureRandom().nextLong();
        SecurePreferences.Editor mEditor = mPreferences.edit();
        mEditor.putLong(key, data).commit();

        long retData = mPreferences.getLong(key, -1);
        assertEquals(retData, data);
    }

    @Test
    public void testFloat() throws Exception{
        String key = "float_key";
        float data = new SecureRandom().nextFloat();
        SecurePreferences.Editor mEditor = mPreferences.edit();
        mEditor.putFloat(key, data).commit();

        float retData = mPreferences.getFloat(key, -1);
        assertEquals(retData, data, 0.0001F);
    }

    @Test
    public void testBoolean() throws Exception{
        String key = "boolean_key";
        boolean data = (new SecureRandom().nextDouble() > 0.5);
        SecurePreferences.Editor mEditor = mPreferences.edit();
        mEditor.putBoolean(key, data).commit();

        boolean retData = mPreferences.getBoolean(key, false);
        assertEquals(retData, data);
    }

    @Test
    public void testStringSet() throws Exception{
        Set<String> mSet = new HashSet<>();
        String key = "set_key";
        mSet.add(UUID.randomUUID().toString());
        mSet.add(UUID.randomUUID().toString());
        mSet.add(UUID.randomUUID().toString());

        SecurePreferences.Editor mEditor = mPreferences.edit();
        mEditor.putStringSet(key, mSet).commit();

        Set<String> mRetSet = mPreferences.getStringSet(key, null);
        assertEquals(mRetSet, mSet);
    }
}
