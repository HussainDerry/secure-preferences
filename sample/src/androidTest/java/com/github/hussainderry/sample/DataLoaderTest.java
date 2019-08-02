package com.github.hussainderry.sample;

import com.github.hussainderry.securepreferences.SecurePreferences;
import com.github.hussainderry.securepreferences.model.DigestType;
import com.github.hussainderry.securepreferences.model.EncryptionAlgorithm;
import com.github.hussainderry.securepreferences.model.SecurityConfig;
import com.github.hussainderry.securepreferences.util.DataCallback;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

/**
 * @author Hussain Al-Derry <hussain.derry@gmail.com>
 */
@RunWith(AndroidJUnit4.class)
public class DataLoaderTest{

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
	public void test(){
		Context context = InstrumentationRegistry.getTargetContext();
		SecurePreferences mPreferences = SecurePreferences.getInstance(context, "myfile", mConfig);
		SecurePreferences.Editor mEditor = mPreferences.edit();

		mEditor.putString("name", "John Doe");
		mEditor.commit();

		mPreferences.getAsyncDataLoader().getString("name", null, new DataCallback<String>(){

			@Override
			public void onDataLoaded(String data){
				assert "John Doe".equals(data);
			}
		});
	}

}
