load("@rules_java//java:defs.bzl", "java_binary", "java_library", "java_plugin")

package(default_visibility = ["//:__pkg__"])

java_plugin(
    name = "auto_value_plugin",
    processor_class = "com.google.auto.value.processor.AutoValueProcessor",
    deps = ["@maven//:com_google_auto_value_auto_value"],
)

java_library(
    name = "auto_value",
    exported_plugins = [":auto_value_plugin"],
    exports = ["@maven//:com_google_auto_value_auto_value_annotations"],
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
        "@maven//:com_google_code_findbugs_jsr305",
        "@maven//:com_google_guava_guava",
        "@maven//:org_ow2_asm_asm",
        "@maven//:org_ow2_asm_asm_commons",
        "@maven//:org_ow2_asm_asm_tree",
    ],
)
