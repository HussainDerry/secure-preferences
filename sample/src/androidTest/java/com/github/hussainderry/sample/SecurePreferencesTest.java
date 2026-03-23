package com.github.hussainderry.sample;

import com.github.hussainderry.securepreferences.SecurePreferences;
import com.github.hussainderry.securepreferences.exception.DataIntegrityException;
import com.github.hussainderry.securepreferences.exception.InvalidConfigurationException;
import com.github.hussainderry.securepreferences.model.DigestType;
import com.github.hussainderry.securepreferences.model.EncryptionAlgorithm;
import com.github.hussainderry.securepreferences.model.SecurityConfig;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Expanded test suite for SecurePreferences.
 */
@RunWith(AndroidJUnit4.class)
public class SecurePreferencesTest{

	private static final String PASSWORD = "test_pa$$word";
	private static final String FILENAME = "test_prefs";
	private Context mContext;
	private SecurityConfig mConfig;

	@Before
	public void setUp(){
		mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		mConfig = new SecurityConfig.Builder(PASSWORD.toCharArray())
				.setDigestType(DigestType.SHA256)
				.setEncryptionAlgorithm(EncryptionAlgorithm.AES)
				.setPbkdf2Iterations(10_000)
				.setKeySize(128)
				.build();
		// Clear prefs before each test
		mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE).edit().clear().commit();
	}

	@After
	public void tearDown(){
		mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE).edit().clear().commit();
	}

	/* --- getAll() tests --- */

	@Test
	public void getAll_returnsAllStoredValues(){
		SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		SecurePreferences.Editor editor = prefs.edit();
		editor.putString("name", "Alice");
		editor.putString("city", "Dubai");
		editor.putInt("age", 30);
		editor.commit();

		Map<String, ?> all = prefs.getAll();
		Assert.assertEquals(3, all.size());
		Assert.assertEquals("Alice", all.get("name"));
		Assert.assertEquals("Dubai", all.get("city"));
		Assert.assertEquals("30", all.get("age"));
	}

	@Test
	public void getAll_emptyPreferences_returnsEmptyMap(){
		SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		Map<String, ?> all = prefs.getAll();
		Assert.assertTrue(all.isEmpty());
	}

	@Test
	public void getAll_afterRemove_excludesRemovedKey(){
		SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		prefs.edit().putString("a", "1").putString("b", "2").commit();
		prefs.edit().remove("a").commit();

		Map<String, ?> all = prefs.getAll();
		Assert.assertEquals(1, all.size());
		Assert.assertNull(all.get("a"));
		Assert.assertEquals("2", all.get("b"));
	}

	@Test
	public void getAll_afterClear_returnsEmptyMap(){
		SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		prefs.edit().putString("x", "y").commit();
		prefs.edit().clear().commit();

		Map<String, ?> all = prefs.getAll();
		Assert.assertTrue(all.isEmpty());
	}

	/* --- contains() and remove() tests --- */

	@Test
	public void contains_returnsTrueForStoredKey(){
		SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		prefs.edit().putString("key1", "value1").commit();
		Assert.assertTrue(prefs.contains("key1"));
	}

	@Test
	public void contains_returnsFalseForMissingKey(){
		SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		Assert.assertFalse(prefs.contains("nonexistent"));
	}

	@Test
	public void remove_deletesKey(){
		SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		prefs.edit().putString("del", "value").commit();
		Assert.assertTrue(prefs.contains("del"));

		prefs.edit().remove("del").commit();
		Assert.assertFalse(prefs.contains("del"));
		Assert.assertNull(prefs.getString("del", null));
	}

	/* --- Edge cases --- */

	@Test
	public void emptyString_roundTrip(){
		SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		prefs.edit().putString("empty", "").commit();
		Assert.assertEquals("", prefs.getString("empty", "default"));
	}

	@Test
	public void unicodeString_roundTrip(){
		SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		String unicode = "\u0645\u0631\u062D\u0628\u0627 \uD83D\uDE00";
		prefs.edit().putString("unicode", unicode).commit();
		Assert.assertEquals(unicode, prefs.getString("unicode", null));
	}

	@Test
	public void defaultValue_returnedForMissingKey(){
		SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		Assert.assertEquals("fallback", prefs.getString("missing", "fallback"));
		Assert.assertEquals(42, prefs.getInt("missing", 42));
		Assert.assertEquals(99L, prefs.getLong("missing", 99L));
		Assert.assertEquals(1.5f, prefs.getFloat("missing", 1.5f), 0.001f);
		Assert.assertFalse(prefs.getBoolean("missing", false));
	}

	/* --- Wrong password --- */

	@Test(expected = DataIntegrityException.class)
	public void wrongPassword_throwsDataIntegrityException(){
		SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		prefs.edit().putString("secret", "data").commit();

		SecurityConfig wrongConfig = new SecurityConfig.Builder("wrong_password".toCharArray())
				.setDigestType(DigestType.SHA256)
				.setEncryptionAlgorithm(EncryptionAlgorithm.AES)
				.setPbkdf2Iterations(10_000)
				.setKeySize(128)
				.build();

		SecurePreferences wrongPrefs = SecurePreferences.getInstance(mContext, FILENAME, wrongConfig);
		wrongPrefs.getString("secret", null);
	}

	/* --- Use-after-close --- */

	@Test(expected = IllegalStateException.class)
	public void useAfterClose_encrypt_throws(){
		SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		prefs.close();
		prefs.edit().putString("key", "value").commit();
	}

	@Test(expected = IllegalStateException.class)
	public void useAfterClose_decrypt_throws(){
		SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		prefs.edit().putString("key", "value").commit();
		prefs.close();
		prefs.getString("key", null);
	}

	/* --- Invalid configuration --- */

	@Test(expected = InvalidConfigurationException.class)
	public void invalidKeySize_throwsInvalidConfigurationException(){
		new SecurityConfig.Builder(PASSWORD.toCharArray())
				.setKeySize(64)
				.build();
	}

	@Test(expected = InvalidConfigurationException.class)
	public void iterationsBelowMinimum_throwsInvalidConfigurationException(){
		new SecurityConfig.Builder(PASSWORD.toCharArray())
				.setPbkdf2Iterations(500)
				.build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullPassword_throwsIllegalArgument(){
		new SecurityConfig.Builder(null);
	}

	/* --- Change listener --- */

	@Test
	public void changeListener_receivesOriginalKey() throws InterruptedException{
		SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<String> receivedKey = new AtomicReference<>();

		SharedPreferences.OnSharedPreferenceChangeListener listener =
				new SharedPreferences.OnSharedPreferenceChangeListener(){
					@Override
					public void onSharedPreferenceChanged(SharedPreferences sp, String key){
						receivedKey.set(key);
						latch.countDown();
					}
				};

		prefs.registerOnSharedPreferenceChangeListener(listener);
		prefs.edit().putString("observed_key", "value").commit();

		Assert.assertTrue(latch.await(5, TimeUnit.SECONDS));
		Assert.assertEquals("observed_key", receivedKey.get());

		prefs.unregisterOnSharedPreferenceChangeListener(listener);
	}

	/* --- Concurrent access --- */

	@Test
	public void concurrentReadWrite_noExceptions() throws InterruptedException{
		final SecurePreferences prefs = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		final int threadCount = 8;
		final int opsPerThread = 10;
		final CountDownLatch latch = new CountDownLatch(threadCount);
		final AtomicReference<Throwable> failure = new AtomicReference<>();

		for(int t = 0; t < threadCount; t++){
			final int threadId = t;
			new Thread(new Runnable(){
				@Override
				public void run(){
					try{
						for(int i = 0; i < opsPerThread; i++){
							String key = "t" + threadId + "_k" + i;
							prefs.edit().putString(key, "val" + i).commit();
							String result = prefs.getString(key, null);
							Assert.assertEquals("val" + i, result);
						}
					}catch(Throwable e){
						failure.set(e);
					}finally{
						latch.countDown();
					}
				}
			}).start();
		}

		Assert.assertTrue(latch.await(30, TimeUnit.SECONDS));
		if(failure.get() != null){
			Assert.fail("Concurrent access failed: " + failure.get().getMessage());
		}
	}

	/* --- Format versioning backward compat --- */

	@Test
	public void newFormat_readableAcrossSessions(){
		SecurePreferences prefs1 = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		prefs1.edit().putString("versioned", "hello").commit();

		SecurePreferences prefs2 = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		Assert.assertEquals("hello", prefs2.getString("versioned", null));
	}

	/* --- Key registry persistence across sessions --- */

	@Test
	public void keyRegistry_survivesNewInstance(){
		SecurePreferences prefs1 = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		prefs1.edit().putString("persist1", "a").putString("persist2", "b").commit();

		SecurePreferences prefs2 = SecurePreferences.getInstance(mContext, FILENAME, mConfig);
		Map<String, ?> all = prefs2.getAll();
		Assert.assertEquals(2, all.size());
		Assert.assertEquals("a", all.get("persist1"));
		Assert.assertEquals("b", all.get("persist2"));
	}
}
