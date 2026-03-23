## Secure Preferences

[![](https://jitpack.io/v/HussainDerry/secure-preferences.svg)](https://jitpack.io/#HussainDerry/secure-preferences)

A simple library that provides an encrypted version of the Android shared preferences.
Current supported algorithms (AES-GCM, TripleDES-CBC) with PBKDF2 key derivation (SHA1/SHA256/SHA512).

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
implementation 'com.github.HussainDerry:secure-preferences:v5.0.0'
```

## Sample Usage
### Configuring Encryption Parameters
```java
// Minimum Configurations
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

### Getting Data Asynchronously

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
		System.out.println(data);
	}
});
```

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
