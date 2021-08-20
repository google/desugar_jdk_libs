package com.google.devtools.build.android;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

/** A visitor that remaps a class or interface type. */
public class TypeReplacementClassVisitor extends ClassRemapper {

  private final PreScanner preScanner;
  private String typeInternalName;

  public TypeReplacementClassVisitor(int api, ClassVisitor classVisitor, PreScanner preScanner) {
    super(api, classVisitor, new TypeReplacementRemapper(preScanner));
    this.preScanner = preScanner;
  }

  @Override
  public void visit(
      int version,
      int access,
      String name,
      String signature,
      String superName,
      String[] interfaces) {
    typeInternalName = name;
    super.visit(version, access, name, signature, superName, interfaces);
  }

  @Override
  public void visitNestHost(String nestHost) {
    if (preScanner.getReplacementType(typeInternalName) != null) {
      super.visitNestHost(AsmHelpers.getReplacementTypeInternalName(nestHost));
      return;
    }
    super.visitNestHost(nestHost);
  }

  static class TypeReplacementRemapper extends Remapper {

    private final PreScanner preScanner;

    TypeReplacementRemapper(PreScanner preScanner) {
      this.preScanner = preScanner;
    }

    @Override
    public String map(String internalName) {
      String replacementType = preScanner.getReplacementType(internalName);
      if (replacementType != null) {
        return super.map(replacementType);
      }
      return super.map(internalName);
    }
  }
}
