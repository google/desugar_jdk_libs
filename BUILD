alias(
    name = "desugar_jdk_libs",
    actual = "//src/share/classes/java",
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
    tools = [":build_maven_artifact"],
)

py_binary(
    name = "build_maven_artifact",
    srcs = ["tools/build_maven_artifact.py"],
    python_version = "PY2",
)
