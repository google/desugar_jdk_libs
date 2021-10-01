load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "java_base_all",
    srcs = glob(["java.base/**/*.java"]),
    javacopts = [
        "-source 11",
        "-target 11",
        "-XepAllErrorsAsWarnings",
        "-Xep:rawtypes:OFF",
        "--patch-module=java.base=%s" % package_name(),
        "--add-reads=java.base=ALL-UNNAMED",
    ],
    visibility = ["//:__pkg__"],
    deps = [
        "//jdk11/desugarconfig",
        "//jdk11/src/addon",
    ],
)

genrule(
    name = "java_base_selected",
    srcs = [
        ":java_base_all",
    ],
    outs = ["java_base_selected.jar"],
    cmd = """
      $(location //:tools/jdk_type_selector) $(location :java_base_all) $@
    """,
    exec_tools = ["//:tools/jdk_type_selector"],
    visibility = ["//:__pkg__"],
)

genrule(
    name = "d8_java_base_selected",
    srcs = [
        ":java_base_all",
    ],
    outs = ["d8_java_base_selected.jar"],
    cmd = """
      $(location //:tools/jdk_type_selector) $(location :java_base_all) $@ "--config=d8_desugar"
    """,
    exec_tools = ["//:tools/jdk_type_selector"],
    visibility = ["//:__pkg__"],
)

genrule(
    name = "java_base_chm_only",
    srcs = [
        ":java_base_all",
    ],
    outs = ["java_base_chm_only.jar"],
    cmd = """
      $(location //:tools/jdk_type_selector) $(location :java_base_all) $@ "--config=android_fix_libs"
    """,
    exec_tools = ["//:tools/jdk_type_selector"],
    visibility = ["//:__pkg__"],
)
