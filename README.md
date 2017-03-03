## Overview

Library that provides an AES-256 encrypted version of the Android shared preferences

## Setup

#### Gradle

`compile 'com.github.hussainderry:secure-preferences:0.0.1'`

#### Maven
```
<dependency>
    <groupId>com.github.hussainderry</groupId>
    <artifactId>secure-preferences</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Sample Usage
```java
// initializing with minimum configurations
SecurePreferences mPreferences = SecurePreferences.getInstance(MainActivity.this, "myfile", "pa$$word");
SecurePreferences.Editor mEditor = mPreferences.edit();

// initializing with full security configurations
char[] password = "pa$$word".toCharArray();
int pbkdf2_iterations = 25000;
int pbkdf2_salt_size = 32;
DigestType digestType = DigestType.SHA256;
SecurityConfig mConfig = new SecurityConfig(password, pbkdf2_iterations, pbkdf2_salt_size, digestType);

SecurePreferences mPreferences = SecurePreferences.getInstance(MainActivity.this, "myfile", mConfig);
SecurePreferences.Editor mEditor = mPreferences.edit();
```

## Developed By

* Hussain Al-Derry 
 
&nbsp;&nbsp;&nbsp;**Email** - hussain.derry@gmail.com

## License

```
Copyright 2015 Etienne Lawlor

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
