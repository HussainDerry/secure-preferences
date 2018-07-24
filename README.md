## Secure Preferences [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.hussainderry/secure-preferences/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.github.hussainderry/secure-preferences) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Secure%20Preferences-brightgreen.svg?style=plastic)](https://android-arsenal.com/details/1/5403) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/7f81fd82dae444d38e783f72bfd951d5)](https://www.codacy.com/app/hussain.derry/secure-preferences?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=HussainDerry/secure-preferences&amp;utm_campaign=Badge_Grade)

A simple library that provides an encrypted version of the Android shared preferences.
Current supported algorithms (AES, TripleDES).

## Setup

#### Gradle

`compile 'com.github.hussainderry:secure-preferences:3.0.0'`

## Sample Usage
### Configuring Encryption Parameters
```java
// Minimum Configurations
SecurityConfig minimumConfig = new SecurityConfig.Builder(PASSWORD)
        .build();

// Full Configurations
SecurityConfig fullConfig = new SecurityConfig.Builder(PASSWORD)
        .setAesKeySize(256) // key size in bits
        .setPbkdf2SaltSize(32) // salt size in bytes
        .setPbkdf2Iterations(24000)
        .setEncryptionAlgorithm(EncryptionAlgorithm.AES)
        .setDigestType(DigestType.SHA256)
        .build();

SecurePreferences mPreferences = SecurePreferences.getInstance(MainActivity.this, FILENAME, minimumConfig);
SecurePreferences.Editor mEditor = mPreferences.edit();
```

### Getting Data Asynchronously
```java
AsyncDataLoader mAsyncLoader = mPreferences.getAsyncDataLoader();
Future<String> mFuture = mAsyncLoader.getString("name", "default");

/* Do something while the data is being fetched */

if(mFuture.isDone()){
    String data = mFuture.get();
}
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
