package(default_visibility = ["//:__subpackages__"])

genrule(
    name = "addon",
    outs = ["addon.jar"],
    srcs = ["//jdk11/src/addon/java/desugar/sun/nio/fs"],
    cmd = "cp $< $@",
    visibility = ["//visibility:public"],
)
