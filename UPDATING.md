# Updating for maven release

The file `VERSION.txt` is a one line text file which holds the version
used when releasing this as a maven library.

## Updating version

Whenever a commit is supposed to result in releasing a new maven library
the version number in `VERSION.txt` must be updated.

Commits can be made without updating `VERSION.txt`, but no new release
to a maven library can be done without updating the version.

## Building the maven library

To build the maven library zip run:

```
bazel build maven_release
```
