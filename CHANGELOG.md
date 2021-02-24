Change Log
==========

Version 1.1.5 *(2021-02-24)*
-----------------------------

* Revert internal changes incompatible with D8 version 2.2 and 2.1.

Version 1.1.4 *(2021-02-12)*
-----------------------------

* Update configuration for compilation with L8 after updates to how
  missing classes are reported
  ([issue 72683872](https://issuetracker.google.com/72683872)).
* Fix `IllegalArgumentException` when formatting time in Burmese with
  `DateTimeFormatter`.

Version 1.1.3 *(2021-02-10)*
-----------------------------

* Allow CHM JDK-7 serialization
  ([issue 179397308](https://issuetracker.google.com/179397308)).

Version 1.1.2 *(2020-12-09)*
-----------------------------

* Ensure that all packages in desugared library starts with j$
  ([Change 56716](https://r8-review.googlesource.com/c/r8/+/56716)).

Version 1.1.1 *(2020-11-11)*
-----------------------------

* Don't remove remove or obfuscate `enum` values from referenced `enum`s
  ([issue 172834444](https://issuetracker.google.com/172834444)).

Version 1.1.0 *(2020-11-02)*
-----------------------------

* Support GSON deserialization of desugared `List` and `Map` types
  ([issue 167649682](https://issuetracker.google.com/167649682)).
  This also requires D8 version 2.1.70.
* Updated the Lint configuration to not include methods which are not
  implemented by the defugared library implementation. This was mainly methods
  on `ConcurrentHashMap`.

Version 1.0.11 *(2020-07-29)*
-----------------------------

 * Disable `SummaryStatistics` conversions. The implementation was relying on
   reflection on members under[Restrictions on non-SDK interfaces](https://developer.android.com/distribute/best-practices/develop/restrictions-non-sdk-interfaces)
   ([Change 52441](https://r8-review.googlesource.com/c/r8/+/52441)).

Version 1.0.10 *(2020-07-28)*
-----------------------------

 * Added support for methods `getTimeZone` and `toZoneId` on
   `java.util.TimeZone`
   ([issue 159275214](https://issuetracker.google.com/159275214)).

Version 1.0.9 *(2020-06-23)*
----------------------------

 * Updated to new configuration format supporting Android Studio 4.0,
   4.1 and 4.2. The configuration format now contains an explicit list
   of wrappers to generate (`wrapper_conversion` section). This is
   done in a backwards compatible way, so only Android Studio 4.1 and
   4.2 will actually use this information ([issue
   157681341](https://issuetracker.google.com/157681341)).

Version 1.0.8 *(2020-06-23)*
----------------------------

 * Fixed `<dependencies>` section in configuration artifact
   ([issue 158502561](https://issuetracker.google.com/158502561)).

Version 1.0.7 *(2020-06-22)*
----------------------------

 * Reverted changes in 1.0.6 to get back support for Android Studio 4.0.
   Content identical to 1.0.5.

Version 1.0.6 *(2020-06-19)*
----------------------------

 * Release with new configuration file only supported by Android Studio 4.1 and
   4.2.

Version 1.0.5 *(2020-03-05)*
----------------------------

 * Initial release for Android Studio 4.0.
