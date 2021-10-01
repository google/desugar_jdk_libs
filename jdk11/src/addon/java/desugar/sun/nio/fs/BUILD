load("@rules_java//java:defs.bzl", "java_library")

package(default_visibility = ["//:__subpackages__"])

java_library(
    name = "fs",
    srcs = glob(["*.java"]),
    deps = [
        "//jdk11/src/libcore:libcore_addon",
    ],
)
