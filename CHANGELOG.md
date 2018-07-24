# Change Log
All notable changes to this project will be documented in this file.
The format is based on [Keep a Changelog](http://keepachangelog.com/) 
and this project adheres to [Semantic Versioning](http://semver.org/).

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
