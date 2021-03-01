alias(
    name = "desugar_jdk_libs",
    actual = "//src/share/classes/java",
    visibility = ["//visibility:public"],
)

alias(
    name = "desugar_jdk_libs_jdk11",
    actual = "//jdk11/src:java_base_selected",
    visibility = ["//visibility:public"],
)

genrule(
    name = "maven_release",
    srcs = [
        ":desugar_jdk_libs",
        "VERSION.txt",
        "DEPENDENCIES.txt",
    ],
    outs = ["desugar_jdk_libs.zip"],
    cmd = "$(location :build_maven_artifact)" +
          " --jar $(location :desugar_jdk_libs)" +
          " --artifact_id desugar_jdk_libs" +
          " --version_file $(location VERSION.txt)" +
          " --dependencies_file $(location DEPENDENCIES.txt)" +
          " --out $@",
    exec_tools = [":build_maven_artifact"],
)

genrule(
    name = "maven_release_jdk11",
    srcs = [
        ":desugar_jdk_libs",
        "VERSION.txt",
        "DEPENDENCIES.txt",
    ],
    outs = ["desugar_jdk_libs_jdk11.zip"],
    cmd = "$(location :build_maven_artifact)" +
          " --jar $(location :desugar_jdk_libs_jdk11)" +
          " --artifact_id desugar_jdk_libs" +
          " --version_file $(location VERSION.txt)" +
          " --dependencies_file $(location DEPENDENCIES.txt)" +
          " --out $@",
    exec_tools = [":build_maven_artifact"],
)

py_binary(
    name = "build_maven_artifact",
    srcs = ["tools/build_maven_artifact.py"],
    python_version = "PY3",
    srcs_version = "PY3",
)
