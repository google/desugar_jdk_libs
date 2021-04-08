load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "java_base_all",
    srcs = glob(["**/*.java"]),
    javacopts = [
        "-source 11",
        "-target 11",
        "--patch-module java.base=.",
        "-XepAllErrorsAsWarnings",
    ],
    visibility = ["//visibility:private"],
)

java_library(
    name = "java_base_partial",
    srcs = glob([
        "java.base/share/classes/java/lang/*.java",
        "java.base/share/classes/java/io/*.java",
        "java.base/share/classes/java/time/**/*.java",
        "java.base/share/classes/java/util/*.java",
        "java.base/share/classes/java/util/concurrent/**/*.java",
        "java.base/share/classes/java/util/stream/*.java",
        "java.base/share/classes/java/util/function/*.java",
        "java.base/share/classes/sun/misc/*.java",
        "java.base/share/classes/jdk/internal/util/*.java",
    ]),
    javacopts = [
        "-source 11",
        "-target 11",
        "--patch-module java.base=.",
        "-XepAllErrorsAsWarnings",
    ],
    visibility = ["//:__pkg__"],
)

genrule(
    name = "java_base_selected",
    srcs = [
        ":java_base_partial",
    ],
    outs = ["java_base_selected.jar"],
    cmd = """
      $(location //:tools/jdk_type_selector) $(location :java_base_partial) $@
    """,
    exec_tools = ["//:tools/jdk_type_selector"],
    visibility = ["//:__pkg__"],
)

genrule(
    name = "java_base_chm_only",
    srcs = [
        ":java_base_partial",
    ],
    outs = ["java_base_chm_only.jar"],
    cmd = """
      $(location //:tools/jdk_type_selector) $(location :java_base_partial) $@ "--config=android_fix_libs"
    """,
    exec_tools = ["//:tools/jdk_type_selector"],
    visibility = ["//:__pkg__"],
)
