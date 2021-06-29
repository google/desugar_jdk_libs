package com.google.testing.jarentryselector;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static com.google.testing.apianalyzer.AndroidPlatformJdkApiGenerator.isJdkType;

import com.google.common.collect.ImmutableSet;
import com.google.common.flogger.GoogleLogger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

/** Selects certain test jar entries in a given jar and packs into a new JAR. */
public final class TestJarEntrySelector {
  private static final GoogleLogger logger = GoogleLogger.forEnclosingClass();

  private static final LocalDateTime DEFAULT_LOCAL_TIME =
      LocalDateTime.of(2010, 1, 1, 0, 0, 0).atZone(ZoneId.systemDefault()).toLocalDateTime();

  private final Path desugarLibSelectedJarPath;
  private final Path testInputJarPath;
  private final Path testOutputJarPath;

  private ImmutableSet<String> desugarSelectedTypes;
  private ImmutableSet<String> extendedJdkTypeRemapper;

  public TestJarEntrySelector(
      Path desugarLibSelectedJarPath, Path testInputJarPath, Path testOutputJarPath) {
    this.desugarLibSelectedJarPath = desugarLibSelectedJarPath;
    this.testInputJarPath = testInputJarPath;
    this.testOutputJarPath = testOutputJarPath;
  }

  public void preScan() throws IOException {
    desugarSelectedTypes =
        new JarFile(desugarLibSelectedJarPath.toFile())
            .stream()
                .map(ZipEntry::getName)
                .filter(name -> name.endsWith(".class"))
                .map(name -> name.substring(0, name.length() - 6))
                .collect(toImmutableSet());
    extendedJdkTypeRemapper =
        new JarFile(testInputJarPath.toFile())
            .stream()
                .map(ZipEntry::getName)
                .filter(name -> name.endsWith(".class"))
                .map(name -> name.substring(0, name.length() - 6))
                .filter(name -> isJdkType(name) && !desugarSelectedTypes.contains(name))
                .collect(toImmutableSet());
  }

  public void transfer() throws IOException {
    try (JarInputStream in = new JarInputStream(Files.newInputStream(testInputJarPath));
        JarOutputStream out = new JarOutputStream(Files.newOutputStream(testOutputJarPath))) {
      for (JarEntry inEntry; (inEntry = in.getNextJarEntry()) != null; ) {
        String inEntryName = inEntry.getName();
        if (inEntryName.endsWith(".class")) {
          String typeName = inEntryName.substring(0, inEntryName.length() - 6);
          if (desugarSelectedTypes.contains(typeName)) {
            logger.atInfo().log(
                "Omitted type: %s to output test jar as it will be contained the desugared"
                    + " desugar_jdk_libs jar.",
                inEntryName);
            continue;
          }
          ClassWriter cw = new ClassWriter(0);
          ClassVisitor cv = new ExtendedJdkTypeRemapper(Opcodes.ASM9, cw);
          byte[] inBytes = in.readAllBytes();
          ClassReader cr = new ClassReader(inBytes);
          cr.accept(cv, 0);
          byte[] outBytes = cw.toByteArray();
          JarEntry outJarEntry = createJarEntry(inEntryName, outBytes);
          out.putNextEntry(outJarEntry);
          out.write(outBytes);
          out.closeEntry();
        } else {
          byte[] outBytes = in.readAllBytes();
          JarEntry outJarEntry = createJarEntry(inEntryName, outBytes);
          out.putNextEntry(outJarEntry);
          out.write(outBytes);
          out.closeEntry();
        }
      }
    }
  }

  class ExtendedJdkTypeRemapper extends ClassRemapper {
    ExtendedJdkTypeRemapper(int api, ClassVisitor classVisitor) {
      super(
          api,
          classVisitor,
          new Remapper() {
            @Override
            public String map(String internalName) {
              if (extendedJdkTypeRemapper.contains(internalName)) {
                return "jdk11/" + internalName;
              }
              return super.map(internalName);
            }
          });
    }
  }

  private static JarEntry createJarEntry(String entryName, byte[] bytes) {
    JarEntry je = new JarEntry(entryName);
    je.setTimeLocal(DEFAULT_LOCAL_TIME);
    je.setMethod(JarEntry.STORED);
    int byteLength = bytes.length;
    je.setSize(byteLength);
    CRC32 checksum = new CRC32();
    checksum.update(bytes);
    je.setCrc(checksum.getValue());
    return je;
  }

  public static void main(String[] args) throws IOException {
    if (args.length < 3) {
      throw new IllegalArgumentException(
          "Expected input jar path and output jar path to be specified.");
    }
    Path libraryJarPath = Paths.get(args[0]);
    Path testJarPath = Paths.get(args[1]);
    Path outPath = Paths.get(args[2]);
    TestJarEntrySelector testJarEntrySelector =
        new TestJarEntrySelector(libraryJarPath, testJarPath, outPath);
    testJarEntrySelector.preScan();
    testJarEntrySelector.transfer();
  }
}
