load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "java_base_all",
    srcs = glob(["**/*.java"]),
    javacopts = ["-source 11 -target 11"],
    visibility = ["//:__pkg__"],
)
