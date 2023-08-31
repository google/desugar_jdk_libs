# Copyright 2023 Google LLC
#
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# version 2 as published by the Free Software Foundation.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.

"""Adds a wrapper macro for generating a java_library dep for AutoValue."""
load("@rules_java//java:defs.bzl", "java_library", "java_plugin")

def auto_value_library(name):
    """Sets up a java_library for AutoValue.

    Assumes that AutoValue is installed through maven_install().

    Inputs:
        name: A string describing the target name of the AutoValue java_library.

    Outputs:
        A java_library
    """
    java_plugin(
        name = name + "_auto_value_plugin",
        processor_class = "com.google.auto.value.processor.AutoValueProcessor",
        deps = ["@maven//:com_google_auto_value_auto_value"],
    )

    java_library(
        name = name,
        exported_plugins = [name + "_auto_value_plugin"],
        exports = ["@maven//:com_google_auto_value_auto_value"],
    )
