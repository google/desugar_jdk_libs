/*
 * Copyright (c) 1997, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package java.util;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * This class consists exclusively of static methods that operate on or return
 * collections.  It contains polymorphic algorithms that operate on
 * collections, "wrappers", which return a new collection backed by a
 * specified collection, and a few other odds and ends.
 *
 * <p>The methods of this class all throw a <tt>NullPointerException</tt>
 * if the collections or class objects provided to them are null.
 *
 * <p>The documentation for the polymorphic algorithms contained in this class
 * generally includes a brief description of the <i>implementation</i>.  Such
 * descriptions should be regarded as <i>implementation notes</i>, rather than
 * parts of the <i>specification</i>.  Implementors should feel free to
 * substitute other algorithms, so long as the specification itself is adhered
 * to.  (For example, the algorithm used by <tt>sort</tt> does not have to be
 * a mergesort, but it does have to be <i>stable</i>.)
 *
 * <p>The "destructive" algorithms contained in this class, that is, the
 * algorithms that modify the collection on which they operate, are specified
 * to throw <tt>UnsupportedOperationException</tt> if the collection does not
 * support the appropriate mutation primitive(s), such as the <tt>set</tt>
 * method.  These algorithms may, but are not required to, throw this
 * exception if an invocation would have no effect on the collection.  For
 * example, invoking the <tt>sort</tt> method on an unmodifiable list that is
 * already sorted may or may not throw <tt>UnsupportedOperationException</tt>.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Collection
 * @see     Set
 * @see     List
 * @see     Map
 * @since   1.2
 */
// For Desugar: Copy of synchronized Map and subclasses,
// partial copy of synchronized list and collection.
public class DesugarCollections {
  // Suppresses default constructor, ensuring non-instantiability.
  private DesugarCollections() {}

  // For Desugar: Classes are public/package private to be able to be used in emulated dispatch.
  @SuppressWarnings("WeakerAccess")
  public static final Class<? extends Collection> SYNCHRONIZED_COLLECTION;

  @SuppressWarnings("WeakerAccess")
  static final Class<? extends List> SYNCHRONIZED_LIST;

  private static final Field MUTEX_FIELD;
  private static final Field COLLECTION_FIELD;
  private static final Field LIST_FIELD;
  // For Desugar: For instantiation inside the synchronized maps.
  private static final Constructor<? extends Collection> SYNCHRONIZED_COLLECTION_CONSTRUCTOR;
  private static final Constructor<? extends Set> SYNCHRONIZED_SET_CONSTRUCTOR;

  // For Desugar: Besides instrumented phones, all these variables should be present.
  // If they're not present, the code falls back to non synchronized utilities.
  static {
    SYNCHRONIZED_COLLECTION = Collections.synchronizedCollection(new ArrayList<>()).getClass();
    SYNCHRONIZED_LIST = Collections.synchronizedList(new LinkedList<>()).getClass();
    MUTEX_FIELD = getField(SYNCHRONIZED_COLLECTION, "mutex", Object.class);
    if (MUTEX_FIELD != null) {
      MUTEX_FIELD.setAccessible(true);
    }
    COLLECTION_FIELD = getField(SYNCHRONIZED_COLLECTION, "c", Collection.class);
    COLLECTION_FIELD.setAccessible(true);
    LIST_FIELD = getField(SYNCHRONIZED_LIST, "list", List.class);
    LIST_FIELD.setAccessible(true);
    Class<? extends Set> synchronizedSet = Collections.synchronizedSet(new HashSet<>()).getClass();
    SYNCHRONIZED_SET_CONSTRUCTOR = getConstructor(synchronizedSet, Set.class, Object.class);
        if (SYNCHRONIZED_SET_CONSTRUCTOR != null) {
      SYNCHRONIZED_SET_CONSTRUCTOR.setAccessible(true);
    }
    SYNCHRONIZED_COLLECTION_CONSTRUCTOR =
        getConstructor(SYNCHRONIZED_COLLECTION, Collection.class, Object.class);
    if (SYNCHRONIZED_COLLECTION_CONSTRUCTOR != null) {
        SYNCHRONIZED_COLLECTION_CONSTRUCTOR.setAccessible(true);
    }
  }

  private static Field getField(Class<?> clazz, String name, Class<?> type) {
    try {
      return clazz.getDeclaredField(name);
    } catch (NoSuchFieldException e) {
      // For Desugar: Some fields are not available on instrumented devices.
      return null;
    }
  }

