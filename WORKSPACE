workspace(name = "desugar_jdk_libs")
load("@bazel_tools//tools/build_defs/repo:utils.bzl", "maybe")

maybe(
    android_sdk_repository,
    name = "androidsdk",
)

load(":repos.bzl", "desugar_jdk_libs_repos")
desugar_jdk_libs_repos()

load(":setup.bzl", "desugar_jdk_libs_setup")
desugar_jdk_libs_setup()