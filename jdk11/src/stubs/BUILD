load("@rules_java//java:defs.bzl", "java_library")

package(default_visibility = ["//:__subpackages__"])

java_library(
    name = "stubs",
    srcs = glob(["**/*.java"]),
    deps = ["//:android_jar"],
)
