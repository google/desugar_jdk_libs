#!/usr/bin/env python
# Copyright 2019 Google LLC
#
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# version 2 as published by the Free Software Foundation.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.

"""Generate a maven library.
"""

from __future__ import print_function
import argparse
import hashlib
import os
import re
import shutil
import string
import sys
import tempfile


POMTEMPLATE = string.Template(
    """<project
        xmlns="http://maven.apache.org/POM/4.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <groupId>com.android.tools</groupId>
      <artifactId>$artifact_id</artifactId>
      <version>$version</version>
      <name>Small subset of OpenJDK libraries</name>
      <description>
        This project contains a small subset of OpenJDK libraries simplified for use on older runtimes.

        This is not an official Google product.
      </description>
      <url>https://github.com/google/desugar_jdk_libs</url>
      <inceptionYear>2018</inceptionYear>
      <licenses>
        <license>
          <name>GNU General Public License, version 2, with the Classpath Exception</name>
          <url>https://github.com/google/desugar_jdk_libs/blob/master/LICENSE</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>
          https://github.com/google/desugar_jdk_libs
        </url>
      </scm>
    </project>
    """)


class TempDir(object):
  """TODO docstring."""

  def __init__(self, prefix='', delete=True):
    self._temp_dir = None
    self._prefix = prefix
    self._delete = delete

  def __enter__(self):
    self._temp_dir = tempfile.mkdtemp(self._prefix)
    return self._temp_dir

  def __exit__(self, *_):
    if self._delete:
      shutil.rmtree(self._temp_dir, ignore_errors=True)


def determine_version(options):
  """TODO docstring."""
  if options.version_file and options.version:
    print('Only one of --version_file and --version can be specified.')
    exit(1)
  if options.version:
    print('WARNING: version passed on the command line')
    return options.version
  with open(options.version_file) as f:
    lines = f.readlines()
  if len(lines) != 1:
    print('More than one line in version file ' + options.version_file)
    exit(1)
  version = lines[0].strip()
  reg = re.compile('^([0-9]+)\\.([0-9]+)\\.([0-9]+)$')
  if not reg.match(version):
    print('Invalid version \''
          + version
          + '\' in version file '
          + options.version_file)
    exit(1)
  return version


def get_maven_path(artifact_id, version):
  """TODO docstring."""
  return os.path.join('com', 'android', 'tools', artifact_id, version)


def write_pom_file(artifact_id, version, pom_file):
  """TODO docstring."""
  version_pom = POMTEMPLATE.substitute(artifact_id=artifact_id, version=version)
  with open(pom_file, 'w') as f:
    f.write(version_pom)


def hash_for(filename, hash_builder):
  """TODO docstring."""
  with open(filename, 'rb') as f:
    while True:
      # Read chunks of 1MB
      chunk = f.read(2 ** 20)
      if not chunk:
        break
      hash_builder.update(chunk)
  return hash_builder.hexdigest()


def write_md5_for(filename):
  """TODO docstring."""
  hexdigest = hash_for(filename, hashlib.md5())
  with (open(filename + '.md5', 'w')) as f:
    f.write(hexdigest)


def write_sha1_for(filename):
  """TODO docstring."""
  hexdigest = hash_for(filename, hashlib.sha1())
  with (open(filename + '.sha1', 'w')) as f:
    f.write(hexdigest)


def run(jar, out, artifact_id, version):
  """TODO docstring."""
  # Create directory structure for this version.
  with TempDir() as tmp_dir:
    version_dir = os.path.join(tmp_dir, get_maven_path(artifact_id, version))
    os.makedirs(version_dir)
    # Write the pom file.
    pom_file = os.path.join(version_dir, artifact_id + '-' + version + '.pom')
    write_pom_file(artifact_id, version, pom_file)
    # Copy the jar to the output.
    target_jar = os.path.join(version_dir, artifact_id + '-' + version + '.jar')
    shutil.copyfile(jar, target_jar)
    # Create check sums.
    write_md5_for(target_jar)
    write_md5_for(pom_file)
    write_sha1_for(target_jar)
    write_sha1_for(pom_file)
    # Zip it up - make_archive will append zip to the file, so remove.
    assert out.endswith('.zip')
    base_no_zip = out[0:len(out)-4]
    shutil.make_archive(base_no_zip, 'zip', tmp_dir)


def parse_options(argv):
  """TODO docstring."""
  result = argparse.ArgumentParser()
  result.add_argument('--jar', help='The jar file with the library code')
  result.add_argument('--out', help='The zip file to output')
  result.add_argument('--artifact_id', help='Maven artifact id')
  result.add_argument('--version', help='Version number')
  result.add_argument('--version_file', help='File containing version number')
  return result.parse_args(argv)


def main(argv):
  """TODO docstring."""
  options = parse_options(argv)
  jar = options.jar
  artifact_id = options.artifact_id
  out = options.out
  if not jar:
    print('Need to supply jar with --jar.')
    exit(1)
  if not artifact_id:
    print('Need to supply maven artifactId with --artifact_id.')
    exit(1)
  if not out:
    print('Need to supply output zip with --out.')
    exit(1)
  version = determine_version(options)
  run(jar, out, artifact_id, version)


if __name__ == '__main__':
  exit(main(sys.argv[1:]))
