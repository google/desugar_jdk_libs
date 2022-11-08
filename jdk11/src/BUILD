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
        "//:android_jar",
        "//jdk11/src/stubs",
    ],
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
    name = "d8_java_base_selected_with_addon",
    srcs = [
        "d8_java_base_selected",
        "//jdk11/src/addon",
        "//jdk11/src/libcore:libcore_addon",
    ],
    outs = ["d8_java_base_selected_with_addon.jar"],
    cmd = """
        TMPDIR="$$(mktemp -d)"
        unzip -o $(location d8_java_base_selected) -d "$$TMPDIR"/
        unzip -o $(location //jdk11/src/libcore:libcore_addon) -d "$$TMPDIR"/
        unzip -o $(location //jdk11/src/addon) -d "$$TMPDIR"/
        MYHOME=$$PWD
        cd $$TMPDIR
        zip -DXr1 "$$MYHOME/$@" .
    """,
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
