load("@rules_java//java:defs.bzl", "java_binary", "java_library", "java_plugin")

package(default_visibility = ["//:__pkg__"])

java_plugin(
    name = "auto_value_plugin",
    processor_class = "com.google.auto.value.processor.AutoValueProcessor",
    deps = ["@desugar_jdk_libs_maven//:com_google_auto_value_auto_value"],
)

java_library(
    name = "auto_value",
    exported_plugins = [":auto_value_plugin"],
    exports = ["@desugar_jdk_libs_maven//:com_google_auto_value_auto_value_annotations"],
)

java_binary(
    name = "jdk_type_selector",
    srcs = glob(["*.java"]),
    javacopts = [
        "-source 11",
        "-target 11",
    ],
    main_class = "com.google.devtools.build.android.JarFileClassEntrySelector",
    deps = [
        ":auto_value",
        "@desugar_jdk_libs_maven//:com_google_code_findbugs_jsr305",
        "@desugar_jdk_libs_maven//:com_google_guava_guava",
        "@desugar_jdk_libs_maven//:org_ow2_asm_asm",
        "@desugar_jdk_libs_maven//:org_ow2_asm_asm_commons",
        "@desugar_jdk_libs_maven//:org_ow2_asm_asm_tree",
    ],
)
