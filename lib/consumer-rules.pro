# BouncyCastle - required for PBKDF2 key derivation
-keep class org.bouncycastle.** { *; }
-dontwarn org.bouncycastle.**

# Secure Preferences exception hierarchy
-keep class com.github.hussainderry.securepreferences.exception.** { *; }
