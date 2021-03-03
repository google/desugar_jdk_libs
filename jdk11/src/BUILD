load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "java_base_all",
    srcs = glob(["**/*.java"]),
    javacopts = [
        "-source 11",
        "-target 11",
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
      TOP=$$(pwd)
      INPUT_JAR="$(location :java_base_all)"
      TMP_DIR="$$(mktemp -d)"
      unzip -o "$${INPUT_JAR}" \
        META-INF/ \
        META-INF/MANIFEST.MF \
        java/ \
        java/io/ \
        java/io/DesugarBufferedReader*.class \
        java/io/UncheckedIOException*.class \
        java/lang/ \
        java/lang/Desugar*.class \
        java/lang/Iterable*.class \
        java/time/*.class \
        java/util/ \
        java/util/AbstractList*.class \
        java/util/CollSer*.class \
        java/util/Collection.class \
        java/util/Comparator*.class \
        java/util/Deque*.class \
        java/util/Desugar*.class \
        java/util/DoubleSummaryStatistics*.class \
        java/util/ImmutableCollections*.class \
        java/util/IntSummaryStatistics*.class \
        java/util/Iterator*.class \
        java/util/KeyValueHolder*.class \
        java/util/List.class \
        java/util/ListIterator.class \
        java/util/LongSummaryStatistics*.class \
        java/util/Map*.class \
        java/util/NavigableMap*.class \
        java/util/NavigableSet*.class \
        java/util/Objects*.class \
        java/util/Optional*.class \
        java/util/PrimitiveIterator*.class \
        java/util/Queue*.class \
        java/util/Set*.class \
        java/util/SortedMap*.class \
        java/util/SortedSet*.class \
        java/util/Spliterator*.class \
        java/util/StringJoiner*.class \
        java/util/Tripwire*.class \
        java/util/concurrent/ \
        java/util/concurrent/BlockingDeque*.class \
        java/util/concurrent/BlockingQueue*.class \
        java/util/concurrent/ConcurrentHashMap*.class \
        java/util/concurrent/ConcurrentMap*.class \
        java/util/concurrent/ConcurrentNavigableMap*.class \
        java/util/concurrent/Helpers*.class \
        java/util/concurrent/ThreadLocalRandom.class \
        java/util/concurrent/ThreadLocalRandom\\$$*.class \
        java/util/concurrent/TransferQueue*.class \
        java/util/concurrent/atomic/ \
        java/util/concurrent/atomic/Desugar*.class \
        java/util/function/ \
        java/util/function/*.class \
        java/util/stream/ \
        java/util/stream/*.class \
        sun/misc/Desugar*.class \
      -d "$${TMP_DIR}"

      cd $${TMP_DIR}
      zip "$${TMP_DIR}/java_base_selected.jar" -r .
      cd $${TOP}
      mv "$${TMP_DIR}/java_base_selected.jar" $@
      rm -rf $${TMP_DIR}
    """,
    visibility = ["//:__pkg__"],
)
