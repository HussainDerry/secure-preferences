## Secure Preferences

A simple library that provides an AES encrypted version of the Android shared preferences

## Setup

#### Gradle

`compile 'com.github.hussainderry:secure-preferences:0.0.2'`

## Sample Usage
```java
// Minimum Configurations
SecurityConfig minimumConfig = new SecurityConfig.Builder(PASSWORD)
        .build();

// Full Configurations
SecurityConfig fullConfig = new SecurityConfig.Builder(PASSWORD)
        .setAesKeySize(256) // key size in bits
        .setPbkdf2SaltSize(32) // salt size in bytes
        .setPbkdf2Iterations(24000)
        .setDigestType(DigestType.SHA256)
        .build();

SecurePreferences mPreferences = SecurePreferences.getInstance(MainActivity.this, FILENAME, minimumConfig);
SecurePreferences.Editor mEditor = mPreferences.edit();
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
