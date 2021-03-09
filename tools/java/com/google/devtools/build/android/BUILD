package(default_visibility = ["//:__pkg__"])

java_binary(
    name = "jdk_type_selector",
    srcs = glob(["*.java"]),
    javacopts = [
        "-source 11",
        "-target 11",
    ],
    main_class = "com.google.devtools.build.android.JarFileClassEntrySelector",
)
