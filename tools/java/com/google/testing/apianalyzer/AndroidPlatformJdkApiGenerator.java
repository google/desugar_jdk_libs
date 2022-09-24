package com.google.testing.apianalyzer;


import com.google.common.collect.ImmutableSet;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/** Generates a text-format proto-based representation of DJK types in the given android.jar. */
public final class AndroidPlatformJdkApiGenerator {

  private static final String TEXT_PROTO_HEADER =
      "# proto-file:"
          + " third_party/java_src/desugar_jdk_libs/tools/java/com/google/testing/apianalyzer/class_digest.proto\n"
          + "# proto-message: testing.apianalyzer.ClassDigestCollection\n";

  private static final ImmutableSet<String> JDK_TYPE_PREFIXES =
      ImmutableSet.of("com/sun/", "java/", "javax/", "jdk/", "sun/", "j$/");

  public static void main(String[] args) throws IOException {
    if (args.length < 2) {
      throw new IllegalArgumentException(
          "Expected both input jar path and output jar path to be specified.");
    }
    Path inPath = Paths.get(args[0]); // Path to android.jar
    Path outPath = Paths.get(args[1]);

    ClassDigestGenerator digestGenerator =
        new ClassDigestGenerator(classDigest -> isJdkType(classDigest.getName()));
    ClassDigestCollection digestCollection = digestGenerator.read(inPath);
    String digestText = TEXT_PROTO_HEADER + ClassDigestGenerator.prettyTextFormat(digestCollection);
    if (Files.exists(outPath)) {
      Files.writeString(outPath, digestText);
    } else {
      Files.createDirectories(outPath.getParent());
      Files.writeString(outPath, digestText, StandardOpenOption.CREATE_NEW);
    }
  }

  public static boolean isJdkType(String typeInternalName) {
    return JDK_TYPE_PREFIXES.stream().anyMatch(typeInternalName::startsWith);
  }

  private AndroidPlatformJdkApiGenerator() {}
}
