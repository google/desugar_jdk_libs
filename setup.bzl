# Copyright (c) 2023 Google LLC

# This code is free software; you can redistribute it and/or modify it
# under the terms of the GNU General Public License version 2 only, as
# published by the Free Software Foundation.  Google designates this
# particular file as subject to the "Classpath" exception as provided
# by Google in the LICENSE file that accompanied this code.

# This code is distributed in the hope that it will be useful, but WITHOUT
# ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
# FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
# version 2 for more details (a copy is included in the LICENSE file that
# accompanied this code).

# You should have received a copy of the GNU General Public License version
# 2 along with this work; if not, write to the Free Software Foundation,
# Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.

"""Calls setup macros for external deps."""

load("@bazel_skylib//:workspace.bzl", "bazel_skylib_workspace")
load("@rules_jvm_external//:defs.bzl", "maven_install")

def desugar_jdk_libs_setup():
    """Call setup macros for external deps."""
    bazel_skylib_workspace()
    maven_install(
        name = "desugar_jdk_libs_maven",
        artifacts = [
            "com.google.auto:auto-common:1.2.1",
            "com.google.auto.value:auto-value:1.8.2",
            "com.google.auto.value:auto-value-annotations:1.9",
            "com.google.code.findbugs:jsr305:3.0.2",
            "com.google.guava:guava:23.0",
            "org.ow2.asm:asm:9.0",
            "org.ow2.asm:asm-commons:9.0",
            "org.ow2.asm:asm-tree:9.0",
        ],
        repositories = [
            "https://repo1.maven.org/maven2",
        ],
    )
