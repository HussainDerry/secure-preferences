## Secure Preferences

[![](https://jitpack.io/v/HussainDerry/secure-preferences.svg)](https://jitpack.io/#HussainDerry/secure-preferences)

An encrypted drop-in replacement for Android's `SharedPreferences`. Full API compatibility including `getAll()` and change listeners. Password-based encryption with configurable algorithms and PBKDF2 key derivation.

### Why this library?

| | secure-preferences | Jetpack EncryptedSharedPreferences |
|---|---|---|
| minSdk | **14** | 23 |
| Key derivation | Password-based (PBKDF2) | Android KeyStore (device-bound) |
| Data portability | Yes (same password = same keys) | No |
| Async loading | Built-in Future + Callback | No |
| Configuration | Full control (algo, iterations, key size) | Opinionated defaults only |
| `getAll()` support | Yes | Yes |

## Setup

Add the JitPack repository to your `settings.gradle`:

```groovy
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency to your module `build.gradle`:

```groovy
implementation 'com.github.HussainDerry:secure-preferences:v5.1.1'
```

## Sample Usage

### Configuring Encryption Parameters
```java
// Minimum Configurations (AES-128-GCM, SHA256, 210k iterations)
SecurityConfig minimumConfig = new SecurityConfig.Builder("password".toCharArray())
        .build();

// Full Configurations
SecurityConfig fullConfig = new SecurityConfig.Builder("password".toCharArray())
        .setKeySize(256) // key size in bits
        .setPbkdf2SaltSize(32) // salt size in bytes
        .setPbkdf2Iterations(24000)
        .setEncryptionAlgorithm(EncryptionAlgorithm.AES)
        .setDigestType(DigestType.SHA256)
        .build();

SecurePreferences mPreferences = SecurePreferences.getInstance(MainActivity.this, FILENAME, minimumConfig);
SecurePreferences.Editor mEditor = mPreferences.edit();
```

### Reading and Writing Data
```java
// Write
mEditor.putString("token", "abc123");
mEditor.putInt("loginCount", 5);
mEditor.putBoolean("rememberMe", true);
mEditor.commit();

// Read
String token = mPreferences.getString("token", null);
int count = mPreferences.getInt("loginCount", 0);

// Get all stored values
Map<String, ?> allValues = mPreferences.getAll();

// Clean up key material when done
mPreferences.close();
```

### Getting Data Asynchronously

Callbacks are delivered on the main thread, safe for UI updates.

#### Using `Future<T>`

```java
AsyncDataLoader mAsyncLoader = mPreferences.getAsyncDataLoader();
Future<String> mFuture = mAsyncLoader.getString("name", "default");

/* Do something while the data is being fetched */

if(mFuture.isDone()){
    String data = mFuture.get();
}
```

#### Using `DataCallback<T>`

```java
mPreferences.getAsyncDataLoader().getString("key", "default", new DataCallback<String>(){
	@Override
	public void onDataLoaded(String data){
		// Runs on main thread
		textView.setText(data);
	}
});
```

### Change Listeners

Listeners receive the original key names (not hashed):

```java
mPreferences.registerOnSharedPreferenceChangeListener(
    new SharedPreferences.OnSharedPreferenceChangeListener(){
        @Override
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key){
            Log.d("SecurePrefs", "Changed: " + key);
        }
    }
);
```

## Error Handling

The library provides specific exception types for different failure modes:

- `InvalidConfigurationException` -- bad key size, iterations below minimum, invalid algorithm
- `CipherOperationException` -- cipher initialization or operation failure
- `DataIntegrityException` -- wrong password, tampered data, or malformed ciphertext

## Developed By

* Hussain Al-Derry

&nbsp;&nbsp;&nbsp;**Email** - hussain.derry@gmail.com

## License

```
Copyright 2017 Hussain Al-Derry

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
