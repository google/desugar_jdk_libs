# Updating for maven release

The file `oss/VERSION.txt` is a one line text file which holds the version
used when releasing this as a maven library.

## Updating version

Whenever a commit is supposed to result in releasing a new maven library
the version number in `oss/VERSION.txt` must be updated.

Commits can be made without updating `oss/VERSION.txt`, but no new release
to a maven library can be done without updating the version.

When updating the `oss/VERSION.txt` also add a short update to
`oss/CHANGELOG.md`.

## Building the maven library

To build the maven library zip run:

```
bazel build maven_release
```
