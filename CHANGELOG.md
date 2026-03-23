# Change Log
All notable changes to this project will be documented in this file.
The format is based on [Keep a Changelog](http://keepachangelog.com/) 
and this project adheres to [Semantic Versioning](http://semver.org/).

## [5.2.1] - 2026-03-23

### Fixed

* `GCMParameterSpec` (API 19) replaced with reflection for minSdk 14 compatibility
* `AEADBadTagException` (API 19) replaced with runtime class name check via `BadPaddingException`

## [5.2.0] - 2026-03-23

### Added

* ChaCha20-Poly1305 authenticated encryption (`EncryptionAlgorithm.CHACHA20`, 256-bit key)
* Argon2id key derivation (`DigestType.ARGON2`) — memory-hard, GPU/ASIC resistant
* 5 new algorithm combination tests (37 total)

### Changed

* Renamed internal `pbkdf2()` to `deriveKey()` to reflect support for multiple KDFs
* Renamed `isGcmMode` to `useGcmParameterSpec` for clarity

## [5.1.1] - 2026-03-23

### Fixed

* `getAll()` crash (`ClassCastException`) when preferences contain `StringSet` values

### Changed

* Sample app rewritten to showcase all library features (all data types, getAll, async callbacks, change listeners, remove, clear, close/reinitialize)

## [5.1.0] - 2026-03-23

### Added

* `getAll()` support with persistent encrypted key registry
* Change listeners now receive original key names instead of hashed keys
* Storage format versioning (`v1.salt.iv.ciphertext`) with backward compatibility
* Main-thread callback delivery for `AsyncDataLoader`
* `AsyncDataLoader` constructor overload accepting custom `Handler`
* 20 new instrumentation tests (32 total)

### Changed

* `TripleDES` deprecated (NIST SP 800-67 Rev 2), will be removed in v6
* ProGuard/R8 consumer rules now shipped with the AAR
* Use-after-close guard on `Cryptor` prevents silent data corruption

### Fixed

* `DataTypeTest` was reading wrong keys for `getLong` and `getFloat` assertions
* All tests now use JUnit `Assert` instead of `assert` keyword

## [5.0.0] - 2026-03-23

### Added

* Domain exception hierarchy (`SecurePreferencesException`, `CipherOperationException`, `DataIntegrityException`, `InvalidConfigurationException`)
* `Closeable` support on `SecurePreferences` and `Cryptor` to zero key material
* Null-check guard clauses on public entry points
* Publishing via JitPack (replaces Maven Central)

### Changed

* **BREAKING:** `SecurityConfig.Builder` now accepts `char[]` instead of `String`
* AES-GCM now uses `GCMParameterSpec` with 128-bit auth tag (was `IvParameterSpec`)
* Default PBKDF2 iterations raised from 1,000 to 210,000 (minimum 10,000)
* Migrated from SpongyCastle to BouncyCastle (`bcprov-jdk18on:1.78.1`)
* Migrated from Android Support Library to AndroidX
* Upgraded AGP 3.1.3 to 7.4.2, Gradle 5.1.1 to 7.5.1
* Switched distribution from Maven Central to JitPack
* `AsyncDataLoader` uses bounded thread pool with `shutdown()` method
* All classes marked `final` where appropriate
* Defensive copies on `getPassword()` and `getKeySizes()`

### Removed

* Retrolambda plugin (AGP 7 has native Java 8 desugaring)
* JCenter repository (sunset)
* Travis CI config (replaced by GitHub Actions)

## [4.0.0] - 2019-07-28

### Added

* `AsyncDataLoader` now provides callback style loading via `DataCallback<T>`

### Changed

* `CipherServiceImpl` now uses `GCM` mode for `AES` 

## [3.0.0] - 2018-07-24

### Added

* `CipherService` that supports different encryption algorithms.
* TripleDES support.

### Changed

* Modified `Cryptor` and `SecurityConfig` to support the new algorithm selection feature.

### Removed

* AES related classes in favor of the new `CipherService`.

## [2.1.1] - 2017 -06-23

### Changed
- Modified `Cryptor` to use the `String.format()` method instead of using the StringBuilder class.

## [2.1.0] - 2017 -03-29
### Added
- Created `AsynDataLoader` to help get data from preferences asynchronously.

### Changed
- Sample `MainActivity` modified to use `AsyncDataLoader`.

## [2.0.0] - 2017-03-19
### Changed
- Modified `Cryptor` to generate a single encryption key per session for better performance.

### Fixed
- Fixed key inequality issue caused by Base64 encoding configurations.

## [1.0.0] - 2017-03-17
### Added
- Semantic Versioning Compliance

## [0.0.4] - 2017-03-16
### Changed
- Default `SecurityConfig` values changed.
- `CipherAES` is now non-static.
- Removed synchronization from `Cryptor` for better performance.

## [0.0.3] - 2017-03-14
### Fixed
- Solved issue #1 (Manifest merge failed).

## [0.0.2] - 2017-03-04
### Added
- AES key size added to `SecurityConfig`.
- JavaDoc for `SecurityConfig.Builder` 

### Changed
- Sample `MainActivity` modified to configure AES key size.

## [0.0.1] - 2017-03-03
