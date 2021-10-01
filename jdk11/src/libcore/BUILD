load("@rules_java//java:defs.bzl", "java_library")

package(default_visibility = ["//jdk11/src:__subpackages__"])

java_library(
    name = "libcore_addon",
    srcs = glob([
        "luni/src/main/java/libcore/api/*.java",
    ]) + [
        "luni/src/main/java/libcore/content/type/MimeMap.java",
        "luni/src/main/java/libcore/util/NonNull.java",
        "luni/src/main/java/libcore/util/Nullable.java",
    ],
    javacopts = [
        "-source 11",
        "-target 11",
        "-XepAllErrorsAsWarnings",
        "-Xep:rawtypes:OFF",
    ],
)
