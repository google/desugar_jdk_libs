package com.google.devtools.build.android;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

/** A visitor that remaps a class or interface type. */
public class TypeReplacementClassVisitor extends ClassRemapper {

  public TypeReplacementClassVisitor(int api, ClassVisitor classVisitor, PreScanner preScanner) {
    super(api, classVisitor, new TypeReplacementRemapper(preScanner));
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
