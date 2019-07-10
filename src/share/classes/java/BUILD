load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "java",
    srcs = glob(["**/*.java"]),
    javacopts = ["-source 8 -target 8"],
    visibility = ["//:__pkg__"],
)
