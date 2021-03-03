load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "java_base_all",
    srcs = glob(["**/*.java"]),
    javacopts = [
        "-source 11",
        "-target 11",
        "-XepAllErrorsAsWarnings",
    ],
    visibility = ["//:__pkg__"],
)

genrule(
    name = "java_base_selected",
    srcs = [
        ":java_base_all",
    ],
    outs = ["java_base_selected.jar"],
    cmd = """
      $(location //tools/jdk_type_selector) $(location :java_base_all) $@
    """,
    exec_tools = ["//tools/jdk_type_selector"],
    visibility = ["//:__pkg__"],
)

genrule(
    name = "java_base_chm_only",
    srcs = [
        ":java_base_all",
    ],
    outs = ["java_base_chm_only.jar"],
    cmd = """
      $(location //tools/jdk_type_selector) $(location :java_base_all) $@ "--config=android_fix_libs"
    """,
    exec_tools = ["//tools/jdk_type_selector"],
    visibility = ["//:__pkg__"],
)
