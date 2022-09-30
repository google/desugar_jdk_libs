package com.google.testing.apianalyzer;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

/** Checks whether a given type or class member is resolvable in the given type pool. */
public final class ClassMemberResolver {
  private final ImmutableMap<String, ClassDigest> typePool;
  private final ImmutableMultimap<String, String> superTypeMap;
  private final ImmutableMultimap<String, HeadlessClassMemberKey> classMemberMap;

  public static ClassMemberResolver create(ClassDigestCollection libraryClasses) {
    ImmutableMap.Builder<String, ClassDigest> typePool = ImmutableMap.builder();
    ImmutableMultimap.Builder<String, String> superTypeMap = ImmutableListMultimap.builder();
    ImmutableMultimap.Builder<String, HeadlessClassMemberKey> classMemberMap =
        ImmutableListMultimap.builder();
    for (ClassDigest clazz : libraryClasses.getClassesList()) {
      String className = clazz.getName();
      typePool.put(className, clazz);

      superTypeMap.put(className, clazz.getSuperClass());
      superTypeMap.putAll(className, clazz.getSuperInterfacesList());

      for (Field field : clazz.getFieldsList()) {
        classMemberMap.put(
            className, HeadlessClassMemberKey.create(field.getName(), field.getDesc()));
      }
      for (Method method : clazz.getMethodsList()) {
        classMemberMap.put(
            className, HeadlessClassMemberKey.create(method.getName(), method.getDesc()));
      }
    }
    return new ClassMemberResolver(
        typePool.buildOrThrow(), superTypeMap.build(), classMemberMap.build());
  }

  private ClassMemberResolver(
      ImmutableMap<String, ClassDigest> typePool,
      ImmutableMultimap<String, String> superTypeMap,
      ImmutableMultimap<String, HeadlessClassMemberKey> classMemberMap) {
    this.typePool = typePool;
    this.superTypeMap = superTypeMap;
    this.classMemberMap = classMemberMap;
  }

  public boolean isResolvable(String type) {
    return typePool.containsKey(type);
  }

  public boolean isResolvable(Method method) {
    return isResolvable(method.getOwner(), method.getName(), method.getDesc());
  }

  public boolean isResolvable(Field field) {
    return isResolvable(field.getOwner(), field.getName(), field.getDesc());
  }

  public boolean isResolvable(String owner, String name, String desc) {
    HeadlessClassMemberKey memberKey = HeadlessClassMemberKey.create(name, desc);
    ArrayDeque<String> queue = new ArrayDeque<>();
    Set<String> visited = new HashSet<>();

    // BFS of all super types of the target owner type.
    queue.push(owner);
    visited.add(owner);
    while (!queue.isEmpty()) {
      String type = queue.pop();
      if (classMemberMap.containsKey(type) && classMemberMap.get(type).contains(memberKey)) {
        return true;
      } else if (superTypeMap.containsKey(type)) {
        for (String superType : superTypeMap.get(type)) {
          if (!visited.contains(superType)) {
            queue.push(superType);
            visited.add(superType);
          }
        }
      }
    }
    return false;
  }

  @AutoValue
  abstract static class HeadlessClassMemberKey {

    abstract String name();

    abstract String desc();

    public static HeadlessClassMemberKey create(String name, String desc) {
      return new com.google.testing.apianalyzer
          .AutoValue_ClassMemberResolver_HeadlessClassMemberKey(name, desc);
    }
  }
}
