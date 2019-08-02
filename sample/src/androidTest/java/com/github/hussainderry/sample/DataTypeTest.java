package com.github.hussainderry.sample;

import com.github.hussainderry.securepreferences.SecurePreferences;
import com.github.hussainderry.securepreferences.model.DigestType;
import com.github.hussainderry.securepreferences.model.EncryptionAlgorithm;
import com.github.hussainderry.securepreferences.model.SecurityConfig;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Hussain Al-Derry <hussain.derry@gmail.com>
 */
@RunWith(AndroidJUnit4.class)
public class DataTypeTest{

	private static final String PASSWORD = "temp_pa$$word";
	private static SecurityConfig mConfig;

	@BeforeClass
	public static void init(){
		mConfig = new SecurityConfig.Builder(PASSWORD)
				.setDigestType(DigestType.SHA512)
				.setEncryptionAlgorithm(EncryptionAlgorithm.AES)
				.setPbkdf2Iterations(10_000)
				.setKeySize(128)
				.setPbkdf2SaltSize(32)
				.build();
	}

	@Test
	public void testNumbers(){
		Context context = InstrumentationRegistry.getTargetContext();
		SecurePreferences mPreferences = SecurePreferences.getInstance(context, "myfile", mConfig);
		SecurePreferences.Editor mEditor = mPreferences.edit();

		mEditor.putInt("test-int", 44);
		mEditor.putLong("test-long", 55);
		mEditor.putFloat("test-float", 2.5f);
		mEditor.commit();

		assert 44 == mPreferences.getInt("test-int", -1);
		assert 55 == mPreferences.getLong("test-int", -1);
		assert 2.5 == mPreferences.getFloat("test-int", -1);
	}

	@Test
	public void testStrings(){
		Context context = InstrumentationRegistry.getTargetContext();
		SecurePreferences mPreferences = SecurePreferences.getInstance(context, "myfile", mConfig);
		SecurePreferences.Editor mEditor = mPreferences.edit();

		Set<String> mSet = new HashSet<>(3);
		mSet.add("str1");
		mSet.add("str2");
		mSet.add("str3");

		mEditor.putString("test-str", "Hello World!");
		mEditor.putStringSet("test-strset", mSet);
		mEditor.commit();

		assert "Hello World!".equals(mPreferences.getString("test-str", "-1"));
		assert mSet.equals(mPreferences.getStringSet("test-strset", null));
	}

	@Test
	public void testBoolean(){
		Context context = InstrumentationRegistry.getTargetContext();
		SecurePreferences mPreferences = SecurePreferences.getInstance(context, "myfile", mConfig);
		SecurePreferences.Editor mEditor = mPreferences.edit();

		mEditor.putBoolean("isLoggedIn", true);
		mEditor.commit();

		assert mPreferences.getBoolean("isLoggedIn", false);
	}

}
