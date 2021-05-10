package(default_visibility = ["//:__pkg__"])

java_binary(
    name = "jdk_type_selector",
    srcs = glob(["*.java"]),
    javacopts = [
        "-source 11",
        "-target 11",
    ],
    main_class = "com.google.devtools.build.android.JarFileClassEntrySelector",
    deps = [
        "@google_bazel_common//third_party/java/asm",
        "@google_bazel_common//third_party/java/asm:asm-tree",
        "@google_bazel_common//third_party/java/auto:value",
        "@google_bazel_common//third_party/java/guava",
        "@google_bazel_common//third_party/java/jsr305_annotations",
    ],
)
