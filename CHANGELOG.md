Change Log
==========

Version 2.0.0 *(2022-09-16)*
----------------------------

* Release based on JDK-11. Require AGP version 7.4.0-alpha10 or later
  (Android Studio 2022.1.1).
* Supports three different configurations `desugar_jdk_libs_minimal`,
  `desugar_jdk_libs` and `desugar_jdk_libs_nio`.
    * `desugar_jdk_libs_minimal` limits desugaring to package
      `java.util.function` and class `java.util.Optional`.
    * `desugar_jdk_libs` desugars the same APIs as in version `1.1.x` and
    `1.2.x`.
    * `desugar_jdk_libs_nio` includes desugaring of package `java.nio`.


Version 1.2.2 *(2022-08-19)*
----------------------------

* Add support for API level 33.

Version 1.2.1 *(2022-07-08)*
-----------------------------

* Update to cover all APIs up to API level 32 (API level 33 still unsupported).

Version 1.2.0 *(2022-06-21)*
----------------------------

* Release based on JDK-11. Require AGP version 7.3.0-beta03 or later
  (Android Studio 2021.3.1).
* support for all `java.util` methods added in API level 31 (classes
  `java.util.Duration` and `java.util.LocalTime`).
* Support for all  methods on `java.util.concurrent.ConcurrentHashMap`.
* Added class `java.util.concurrent.Flow`.

Version 1.1.8 *(2022-08-19)*
-----------------------------

Same as in version _1.2.2_ above.

Version 1.1.7 *(2022-07-08)*
-----------------------------

Same as in version _1.2.1_ above.

Version 1.1.6 *(2022-06-21)*
-----------------------------

* Update version to 1.1.6 to get configuration with fix for `j$.time`
  serialization issues
  ([issue 235932415](https://issuetracker.google.com/235932415)).

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
  implemented by the desugared library implementation. This was mainly methods
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