  private static <E> Constructor<? extends E> getConstructor(
      Class<? extends E> clazz, Class<?>... parameterTypes) {
    try {
      return clazz.getDeclaredConstructor(parameterTypes);
    } catch (NoSuchMethodException e) {
      // For Desugar: Some constructors are not available on instrumented devices.
      return null;
    }
  }

  // For Desugar: Support for synchronization in synchronizedCollection and synchronizedList.
  @SuppressWarnings({"unchecked"})
  static <E> boolean removeIf(Collection<E> collection, Predicate<? super E> filter) {
    // Fall-back to non-synchronized set-up on instrumented devices.
    if (MUTEX_FIELD == null) {
      try {
        return ((Collection<E>) COLLECTION_FIELD.get(collection)).removeIf(filter);
      } catch (IllegalAccessException e) {
        throw new Error("Runtime illegal access in synchronized collection removeIf fall-back.", e);
      }
    }
    try {
      synchronized (MUTEX_FIELD.get(collection)) {
        return ((Collection<E>) COLLECTION_FIELD.get(collection)).removeIf(filter);
      }
    } catch (IllegalAccessException e) {
      throw new Error("Runtime illegal access in synchronized collection removeIf.", e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <E> void forEach(Iterable<E> iterable, Consumer<? super E> consumer) {
    // Fall-back to non-synchronized set-up on instrumented devices.
    if (MUTEX_FIELD == null) {
      try {
        ((Collection<E>) COLLECTION_FIELD.get(iterable)).forEach(consumer);
        return;
      } catch (IllegalAccessException e) {
        throw new Error("Runtime illegal access in synchronized collection forEach fall-back.", e);
      }
    }
    try {
      synchronized (MUTEX_FIELD.get(iterable)) {
        ((Collection<E>) COLLECTION_FIELD.get(iterable)).forEach(consumer);
      }
    } catch (IllegalAccessException e) {
      throw new Error("Runtime illegal access in synchronized collection forEach.", e);
    }
  }

  @SuppressWarnings("unchecked")
  static <E> void replaceAll(List<E> list, UnaryOperator<E> operator) {
    // Fall-back to non-synchronized set-up on instrumented devices.
    if (MUTEX_FIELD == null) {
      try {
        ((List<E>) LIST_FIELD.get(list)).replaceAll(operator);
        return;
      } catch (IllegalAccessException e) {
        throw new Error("Runtime illegal access in synchronized list replaceAll fall-back.", e);
      }
    }
    try {
      synchronized (MUTEX_FIELD.get(list)) {
        ((List<E>) LIST_FIELD.get(list)).replaceAll(operator);
      }
    } catch (IllegalAccessException e) {
      throw new Error("Runtime illegal access in synchronized list replaceAll.", e);
    }
  }

  @SuppressWarnings("unchecked")
  static <E> void sort(List<E> list, Comparator<? super E> comparator) {
    // Fall-back to non-synchronized set-up on instrumented devices.
    if (MUTEX_FIELD == null) {
      try {
        ((List<E>) LIST_FIELD.get(list)).sort(comparator);
        return;
      } catch (IllegalAccessException e) {
        throw new Error("Runtime illegal access in synchronized collection sort fall-back.", e);
      }
    }
    try {
      synchronized (MUTEX_FIELD.get(list)) {
        ((List<E>) LIST_FIELD.get(list)).sort(comparator);
      }
    } catch (IllegalAccessException e) {
      throw new Error("Runtime illegal access in synchronized list sort.", e);
    }
  }

    // For Desugar: Support for SynchronizedMap and SynchronizedSortedMap.
    /**
     * Returns a synchronized (thread-safe) map backed by the specified
     * map.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing map is accomplished
     * through the returned map.<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * map when iterating over any of its collection views:
     * <pre>
     *  Map m = Collections.synchronizedMap(new HashMap());
     *      ...
     *  Set s = m.keySet();  // Needn't be in synchronized block
     *      ...
     *  synchronized (m) {  // Synchronizing on m, not s!
     *      Iterator i = s.iterator(); // Must be in synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned map will be serializable if the specified map is
     * serializable.
     *
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @param  m the map to be "wrapped" in a synchronized map.
     * @return a synchronized view of the specified map.
     */
    public static <K,V> Map<K,V> synchronizedMap(Map<K,V> m) {
        return new SynchronizedMap<>(m);
    }

    /**
     * @serial include
     */
    private static class SynchronizedMap<K,V>
        implements Map<K,V>, Serializable {
        private static final long serialVersionUID = 1978198479659022715L;

        private final Map<K,V> m;     // Backing Map
        final Object      mutex;        // Object on which to synchronize

        SynchronizedMap(Map<K,V> m) {
            this.m = Objects.requireNonNull(m);
            mutex = this;
        }

        SynchronizedMap(Map<K,V> m, Object mutex) {
            this.m = m;
            this.mutex = mutex;
        }

        public int size() {
            synchronized (mutex) {return m.size();}
        }
        public boolean isEmpty() {
            synchronized (mutex) {return m.isEmpty();}
        }
        public boolean containsKey(Object key) {
            synchronized (mutex) {return m.containsKey(key);}
        }
        public boolean containsValue(Object value) {
            synchronized (mutex) {return m.containsValue(value);}
        }
        public V get(Object key) {
            synchronized (mutex) {return m.get(key);}
        }

        public V put(K key, V value) {
            synchronized (mutex) {return m.put(key, value);}
        }
        public V remove(Object key) {
            synchronized (mutex) {return m.remove(key);}
        }
        public void putAll(Map<? extends K, ? extends V> map) {
            synchronized (mutex) {m.putAll(map);}
        }
        public void clear() {
            synchronized (mutex) {m.clear();}
        }

        private transient Set<K> keySet;
        private transient Set<Map.Entry<K,V>> entrySet;
        private transient Collection<V> values;

        // For Desugar: Instantiate a java.util.Collections$SynchronizedSet.
        @SuppressWarnings("unchecked")
        private <T> Set<T> instantiateSet(Set<T> set, Object mutex) {
          // Fall-back to synchronized set on invalid mutex on instrumented devices.
          // This is used for testing purpose only.
          if (SYNCHRONIZED_SET_CONSTRUCTOR == null) {
            return Collections.synchronizedSet(set);
          }
          try {
            return SYNCHRONIZED_SET_CONSTRUCTOR.newInstance(set, mutex);
          } catch (InstantiationException
              | IllegalAccessException
              | InvocationTargetException e) {
            throw new Error("Unable to instantiate a synchronized list.", e);
          }
        }

        // For Desugar: Instantiate a java.util.Collections$SynchronizedCollection.
        @SuppressWarnings("unchecked")
        private <T> Collection<T> instantiateCollection(Collection<T> collection, Object mutex) {
          // Fall-back to synchronized collection on invalid mutex on instrumented devices.
          // This is used for testing purpose only.
          if (SYNCHRONIZED_COLLECTION_CONSTRUCTOR == null) {
            return Collections.synchronizedCollection(collection);
          }
          try {
            return SYNCHRONIZED_COLLECTION_CONSTRUCTOR.newInstance(collection, mutex);
          } catch (InstantiationException
              | IllegalAccessException
              | InvocationTargetException e) {
            throw new Error("Unable to instantiate a synchronized list.", e);
          }
        }

        public Set<K> keySet() {
            synchronized (mutex) {
                if (keySet==null)
                    keySet = instantiateSet(m.keySet(), mutex);
                return keySet;
            }
        }

        public Set<Map.Entry<K,V>> entrySet() {
            synchronized (mutex) {
                if (entrySet==null)
                    entrySet = instantiateSet(m.entrySet(), mutex);
                return entrySet;
            }
        }

        public Collection<V> values() {
            synchronized (mutex) {
                if (values==null)
                    values = instantiateCollection(m.values(), mutex);
                return values;
            }
        }

        public boolean equals(Object o) {
            if (this == o)
                return true;
            synchronized (mutex) {return m.equals(o);}
        }
        public int hashCode() {
            synchronized (mutex) {return m.hashCode();}
        }
        public String toString() {
            synchronized (mutex) {return m.toString();}
        }

        // Override default methods in Map
        @Override
        public V getOrDefault(Object k, V defaultValue) {
            synchronized (mutex) {return m.getOrDefault(k, defaultValue);}
        }
        @Override
        public void forEach(BiConsumer<? super K, ? super V> action) {
            synchronized (mutex) {m.forEach(action);}
        }
        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
            synchronized (mutex) {m.replaceAll(function);}
        }
        @Override
        public V putIfAbsent(K key, V value) {
            synchronized (mutex) {return m.putIfAbsent(key, value);}
        }
        @Override
        public boolean remove(Object key, Object value) {
            synchronized (mutex) {return m.remove(key, value);}
        }
        @Override
        public boolean replace(K key, V oldValue, V newValue) {
            synchronized (mutex) {return m.replace(key, oldValue, newValue);}
        }
        @Override
        public V replace(K key, V value) {
            synchronized (mutex) {return m.replace(key, value);}
        }
        @Override
        public V computeIfAbsent(K key,
                Function<? super K, ? extends V> mappingFunction) {
            synchronized (mutex) {return m.computeIfAbsent(key, mappingFunction);}
        }
        @Override
        public V computeIfPresent(K key,
                BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            synchronized (mutex) {return m.computeIfPresent(key, remappingFunction);}
        }
        @Override
        public V compute(K key,
                BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            synchronized (mutex) {return m.compute(key, remappingFunction);}
        }
        @Override
        public V merge(K key, V value,
                BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
            synchronized (mutex) {return m.merge(key, value, remappingFunction);}
        }

        private void writeObject(ObjectOutputStream s) throws IOException {
            synchronized (mutex) {s.defaultWriteObject();}
        }
    }

    /**
     * Returns a synchronized (thread-safe) sorted map backed by the specified
     * sorted map.  In order to guarantee serial access, it is critical that
     * <strong>all</strong> access to the backing sorted map is accomplished
     * through the returned sorted map (or its views).<p>
     *
     * It is imperative that the user manually synchronize on the returned
     * sorted map when iterating over any of its collection views, or the
     * collections views of any of its <tt>subMap</tt>, <tt>headMap</tt> or
     * <tt>tailMap</tt> views.
     * <pre>
     *  SortedMap m = Collections.synchronizedSortedMap(new TreeMap());
     *      ...
     *  Set s = m.keySet();  // Needn't be in synchronized block
     *      ...
     *  synchronized (m) {  // Synchronizing on m, not s!
     *      Iterator i = s.iterator(); // Must be in synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * or:
     * <pre>
     *  SortedMap m = Collections.synchronizedSortedMap(new TreeMap());
     *  SortedMap m2 = m.subMap(foo, bar);
     *      ...
     *  Set s2 = m2.keySet();  // Needn't be in synchronized block
     *      ...
     *  synchronized (m) {  // Synchronizing on m, not m2 or s2!
     *      Iterator i = s.iterator(); // Must be in synchronized block
     *      while (i.hasNext())
     *          foo(i.next());
     *  }
     * </pre>
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * <p>The returned sorted map will be serializable if the specified
     * sorted map is serializable.
     *
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @param  m the sorted map to be "wrapped" in a synchronized sorted map.
     * @return a synchronized view of the specified sorted map.
     */
    public static <K,V> SortedMap<K,V> synchronizedSortedMap(SortedMap<K,V> m) {
        return new SynchronizedSortedMap<>(m);
    }

    /**
     * @serial include
     */
    static class SynchronizedSortedMap<K,V>
        extends SynchronizedMap<K,V>
        implements SortedMap<K,V>
    {
        private static final long serialVersionUID = -8798146769416483793L;

        private final SortedMap<K,V> sm;

        SynchronizedSortedMap(SortedMap<K,V> m) {
            super(m);
            sm = m;
        }
        SynchronizedSortedMap(SortedMap<K,V> m, Object mutex) {
            super(m, mutex);
            sm = m;
        }

        public Comparator<? super K> comparator() {
            synchronized (mutex) {return sm.comparator();}
        }

        public SortedMap<K,V> subMap(K fromKey, K toKey) {
            synchronized (mutex) {
                return new SynchronizedSortedMap<>(
                    sm.subMap(fromKey, toKey), mutex);
            }
        }
        public SortedMap<K,V> headMap(K toKey) {
            synchronized (mutex) {
                return new SynchronizedSortedMap<>(sm.headMap(toKey), mutex);
            }
        }
        public SortedMap<K,V> tailMap(K fromKey) {
            synchronized (mutex) {
               return new SynchronizedSortedMap<>(sm.tailMap(fromKey),mutex);
            }
        }

        public K firstKey() {
            synchronized (mutex) {return sm.firstKey();}
        }
        public K lastKey() {
            synchronized (mutex) {return sm.lastKey();}
        }
    }
}
