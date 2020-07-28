Change Log
==========

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
