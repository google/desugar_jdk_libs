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
import java.lang.reflect.Array;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

// For Desugar: Copy of synchronized, unmodifiable and checked collections.
public class DesugarCollections {
  // Suppresses default constructor, ensuring non-instantiability.
  private DesugarCollections() {}

  // Unmodifiable Wrappers
  /**
   * Returns an <a href="Collection.html#unmodview">unmodifiable view</a> of the specified
   * collection. Query operations on the returned collection "read through" to the specified
   * collection, and attempts to modify the returned collection, whether direct or via its iterator,
   * result in an {@code UnsupportedOperationException}.
   *
   * <p>The returned collection does <i>not</i> pass the hashCode and equals operations through to
   * the backing collection, but relies on {@code Object}'s {@code equals} and {@code hashCode}
   * methods. This is necessary to preserve the contracts of these operations in the case that the
   * backing collection is a set or a list.
   *
   * <p>The returned collection will be serializable if the specified collection is serializable.
   *
   * @param <T> the class of the objects in the collection
   * @param c the collection for which an unmodifiable view is to be returned.
   * @return an unmodifiable view of the specified collection.
   */
  public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> c) {
    return new UnmodifiableCollection<>(c);
  }

  /**
   * @serial include
   */
  static class UnmodifiableCollection<E> implements Collection<E>, Serializable {
    private static final long serialVersionUID = 1820017752578914078L;
    final Collection<? extends E> c;

    UnmodifiableCollection(Collection<? extends E> c) {
      if (c == null) {
        throw new NullPointerException();
      }
      this.c = c;
    }

    public int size() {
      return c.size();
    }

    public boolean isEmpty() {
      return c.isEmpty();
    }

    public boolean contains(Object o) {
      return c.contains(o);
    }

    public Object[] toArray() {
      return c.toArray();
    }

    public <T> T[] toArray(T[] a) {
      return c.toArray(a);
    }

    // public <T> T[] toArray(IntFunction<T[]> f) {
    //   return c.toArray(f);
    // }
    public String toString() {
      return c.toString();
    }

    public Iterator<E> iterator() {
      return new Iterator<E>() {
        private final Iterator<? extends E> i = c.iterator();

        public boolean hasNext() {
          return i.hasNext();
        }

        public E next() {
          return i.next();
        }

        public void remove() {
          throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
          // Use backing collection version
          i.forEachRemaining(action);
        }
      };
    }

    public boolean add(E e) {
      throw new UnsupportedOperationException();
    }

    public boolean remove(Object o) {
      throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> coll) {
      return c.containsAll(coll);
    }

    public boolean addAll(Collection<? extends E> coll) {
      throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> coll) {
      throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> coll) {
      throw new UnsupportedOperationException();
    }

    public void clear() {
      throw new UnsupportedOperationException();
    }

    // Override default methods in Collection
    @Override
    public void forEach(Consumer<? super E> action) {
      c.forEach(action);
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
      throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Spliterator<E> spliterator() {
      return (Spliterator<E>) c.spliterator();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Stream<E> stream() {
      return (Stream<E>) c.stream();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Stream<E> parallelStream() {
      return (Stream<E>) c.parallelStream();
    }
  }

  /**
   * Returns an <a href="Collection.html#unmodview">unmodifiable view</a> of the specified set.
   * Query operations on the returned set "read through" to the specified set, and attempts to
   * modify the returned set, whether direct or via its iterator, result in an {@code
   * UnsupportedOperationException}.
   *
   * <p>The returned set will be serializable if the specified set is serializable.
   *
   * @param <T> the class of the objects in the set
   * @param s the set for which an unmodifiable view is to be returned.
   * @return an unmodifiable view of the specified set.
   */
  public static <T> Set<T> unmodifiableSet(Set<? extends T> s) {
    return new UnmodifiableSet<>(s);
  }

  /**
   * @serial include
   */
  static class UnmodifiableSet<E> extends UnmodifiableCollection<E>
      implements Set<E>, Serializable {
    private static final long serialVersionUID = -9215047833775013803L;

    UnmodifiableSet(Set<? extends E> s) {
      super(s);
    }

    public boolean equals(Object o) {
      return o == this || c.equals(o);
    }

    public int hashCode() {
      return c.hashCode();
    }
  }

  /**
   * Returns an <a href="Collection.html#unmodview">unmodifiable view</a> of the specified sorted
   * set. Query operations on the returned sorted set "read through" to the specified sorted set.
   * Attempts to modify the returned sorted set, whether direct, via its iterator, or via its {@code
   * subSet}, {@code headSet}, or {@code tailSet} views, result in an {@code
   * UnsupportedOperationException}.
   *
   * <p>The returned sorted set will be serializable if the specified sorted set is serializable.
   *
   * @param <T> the class of the objects in the set
   * @param s the sorted set for which an unmodifiable view is to be returned.
   * @return an unmodifiable view of the specified sorted set.
   */
  public static <T> SortedSet<T> unmodifiableSortedSet(SortedSet<T> s) {
    return new UnmodifiableSortedSet<>(s);
  }

  /**
   * @serial include
   */
  static class UnmodifiableSortedSet<E> extends UnmodifiableSet<E>
      implements SortedSet<E>, Serializable {
    private static final long serialVersionUID = -4929149591599911165L;
    private final SortedSet<E> ss;

    UnmodifiableSortedSet(SortedSet<E> s) {
      super(s);
      ss = s;
    }

    public Comparator<? super E> comparator() {
      return ss.comparator();
    }

    public SortedSet<E> subSet(E fromElement, E toElement) {
      return new UnmodifiableSortedSet<>(ss.subSet(fromElement, toElement));
    }

    public SortedSet<E> headSet(E toElement) {
      return new UnmodifiableSortedSet<>(ss.headSet(toElement));
    }

    public SortedSet<E> tailSet(E fromElement) {
      return new UnmodifiableSortedSet<>(ss.tailSet(fromElement));
    }

    public E first() {
      return ss.first();
    }

    public E last() {
      return ss.last();
    }
  }

  /**
   * Returns an <a href="Collection.html#unmodview">unmodifiable view</a> of the specified list.
   * Query operations on the returned list "read through" to the specified list, and attempts to
   * modify the returned list, whether direct or via its iterator, result in an {@code
   * UnsupportedOperationException}.
   *
   * <p>The returned list will be serializable if the specified list is serializable. Similarly, the
   * returned list will implement {@link RandomAccess} if the specified list does.
   *
   * @param <T> the class of the objects in the list
   * @param list the list for which an unmodifiable view is to be returned.
   * @return an unmodifiable view of the specified list.
   */
  public static <T> List<T> unmodifiableList(List<? extends T> list) {
    return (list instanceof RandomAccess
        ? new UnmodifiableRandomAccessList<>(list)
        : new UnmodifiableList<>(list));
  }

  /**
   * @serial include
   */
  static class UnmodifiableList<E> extends UnmodifiableCollection<E> implements List<E> {
    private static final long serialVersionUID = -283967356065247728L;
    final List<? extends E> list;

    UnmodifiableList(List<? extends E> list) {
      super(list);
      this.list = list;
    }

    public boolean equals(Object o) {
      return o == this || list.equals(o);
    }

    public int hashCode() {
      return list.hashCode();
    }

    public E get(int index) {
      return list.get(index);
    }

    public E set(int index, E element) {
      throw new UnsupportedOperationException();
    }

    public void add(int index, E element) {
      throw new UnsupportedOperationException();
    }

    public E remove(int index) {
      throw new UnsupportedOperationException();
    }

    public int indexOf(Object o) {
      return list.indexOf(o);
    }

    public int lastIndexOf(Object o) {
      return list.lastIndexOf(o);
    }

    public boolean addAll(int index, Collection<? extends E> c) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void sort(Comparator<? super E> c) {
      throw new UnsupportedOperationException();
    }

    public ListIterator<E> listIterator() {
      return listIterator(0);
    }

    public ListIterator<E> listIterator(final int index) {
      return new ListIterator<E>() {
        private final ListIterator<? extends E> i = list.listIterator(index);

        public boolean hasNext() {
          return i.hasNext();
        }

        public E next() {
          return i.next();
        }

        public boolean hasPrevious() {
          return i.hasPrevious();
        }

        public E previous() {
          return i.previous();
        }

        public int nextIndex() {
          return i.nextIndex();
        }

        public int previousIndex() {
          return i.previousIndex();
        }

        public void remove() {
          throw new UnsupportedOperationException();
        }

        public void set(E e) {
          throw new UnsupportedOperationException();
        }

        public void add(E e) {
          throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
          i.forEachRemaining(action);
        }
      };
    }

    public List<E> subList(int fromIndex, int toIndex) {
      return new UnmodifiableList<>(list.subList(fromIndex, toIndex));
    }

    /**
     * UnmodifiableRandomAccessList instances are serialized as UnmodifiableList instances to allow
     * them to be deserialized in pre-1.4 JREs (which do not have UnmodifiableRandomAccessList).
     * This method inverts the transformation. As a beneficial side-effect, it also grafts the
     * RandomAccess marker onto UnmodifiableList instances that were serialized in pre-1.4 JREs.
     *
     * <p>Note: Unfortunately, UnmodifiableRandomAccessList instances serialized in 1.4.1 and
     * deserialized in 1.4 will become UnmodifiableList instances, as this method was missing in
     * 1.4.
     */
    private Object readResolve() {
      return (list instanceof RandomAccess ? new UnmodifiableRandomAccessList<>(list) : this);
    }
  }

  /**
   * @serial include
   */
  static class UnmodifiableRandomAccessList<E> extends UnmodifiableList<E> implements RandomAccess {
    UnmodifiableRandomAccessList(List<? extends E> list) {
      super(list);
    }

    public List<E> subList(int fromIndex, int toIndex) {
      return new UnmodifiableRandomAccessList<>(list.subList(fromIndex, toIndex));
    }

    private static final long serialVersionUID = -2542308836966382001L;

    /**
     * Allows instances to be deserialized in pre-1.4 JREs (which do not have
     * UnmodifiableRandomAccessList). UnmodifiableList has a readResolve method that inverts this
     * transformation upon deserialization.
     */
    private Object writeReplace() {
      return new UnmodifiableList<>(list);
    }
  }

  /**
   * Returns an <a href="Collection.html#unmodview">unmodifiable view</a> of the specified map.
   * Query operations on the returned map "read through" to the specified map, and attempts to
   * modify the returned map, whether direct or via its collection views, result in an {@code
   * UnsupportedOperationException}.
   *
   * <p>The returned map will be serializable if the specified map is serializable.
   *
   * @param <K> the class of the map keys
   * @param <V> the class of the map values
   * @param m the map for which an unmodifiable view is to be returned.
   * @return an unmodifiable view of the specified map.
   */
  public static <K, V> Map<K, V> unmodifiableMap(Map<? extends K, ? extends V> m) {
    return new UnmodifiableMap<>(m);
  }

  /**
   * @serial include
   */
  private static class UnmodifiableMap<K, V> implements Map<K, V>, Serializable {
    private static final long serialVersionUID = -1034234728574286014L;
    private final Map<? extends K, ? extends V> m;

    UnmodifiableMap(Map<? extends K, ? extends V> m) {
      if (m == null) {
        throw new NullPointerException();
      }
      this.m = m;
    }

    public int size() {
      return m.size();
    }

    public boolean isEmpty() {
      return m.isEmpty();
    }

    public boolean containsKey(Object key) {
      return m.containsKey(key);
    }

    public boolean containsValue(Object val) {
      return m.containsValue(val);
    }

    public V get(Object key) {
      return m.get(key);
    }

    public V put(K key, V value) {
      throw new UnsupportedOperationException();
    }

    public V remove(Object key) {
      throw new UnsupportedOperationException();
    }

    public void putAll(Map<? extends K, ? extends V> m) {
      throw new UnsupportedOperationException();
    }

    public void clear() {
      throw new UnsupportedOperationException();
    }

    private transient Set<K> keySet;
    private transient Set<Map.Entry<K, V>> entrySet;
    private transient Collection<V> values;

    public Set<K> keySet() {
      if (keySet == null) {
        keySet = unmodifiableSet(m.keySet());
      }
      return keySet;
    }

    public Set<Map.Entry<K, V>> entrySet() {
      if (entrySet == null) {
        entrySet = new UnmodifiableEntrySet<>(m.entrySet());
      }
      return entrySet;
    }

    public Collection<V> values() {
      if (values == null) {
        values = unmodifiableCollection(m.values());
      }
      return values;
    }

    public boolean equals(Object o) {
      return o == this || m.equals(o);
    }

    public int hashCode() {
      return m.hashCode();
    }

    public String toString() {
      return m.toString();
    }

    // Override default methods in Map
    @Override
    @SuppressWarnings("unchecked")
    public V getOrDefault(Object k, V defaultValue) {
      // Safe cast as we don't change the value
      return ((Map<K, V>) m).getOrDefault(k, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
      m.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
      throw new UnsupportedOperationException();
    }

    @Override
    public V putIfAbsent(K key, V value) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object key, Object value) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
      throw new UnsupportedOperationException();
    }

    @Override
    public V replace(K key, V value) {
      throw new UnsupportedOperationException();
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
      throw new UnsupportedOperationException();
    }

    @Override
    public V computeIfPresent(
        K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
      throw new UnsupportedOperationException();
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
      throw new UnsupportedOperationException();
    }

    @Override
    public V merge(
        K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
      throw new UnsupportedOperationException();
    }

    /**
     * We need this class in addition to UnmodifiableSet as Map.Entries themselves permit
     * modification of the backing Map via their setValue operation. This class is subtle: there are
     * many possible attacks that must be thwarted.
     *
     * @serial include
     */
    static class UnmodifiableEntrySet<K, V> extends UnmodifiableSet<Map.Entry<K, V>> {
      private static final long serialVersionUID = 7854390611657943733L;

      @SuppressWarnings({"unchecked", "rawtypes"})
      UnmodifiableEntrySet(Set<? extends Map.Entry<? extends K, ? extends V>> s) {
        // Need to cast to raw in order to work around a limitation in the type system
        super((Set) s);
      }

      static <K, V> Consumer<Map.Entry<? extends K, ? extends V>> entryConsumer(
          Consumer<? super Entry<K, V>> action) {
        return e -> action.accept(new UnmodifiableEntry<>(e));
      }

      public void forEach(Consumer<? super Entry<K, V>> action) {
        Objects.requireNonNull(action);
        c.forEach(entryConsumer(action));
      }

      static final class UnmodifiableEntrySetSpliterator<K, V> implements Spliterator<Entry<K, V>> {
        final Spliterator<Map.Entry<K, V>> s;

        UnmodifiableEntrySetSpliterator(Spliterator<Entry<K, V>> s) {
          this.s = s;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Entry<K, V>> action) {
          Objects.requireNonNull(action);
          return s.tryAdvance(entryConsumer(action));
        }

        @Override
        public void forEachRemaining(Consumer<? super Entry<K, V>> action) {
          Objects.requireNonNull(action);
          s.forEachRemaining(entryConsumer(action));
        }

        @Override
        public Spliterator<Entry<K, V>> trySplit() {
          Spliterator<Entry<K, V>> split = s.trySplit();
          return split == null ? null : new UnmodifiableEntrySetSpliterator<>(split);
        }

        @Override
        public long estimateSize() {
          return s.estimateSize();
        }

        @Override
        public long getExactSizeIfKnown() {
          return s.getExactSizeIfKnown();
        }

        @Override
        public int characteristics() {
          return s.characteristics();
        }

        @Override
        public boolean hasCharacteristics(int characteristics) {
          return s.hasCharacteristics(characteristics);
        }

        @Override
        public Comparator<? super Entry<K, V>> getComparator() {
          return s.getComparator();
        }
      }

      @SuppressWarnings("unchecked")
      public Spliterator<Entry<K, V>> spliterator() {
        return new UnmodifiableEntrySetSpliterator<>(
            (Spliterator<Map.Entry<K, V>>) c.spliterator());
      }

      @Override
      public Stream<Entry<K, V>> stream() {
        return StreamSupport.stream(spliterator(), false);
      }

      @Override
      public Stream<Entry<K, V>> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
      }

      public Iterator<Map.Entry<K, V>> iterator() {
        return new Iterator<Map.Entry<K, V>>() {
          private final Iterator<? extends Map.Entry<? extends K, ? extends V>> i = c.iterator();

          public boolean hasNext() {
            return i.hasNext();
          }

          public Map.Entry<K, V> next() {
            return new UnmodifiableEntry<>(i.next());
          }

          public void remove() {
            throw new UnsupportedOperationException();
          }

          public void forEachRemaining(Consumer<? super Map.Entry<K, V>> action) {
            i.forEachRemaining(entryConsumer(action));
          }
        };
      }

      @SuppressWarnings("unchecked")
      public Object[] toArray() {
        Object[] a = c.toArray();
        for (int i = 0; i < a.length; i++) {
          a[i] = new UnmodifiableEntry<>((Map.Entry<? extends K, ? extends V>) a[i]);
        }
        return a;
      }

      @SuppressWarnings("unchecked")
      public <T> T[] toArray(T[] a) {
        // We don't pass a to c.toArray, to avoid window of
        // vulnerability wherein an unscrupulous multithreaded client
        // could get his hands on raw (unwrapped) Entries from c.
        Object[] arr = c.toArray(a.length == 0 ? a : Arrays.copyOf(a, 0));
        for (int i = 0; i < arr.length; i++) {
          arr[i] = new UnmodifiableEntry<>((Map.Entry<? extends K, ? extends V>) arr[i]);
        }
        if (arr.length > a.length) {
          return (T[]) arr;
        }
        System.arraycopy(arr, 0, a, 0, arr.length);
        if (a.length > arr.length) {
          a[arr.length] = null;
        }
        return a;
      }

      /**
       * This method is overridden to protect the backing set against an object with a nefarious
       * equals function that senses that the equality-candidate is Map.Entry and calls its setValue
       * method.
       */
      public boolean contains(Object o) {
        if (!(o instanceof Map.Entry)) {
          return false;
        }
        return c.contains(new UnmodifiableEntry<>((Map.Entry<?, ?>) o));
      }

      /**
       * The next two methods are overridden to protect against an unscrupulous List whose
       * contains(Object o) method senses when o is a Map.Entry, and calls o.setValue.
       */
      public boolean containsAll(Collection<?> coll) {
        for (Object e : coll) {
          if (!contains(e)) // Invokes safe contains() above
          {
            return false;
          }
        }
        return true;
      }

      public boolean equals(Object o) {
        if (o == this) {
          return true;
        }
        if (!(o instanceof Set)) {
          return false;
        }
        Set<?> s = (Set<?>) o;
        if (s.size() != c.size()) {
          return false;
        }
        return containsAll(s); // Invokes safe containsAll() above
      }

      /**
       * This "wrapper class" serves two purposes: it prevents the client from modifying the backing
       * Map, by short-circuiting the setValue method, and it protects the backing Map against an
       * ill-behaved Map.Entry that attempts to modify another Map Entry when asked to perform an
       * equality check.
       */
      private static class UnmodifiableEntry<K, V> implements Map.Entry<K, V> {
        private Map.Entry<? extends K, ? extends V> e;

        UnmodifiableEntry(Map.Entry<? extends K, ? extends V> e) {
          this.e = Objects.requireNonNull(e);
        }

        public K getKey() {
          return e.getKey();
        }

        public V getValue() {
          return e.getValue();
        }

        public V setValue(V value) {
          throw new UnsupportedOperationException();
        }

        public int hashCode() {
          return e.hashCode();
        }

        public boolean equals(Object o) {
          if (this == o) {
            return true;
          }
          if (!(o instanceof Map.Entry)) {
            return false;
          }
          Map.Entry<?, ?> t = (Map.Entry<?, ?>) o;
          return eq(e.getKey(), t.getKey()) && eq(e.getValue(), t.getValue());
        }

        public String toString() {
          return e.toString();
        }
      }
    }
  }

  /**
   * Returns an <a href="Collection.html#unmodview">unmodifiable view</a> of the specified sorted
   * map. Query operations on the returned sorted map "read through" to the specified sorted map.
   * Attempts to modify the returned sorted map, whether direct, via its collection views, or via
   * its {@code subMap}, {@code headMap}, or {@code tailMap} views, result in an {@code
   * UnsupportedOperationException}.
   *
   * <p>The returned sorted map will be serializable if the specified sorted map is serializable.
   *
   * @param <K> the class of the map keys
   * @param <V> the class of the map values
   * @param m the sorted map for which an unmodifiable view is to be returned.
   * @return an unmodifiable view of the specified sorted map.
   */
  public static <K, V> SortedMap<K, V> unmodifiableSortedMap(SortedMap<K, ? extends V> m) {
    return new UnmodifiableSortedMap<>(m);
  }

  /**
   * @serial include
   */
  static class UnmodifiableSortedMap<K, V> extends UnmodifiableMap<K, V>
      implements SortedMap<K, V>, Serializable {
    private static final long serialVersionUID = -8806743815996713206L;
    private final SortedMap<K, ? extends V> sm;

    UnmodifiableSortedMap(SortedMap<K, ? extends V> m) {
      super(m);
      sm = m;
    }

    public Comparator<? super K> comparator() {
      return sm.comparator();
    }

    public SortedMap<K, V> subMap(K fromKey, K toKey) {
      return new UnmodifiableSortedMap<>(sm.subMap(fromKey, toKey));
    }

    public SortedMap<K, V> headMap(K toKey) {
      return new UnmodifiableSortedMap<>(sm.headMap(toKey));
    }

    public SortedMap<K, V> tailMap(K fromKey) {
      return new UnmodifiableSortedMap<>(sm.tailMap(fromKey));
    }

    public K firstKey() {
      return sm.firstKey();
    }

    public K lastKey() {
      return sm.lastKey();
    }
  }

  // Synch Wrappers
  // For Desugar: Public bridges to the constructors with extra parameters.
  public static <T> Collection<T> bridge_synchronizedCollection(Collection<T> c, Object mutex) {
    return synchronizedCollection(c, mutex);
  }

  public static <T> Set<T> bridge_synchronizedSet(Set<T> s, Object mutex) {
    return synchronizedSet(s, mutex);
  }

  public static <T> List<T> bridge_synchronizedList(List<T> l, Object mutex) {
    return synchronizedList(l, mutex);
  }

  /**
   * Returns a synchronized (thread-safe) collection backed by the specified collection. In order to
   * guarantee serial access, it is critical that <strong>all</strong> access to the backing
   * collection is accomplished through the returned collection.
   *
   * <p>It is imperative that the user manually synchronize on the returned collection when
   * traversing it via {@link Iterator}, {@link Spliterator} or {@link Stream}:
   *
   * <pre>
   *  Collection c = Collections.synchronizedCollection(myCollection);
   *     ...
   *  synchronized (c) {
   *      Iterator i = c.iterator(); // Must be in the synchronized block
   *      while (i.hasNext())
   *         foo(i.next());
   *  }
   * </pre>
   *
   * Failure to follow this advice may result in non-deterministic behavior.
   *
   * <p>The returned collection does <i>not</i> pass the {@code hashCode} and {@code equals}
   * operations through to the backing collection, but relies on {@code Object}'s equals and
   * hashCode methods. This is necessary to preserve the contracts of these operations in the case
   * that the backing collection is a set or a list.
   *
   * <p>The returned collection will be serializable if the specified collection is serializable.
   *
   * @param <T> the class of the objects in the collection
   * @param c the collection to be "wrapped" in a synchronized collection.
   * @return a synchronized view of the specified collection.
   */
  public static <T> Collection<T> synchronizedCollection(Collection<T> c) {
    return new SynchronizedCollection<>(c);
  }

  static <T> Collection<T> synchronizedCollection(Collection<T> c, Object mutex) {
    return new SynchronizedCollection<>(c, mutex);
  }

  /**
   * @serial include
   */
  static class SynchronizedCollection<E> implements Collection<E>, Serializable {
    private static final long serialVersionUID = 3053995032091335093L;
    final Collection<E> c; // Backing Collection
    final Object mutex; // Object on which to synchronize

    SynchronizedCollection(Collection<E> c) {
      this.c = Objects.requireNonNull(c);
      mutex = this;
    }

    SynchronizedCollection(Collection<E> c, Object mutex) {
      this.c = Objects.requireNonNull(c);
      this.mutex = Objects.requireNonNull(mutex);
    }

    public int size() {
      synchronized (mutex) {
        return c.size();
      }
    }

    public boolean isEmpty() {
      synchronized (mutex) {
        return c.isEmpty();
      }
    }

    public boolean contains(Object o) {
      synchronized (mutex) {
        return c.contains(o);
      }
    }

    public Object[] toArray() {
      synchronized (mutex) {
        return c.toArray();
      }
    }

    public <T> T[] toArray(T[] a) {
      synchronized (mutex) {
        return c.toArray(a);
      }
    }

    // public <T> T[] toArray(IntFunction<T[]> f) {
    //   synchronized (mutex) {
    //     return c.toArray(f);
    //   }
    // }
    public Iterator<E> iterator() {
      return c.iterator(); // Must be manually synched by user!
    }

    public boolean add(E e) {
      synchronized (mutex) {
        return c.add(e);
      }
    }

    public boolean remove(Object o) {
      synchronized (mutex) {
        return c.remove(o);
      }
    }

    public boolean containsAll(Collection<?> coll) {
      synchronized (mutex) {
        return c.containsAll(coll);
      }
    }

    public boolean addAll(Collection<? extends E> coll) {
      synchronized (mutex) {
        return c.addAll(coll);
      }
    }

    public boolean removeAll(Collection<?> coll) {
      synchronized (mutex) {
        return c.removeAll(coll);
      }
    }

    public boolean retainAll(Collection<?> coll) {
      synchronized (mutex) {
        return c.retainAll(coll);
      }
    }

    public void clear() {
      synchronized (mutex) {
        c.clear();
      }
    }

    public String toString() {
      synchronized (mutex) {
        return c.toString();
      }
    }

    // Override default methods in Collection
    @Override
    public void forEach(Consumer<? super E> consumer) {
      synchronized (mutex) {
        c.forEach(consumer);
      }
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
      synchronized (mutex) {
        return c.removeIf(filter);
      }
    }

    @Override
    public Spliterator<E> spliterator() {
      return c.spliterator(); // Must be manually synched by user!
    }

    @Override
    public Stream<E> stream() {
      return c.stream(); // Must be manually synched by user!
    }

    @Override
    public Stream<E> parallelStream() {
      return c.parallelStream(); // Must be manually synched by user!
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
      synchronized (mutex) {
        s.defaultWriteObject();
      }
    }
  }

  /**
   * Returns a synchronized (thread-safe) set backed by the specified set. In order to guarantee
   * serial access, it is critical that <strong>all</strong> access to the backing set is
   * accomplished through the returned set.
   *
   * <p>It is imperative that the user manually synchronize on the returned collection when
   * traversing it via {@link Iterator}, {@link Spliterator} or {@link Stream}:
   *
   * <pre>
   *  Set s = Collections.synchronizedSet(new HashSet());
   *      ...
   *  synchronized (s) {
   *      Iterator i = s.iterator(); // Must be in the synchronized block
   *      while (i.hasNext())
   *          foo(i.next());
   *  }
   * </pre>
   *
   * Failure to follow this advice may result in non-deterministic behavior.
   *
   * <p>The returned set will be serializable if the specified set is serializable.
   *
   * @param <T> the class of the objects in the set
   * @param s the set to be "wrapped" in a synchronized set.
   * @return a synchronized view of the specified set.
   */
  public static <T> Set<T> synchronizedSet(Set<T> s) {
    return new SynchronizedSet<>(s);
  }

  static <T> Set<T> synchronizedSet(Set<T> s, Object mutex) {
    return new SynchronizedSet<>(s, mutex);
  }

  /**
   * @serial include
   */
  static class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E> {
    private static final long serialVersionUID = 487447009682186044L;

    SynchronizedSet(Set<E> s) {
      super(s);
    }

    SynchronizedSet(Set<E> s, Object mutex) {
      super(s, mutex);
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      synchronized (mutex) {
        return c.equals(o);
      }
    }

    public int hashCode() {
      synchronized (mutex) {
        return c.hashCode();
      }
    }
  }

  /**
   * Returns a synchronized (thread-safe) sorted set backed by the specified sorted set. In order to
   * guarantee serial access, it is critical that <strong>all</strong> access to the backing sorted
   * set is accomplished through the returned sorted set (or its views).
   *
   * <p>It is imperative that the user manually synchronize on the returned sorted set when
   * traversing it or any of its {@code subSet}, {@code headSet}, or {@code tailSet} views via
   * {@link Iterator}, {@link Spliterator} or {@link Stream}:
   *
   * <pre>
   *  SortedSet s = Collections.synchronizedSortedSet(new TreeSet());
   *      ...
   *  synchronized (s) {
   *      Iterator i = s.iterator(); // Must be in the synchronized block
   *      while (i.hasNext())
   *          foo(i.next());
   *  }
   * </pre>
   *
   * or:
   *
   * <pre>
   *  SortedSet s = Collections.synchronizedSortedSet(new TreeSet());
   *  SortedSet s2 = s.headSet(foo);
   *      ...
   *  synchronized (s) {  // Note: s, not s2!!!
   *      Iterator i = s2.iterator(); // Must be in the synchronized block
   *      while (i.hasNext())
   *          foo(i.next());
   *  }
   * </pre>
   *
   * Failure to follow this advice may result in non-deterministic behavior.
   *
   * <p>The returned sorted set will be serializable if the specified sorted set is serializable.
   *
   * @param <T> the class of the objects in the set
   * @param s the sorted set to be "wrapped" in a synchronized sorted set.
   * @return a synchronized view of the specified sorted set.
   */
  public static <T> SortedSet<T> synchronizedSortedSet(SortedSet<T> s) {
    return new SynchronizedSortedSet<>(s);
  }

  /**
   * @serial include
   */
  static class SynchronizedSortedSet<E> extends SynchronizedSet<E> implements SortedSet<E> {
    private static final long serialVersionUID = 8695801310862127406L;
    private final SortedSet<E> ss;

    SynchronizedSortedSet(SortedSet<E> s) {
      super(s);
      ss = s;
    }

    SynchronizedSortedSet(SortedSet<E> s, Object mutex) {
      super(s, mutex);
      ss = s;
    }

    public Comparator<? super E> comparator() {
      synchronized (mutex) {
        return ss.comparator();
      }
    }

    public SortedSet<E> subSet(E fromElement, E toElement) {
      synchronized (mutex) {
        return new SynchronizedSortedSet<>(ss.subSet(fromElement, toElement), mutex);
      }
    }

    public SortedSet<E> headSet(E toElement) {
      synchronized (mutex) {
        return new SynchronizedSortedSet<>(ss.headSet(toElement), mutex);
      }
    }

    public SortedSet<E> tailSet(E fromElement) {
      synchronized (mutex) {
        return new SynchronizedSortedSet<>(ss.tailSet(fromElement), mutex);
      }
    }

    public E first() {
      synchronized (mutex) {
        return ss.first();
      }
    }

    public E last() {
      synchronized (mutex) {
        return ss.last();
      }
    }
  }

  /**
   * Returns a synchronized (thread-safe) list backed by the specified list. In order to guarantee
   * serial access, it is critical that <strong>all</strong> access to the backing list is
   * accomplished through the returned list.
   *
   * <p>It is imperative that the user manually synchronize on the returned list when traversing it
   * via {@link Iterator}, {@link Spliterator} or {@link Stream}:
   *
   * <pre>
   *  List list = Collections.synchronizedList(new ArrayList());
   *      ...
   *  synchronized (list) {
   *      Iterator i = list.iterator(); // Must be in synchronized block
   *      while (i.hasNext())
   *          foo(i.next());
   *  }
   * </pre>
   *
   * Failure to follow this advice may result in non-deterministic behavior.
   *
   * <p>The returned list will be serializable if the specified list is serializable.
   *
   * @param <T> the class of the objects in the list
   * @param list the list to be "wrapped" in a synchronized list.
   * @return a synchronized view of the specified list.
   */
  public static <T> List<T> synchronizedList(List<T> list) {
    return (list instanceof RandomAccess
        ? new SynchronizedRandomAccessList<>(list)
        : new SynchronizedList<>(list));
  }

  static <T> List<T> synchronizedList(List<T> list, Object mutex) {
    return (list instanceof RandomAccess
        ? new SynchronizedRandomAccessList<>(list, mutex)
        : new SynchronizedList<>(list, mutex));
  }

  /**
   * @serial include
   */
  static class SynchronizedList<E> extends SynchronizedCollection<E> implements List<E> {
    private static final long serialVersionUID = -7754090372962971524L;
    final List<E> list;

    SynchronizedList(List<E> list) {
      super(list);
      this.list = list;
    }

    SynchronizedList(List<E> list, Object mutex) {
      super(list, mutex);
      this.list = list;
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      synchronized (mutex) {
        return list.equals(o);
      }
    }

    public int hashCode() {
      synchronized (mutex) {
        return list.hashCode();
      }
    }

    public E get(int index) {
      synchronized (mutex) {
        return list.get(index);
      }
    }

    public E set(int index, E element) {
      synchronized (mutex) {
        return list.set(index, element);
      }
    }

    public void add(int index, E element) {
      synchronized (mutex) {
        list.add(index, element);
      }
    }

    public E remove(int index) {
      synchronized (mutex) {
        return list.remove(index);
      }
    }

    public int indexOf(Object o) {
      synchronized (mutex) {
        return list.indexOf(o);
      }
    }

    public int lastIndexOf(Object o) {
      synchronized (mutex) {
        return list.lastIndexOf(o);
      }
    }

    public boolean addAll(int index, Collection<? extends E> c) {
      synchronized (mutex) {
        return list.addAll(index, c);
      }
    }

    public ListIterator<E> listIterator() {
      return list.listIterator(); // Must be manually synched by user
    }

    public ListIterator<E> listIterator(int index) {
      return list.listIterator(index); // Must be manually synched by user
    }

    public List<E> subList(int fromIndex, int toIndex) {
      synchronized (mutex) {
        return new SynchronizedList<>(list.subList(fromIndex, toIndex), mutex);
      }
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
      synchronized (mutex) {
        list.replaceAll(operator);
      }
    }

    @Override
    public void sort(Comparator<? super E> c) {
      synchronized (mutex) {
        list.sort(c);
      }
    }

    /**
     * SynchronizedRandomAccessList instances are serialized as SynchronizedList instances to allow
     * them to be deserialized in pre-1.4 JREs (which do not have SynchronizedRandomAccessList).
     * This method inverts the transformation. As a beneficial side-effect, it also grafts the
     * RandomAccess marker onto SynchronizedList instances that were serialized in pre-1.4 JREs.
     *
     * <p>Note: Unfortunately, SynchronizedRandomAccessList instances serialized in 1.4.1 and
     * deserialized in 1.4 will become SynchronizedList instances, as this method was missing in
     * 1.4.
     */
    private Object readResolve() {
      return (list instanceof RandomAccess ? new SynchronizedRandomAccessList<>(list) : this);
    }
  }

  /**
   * @serial include
   */
  static class SynchronizedRandomAccessList<E> extends SynchronizedList<E> implements RandomAccess {
    SynchronizedRandomAccessList(List<E> list) {
      super(list);
    }

    SynchronizedRandomAccessList(List<E> list, Object mutex) {
      super(list, mutex);
    }

    public List<E> subList(int fromIndex, int toIndex) {
      synchronized (mutex) {
        return new SynchronizedRandomAccessList<>(list.subList(fromIndex, toIndex), mutex);
      }
    }

    private static final long serialVersionUID = 1530674583602358482L;

    /**
     * Allows instances to be deserialized in pre-1.4 JREs (which do not have
     * SynchronizedRandomAccessList). SynchronizedList has a readResolve method that inverts this
     * transformation upon deserialization.
     */
    private Object writeReplace() {
      return new SynchronizedList<>(list);
    }
  }

  /**
   * Returns a synchronized (thread-safe) map backed by the specified map. In order to guarantee
   * serial access, it is critical that <strong>all</strong> access to the backing map is
   * accomplished through the returned map.
   *
   * <p>It is imperative that the user manually synchronize on the returned map when traversing any
   * of its collection views via {@link Iterator}, {@link Spliterator} or {@link Stream}:
   *
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
   *
   * Failure to follow this advice may result in non-deterministic behavior.
   *
   * <p>The returned map will be serializable if the specified map is serializable.
   *
   * @param <K> the class of the map keys
   * @param <V> the class of the map values
   * @param m the map to be "wrapped" in a synchronized map.
   * @return a synchronized view of the specified map.
   */
  public static <K, V> Map<K, V> synchronizedMap(Map<K, V> m) {
    return new SynchronizedMap<>(m);
  }

  /**
   * @serial include
   */
  private static class SynchronizedMap<K, V> implements Map<K, V>, Serializable {
    private static final long serialVersionUID = 1978198479659022715L;
    private final Map<K, V> m; // Backing Map
    final Object mutex; // Object on which to synchronize

    SynchronizedMap(Map<K, V> m) {
      this.m = Objects.requireNonNull(m);
      mutex = this;
    }

    SynchronizedMap(Map<K, V> m, Object mutex) {
      this.m = m;
      this.mutex = mutex;
    }

    public int size() {
      synchronized (mutex) {
        return m.size();
      }
    }

    public boolean isEmpty() {
      synchronized (mutex) {
        return m.isEmpty();
      }
    }

    public boolean containsKey(Object key) {
      synchronized (mutex) {
        return m.containsKey(key);
      }
    }

    public boolean containsValue(Object value) {
      synchronized (mutex) {
        return m.containsValue(value);
      }
    }

    public V get(Object key) {
      synchronized (mutex) {
        return m.get(key);
      }
    }

    public V put(K key, V value) {
      synchronized (mutex) {
        return m.put(key, value);
      }
    }

    public V remove(Object key) {
      synchronized (mutex) {
        return m.remove(key);
      }
    }

    public void putAll(Map<? extends K, ? extends V> map) {
      synchronized (mutex) {
        m.putAll(map);
      }
    }

    public void clear() {
      synchronized (mutex) {
        m.clear();
      }
    }

    private transient Set<K> keySet;
    private transient Set<Map.Entry<K, V>> entrySet;
    private transient Collection<V> values;

    public Set<K> keySet() {
      synchronized (mutex) {
        if (keySet == null) {
          keySet = new SynchronizedSet<>(m.keySet(), mutex);
        }
        return keySet;
      }
    }

    public Set<Map.Entry<K, V>> entrySet() {
      synchronized (mutex) {
        if (entrySet == null) {
          entrySet = new SynchronizedSet<>(m.entrySet(), mutex);
        }
        return entrySet;
      }
    }

    public Collection<V> values() {
      synchronized (mutex) {
        if (values == null) {
          values = new SynchronizedCollection<>(m.values(), mutex);
        }
        return values;
      }
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      synchronized (mutex) {
        return m.equals(o);
      }
    }

    public int hashCode() {
      synchronized (mutex) {
        return m.hashCode();
      }
    }

    public String toString() {
      synchronized (mutex) {
        return m.toString();
      }
    }

    // Override default methods in Map
    @Override
    public V getOrDefault(Object k, V defaultValue) {
      synchronized (mutex) {
        return m.getOrDefault(k, defaultValue);
      }
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
      synchronized (mutex) {
        m.forEach(action);
      }
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
      synchronized (mutex) {
        m.replaceAll(function);
      }
    }

    @Override
    public V putIfAbsent(K key, V value) {
      synchronized (mutex) {
        return m.putIfAbsent(key, value);
      }
    }

    @Override
    public boolean remove(Object key, Object value) {
      synchronized (mutex) {
        return m.remove(key, value);
      }
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
      synchronized (mutex) {
        return m.replace(key, oldValue, newValue);
      }
    }

    @Override
    public V replace(K key, V value) {
      synchronized (mutex) {
        return m.replace(key, value);
      }
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
      synchronized (mutex) {
        return m.computeIfAbsent(key, mappingFunction);
      }
    }

    @Override
    public V computeIfPresent(
        K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
      synchronized (mutex) {
        return m.computeIfPresent(key, remappingFunction);
      }
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
      synchronized (mutex) {
        return m.compute(key, remappingFunction);
      }
    }

    @Override
    public V merge(
        K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
      synchronized (mutex) {
        return m.merge(key, value, remappingFunction);
      }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
      synchronized (mutex) {
        s.defaultWriteObject();
      }
    }
  }

  /**
   * Returns a synchronized (thread-safe) sorted map backed by the specified sorted map. In order to
   * guarantee serial access, it is critical that <strong>all</strong> access to the backing sorted
   * map is accomplished through the returned sorted map (or its views).
   *
   * <p>It is imperative that the user manually synchronize on the returned sorted map when
   * traversing any of its collection views, or the collections views of any of its {@code subMap},
   * {@code headMap} or {@code tailMap} views, via {@link Iterator}, {@link Spliterator} or {@link
   * Stream}:
   *
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
   *
   * or:
   *
   * <pre>
   *  SortedMap m = Collections.synchronizedSortedMap(new TreeMap());
   *  SortedMap m2 = m.subMap(foo, bar);
   *      ...
   *  Set s2 = m2.keySet();  // Needn't be in synchronized block
   *      ...
   *  synchronized (m) {  // Synchronizing on m, not m2 or s2!
   *      Iterator i = s2.iterator(); // Must be in synchronized block
   *      while (i.hasNext())
   *          foo(i.next());
   *  }
   * </pre>
   *
   * Failure to follow this advice may result in non-deterministic behavior.
   *
   * <p>The returned sorted map will be serializable if the specified sorted map is serializable.
   *
   * @param <K> the class of the map keys
   * @param <V> the class of the map values
   * @param m the sorted map to be "wrapped" in a synchronized sorted map.
   * @return a synchronized view of the specified sorted map.
   */
  public static <K, V> SortedMap<K, V> synchronizedSortedMap(SortedMap<K, V> m) {
    return new SynchronizedSortedMap<>(m);
  }

  /**
   * @serial include
   */
  static class SynchronizedSortedMap<K, V> extends SynchronizedMap<K, V>
      implements SortedMap<K, V> {
    private static final long serialVersionUID = -8798146769416483793L;
    private final SortedMap<K, V> sm;

    SynchronizedSortedMap(SortedMap<K, V> m) {
      super(m);
      sm = m;
    }

    SynchronizedSortedMap(SortedMap<K, V> m, Object mutex) {
      super(m, mutex);
      sm = m;
    }

    public Comparator<? super K> comparator() {
      synchronized (mutex) {
        return sm.comparator();
      }
    }

    public SortedMap<K, V> subMap(K fromKey, K toKey) {
      synchronized (mutex) {
        return new SynchronizedSortedMap<>(sm.subMap(fromKey, toKey), mutex);
      }
    }

    public SortedMap<K, V> headMap(K toKey) {
      synchronized (mutex) {
        return new SynchronizedSortedMap<>(sm.headMap(toKey), mutex);
      }
    }

    public SortedMap<K, V> tailMap(K fromKey) {
      synchronized (mutex) {
        return new SynchronizedSortedMap<>(sm.tailMap(fromKey), mutex);
      }
    }

    public K firstKey() {
      synchronized (mutex) {
        return sm.firstKey();
      }
    }

    public K lastKey() {
      synchronized (mutex) {
        return sm.lastKey();
      }
    }
  }

  // Dynamically typesafe collection wrappers
  /**
   * Returns a dynamically typesafe view of the specified collection. Any attempt to insert an
   * element of the wrong type will result in an immediate {@link ClassCastException}. Assuming a
   * collection contains no incorrectly typed elements prior to the time a dynamically typesafe view
   * is generated, and that all subsequent access to the collection takes place through the view, it
   * is <i>guaranteed</i> that the collection cannot contain an incorrectly typed element.
   *
   * <p>The generics mechanism in the language provides compile-time (static) type checking, but it
   * is possible to defeat this mechanism with unchecked casts. Usually this is not a problem, as
   * the compiler issues warnings on all such unchecked operations. There are, however, times when
   * static type checking alone is not sufficient. For example, suppose a collection is passed to a
   * third-party library and it is imperative that the library code not corrupt the collection by
   * inserting an element of the wrong type.
   *
   * <p>Another use of dynamically typesafe views is debugging. Suppose a program fails with a
   * {@code ClassCastException}, indicating that an incorrectly typed element was put into a
   * parameterized collection. Unfortunately, the exception can occur at any time after the
   * erroneous element is inserted, so it typically provides little or no information as to the real
   * source of the problem. If the problem is reproducible, one can quickly determine its source by
   * temporarily modifying the program to wrap the collection with a dynamically typesafe view. For
   * example, this declaration:
   *
   * <pre>{@code
   * Collection<String> c = new HashSet<>();
   * }</pre>
   *
   * may be replaced temporarily by this one:
   *
   * <pre>{@code
   * Collection<String> c = Collections.checkedCollection(
   *     new HashSet<>(), String.class);
   * }</pre>
   *
   * Running the program again will cause it to fail at the point where an incorrectly typed element
   * is inserted into the collection, clearly identifying the source of the problem. Once the
   * problem is fixed, the modified declaration may be reverted back to the original.
   *
   * <p>The returned collection does <i>not</i> pass the hashCode and equals operations through to
   * the backing collection, but relies on {@code Object}'s {@code equals} and {@code hashCode}
   * methods. This is necessary to preserve the contracts of these operations in the case that the
   * backing collection is a set or a list.
   *
   * <p>The returned collection will be serializable if the specified collection is serializable.
   *
   * <p>Since {@code null} is considered to be a value of any reference type, the returned
   * collection permits insertion of null elements whenever the backing collection does.
   *
   * @param <E> the class of the objects in the collection
   * @param c the collection for which a dynamically typesafe view is to be returned
   * @param type the type of element that {@code c} is permitted to hold
   * @return a dynamically typesafe view of the specified collection
   * @since 1.5
   */
  public static <E> Collection<E> checkedCollection(Collection<E> c, Class<E> type) {
    return new CheckedCollection<>(c, type);
  }

  @SuppressWarnings("unchecked")
  static <T> T[] zeroLengthArray(Class<T> type) {
    return (T[]) Array.newInstance(type, 0);
  }

  /**
   * @serial include
   */
  static class CheckedCollection<E> implements Collection<E>, Serializable {
    private static final long serialVersionUID = 1578914078182001775L;
    final Collection<E> c;
    final Class<E> type;

    @SuppressWarnings("unchecked")
    E typeCheck(Object o) {
      if (o != null && !type.isInstance(o)) {
        throw new ClassCastException(badElementMsg(o));
      }
      return (E) o;
    }

    private String badElementMsg(Object o) {
      return "Attempt to insert "
          + o.getClass()
          + " element into collection with element type "
          + type;
    }

    CheckedCollection(Collection<E> c, Class<E> type) {
      this.c = Objects.requireNonNull(c, "c");
      this.type = Objects.requireNonNull(type, "type");
    }

    public int size() {
      return c.size();
    }

    public boolean isEmpty() {
      return c.isEmpty();
    }

    public boolean contains(Object o) {
      return c.contains(o);
    }

    public Object[] toArray() {
      return c.toArray();
    }

    public <T> T[] toArray(T[] a) {
      return c.toArray(a);
    }

    // public <T> T[] toArray(IntFunction<T[]> f) {
    //   return c.toArray(f);
    // }
    public String toString() {
      return c.toString();
    }

    public boolean remove(Object o) {
      return c.remove(o);
    }

    public void clear() {
      c.clear();
    }

    public boolean containsAll(Collection<?> coll) {
      return c.containsAll(coll);
    }

    public boolean removeAll(Collection<?> coll) {
      return c.removeAll(coll);
    }

    public boolean retainAll(Collection<?> coll) {
      return c.retainAll(coll);
    }

    public Iterator<E> iterator() {
      // JDK-6363904 - unwrapped iterator could be typecast to
      // ListIterator with unsafe set()
      final Iterator<E> it = c.iterator();
      return new Iterator<E>() {
        public boolean hasNext() {
          return it.hasNext();
        }

        public E next() {
          return it.next();
        }

        public void remove() {
          it.remove();
        }

        public void forEachRemaining(Consumer<? super E> action) {
          it.forEachRemaining(action);
        }
      };
    }

    public boolean add(E e) {
      return c.add(typeCheck(e));
    }

    private E[] zeroLengthElementArray; // Lazily initialized

    private E[] zeroLengthElementArray() {
      return zeroLengthElementArray != null
          ? zeroLengthElementArray
          : (zeroLengthElementArray = zeroLengthArray(type));
    }

    @SuppressWarnings("unchecked")
    Collection<E> checkedCopyOf(Collection<? extends E> coll) {
      Object[] a;
      try {
        E[] z = zeroLengthElementArray();
        a = coll.toArray(z);
        // Defend against coll violating the toArray contract
        if (a.getClass() != z.getClass()) {
          a = Arrays.copyOf(a, a.length, z.getClass());
        }
      } catch (ArrayStoreException ignore) {
        // To get better and consistent diagnostics,
        // we call typeCheck explicitly on each element.
        // We call clone() to defend against coll retaining a
        // reference to the returned array and storing a bad
        // element into it after it has been type checked.
        a = coll.toArray().clone();
        for (Object o : a) {
          typeCheck(o);
        }
      }
      // A slight abuse of the type system, but safe here.
      return (Collection<E>) Arrays.asList(a);
    }

    public boolean addAll(Collection<? extends E> coll) {
      // Doing things this way insulates us from concurrent changes
      // in the contents of coll and provides all-or-nothing
      // semantics (which we wouldn't get if we type-checked each
      // element as we added it)
      return c.addAll(checkedCopyOf(coll));
    }

    // Override default methods in Collection
    @Override
    public void forEach(Consumer<? super E> action) {
      c.forEach(action);
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
      return c.removeIf(filter);
    }

    @Override
    public Spliterator<E> spliterator() {
      return c.spliterator();
    }

    @Override
    public Stream<E> stream() {
      return c.stream();
    }

    @Override
    public Stream<E> parallelStream() {
      return c.parallelStream();
    }
  }

  /**
   * Returns a dynamically typesafe view of the specified set. Any attempt to insert an element of
   * the wrong type will result in an immediate {@link ClassCastException}. Assuming a set contains
   * no incorrectly typed elements prior to the time a dynamically typesafe view is generated, and
   * that all subsequent access to the set takes place through the view, it is <i>guaranteed</i>
   * that the set cannot contain an incorrectly typed element.
   *
   * <p>A discussion of the use of dynamically typesafe views may be found in the documentation for
   * the {@link #checkedCollection checkedCollection} method.
   *
   * <p>The returned set will be serializable if the specified set is serializable.
   *
   * <p>Since {@code null} is considered to be a value of any reference type, the returned set
   * permits insertion of null elements whenever the backing set does.
   *
   * @param <E> the class of the objects in the set
   * @param s the set for which a dynamically typesafe view is to be returned
   * @param type the type of element that {@code s} is permitted to hold
   * @return a dynamically typesafe view of the specified set
   * @since 1.5
   */
  public static <E> Set<E> checkedSet(Set<E> s, Class<E> type) {
    return new CheckedSet<>(s, type);
  }

  /**
   * @serial include
   */
  static class CheckedSet<E> extends CheckedCollection<E> implements Set<E>, Serializable {
    private static final long serialVersionUID = 4694047833775013803L;

    CheckedSet(Set<E> s, Class<E> elementType) {
      super(s, elementType);
    }

    public boolean equals(Object o) {
      return o == this || c.equals(o);
    }

    public int hashCode() {
      return c.hashCode();
    }
  }

  /**
   * Returns a dynamically typesafe view of the specified sorted set. Any attempt to insert an
   * element of the wrong type will result in an immediate {@link ClassCastException}. Assuming a
   * sorted set contains no incorrectly typed elements prior to the time a dynamically typesafe view
   * is generated, and that all subsequent access to the sorted set takes place through the view, it
   * is <i>guaranteed</i> that the sorted set cannot contain an incorrectly typed element.
   *
   * <p>A discussion of the use of dynamically typesafe views may be found in the documentation for
   * the {@link #checkedCollection checkedCollection} method.
   *
   * <p>The returned sorted set will be serializable if the specified sorted set is serializable.
   *
   * <p>Since {@code null} is considered to be a value of any reference type, the returned sorted
   * set permits insertion of null elements whenever the backing sorted set does.
   *
   * @param <E> the class of the objects in the set
   * @param s the sorted set for which a dynamically typesafe view is to be returned
   * @param type the type of element that {@code s} is permitted to hold
   * @return a dynamically typesafe view of the specified sorted set
   * @since 1.5
   */
  public static <E> SortedSet<E> checkedSortedSet(SortedSet<E> s, Class<E> type) {
    return new CheckedSortedSet<>(s, type);
  }

  /**
   * @serial include
   */
  static class CheckedSortedSet<E> extends CheckedSet<E> implements SortedSet<E>, Serializable {
    private static final long serialVersionUID = 1599911165492914959L;
    private final SortedSet<E> ss;

    CheckedSortedSet(SortedSet<E> s, Class<E> type) {
      super(s, type);
      ss = s;
    }

    public Comparator<? super E> comparator() {
      return ss.comparator();
    }

    public E first() {
      return ss.first();
    }

    public E last() {
      return ss.last();
    }

    public SortedSet<E> subSet(E fromElement, E toElement) {
      return checkedSortedSet(ss.subSet(fromElement, toElement), type);
    }

    public SortedSet<E> headSet(E toElement) {
      return checkedSortedSet(ss.headSet(toElement), type);
    }

    public SortedSet<E> tailSet(E fromElement) {
      return checkedSortedSet(ss.tailSet(fromElement), type);
    }
  }

  /**
   * Returns a dynamically typesafe view of the specified list. Any attempt to insert an element of
   * the wrong type will result in an immediate {@link ClassCastException}. Assuming a list contains
   * no incorrectly typed elements prior to the time a dynamically typesafe view is generated, and
   * that all subsequent access to the list takes place through the view, it is <i>guaranteed</i>
   * that the list cannot contain an incorrectly typed element.
   *
   * <p>A discussion of the use of dynamically typesafe views may be found in the documentation for
   * the {@link #checkedCollection checkedCollection} method.
   *
   * <p>The returned list will be serializable if the specified list is serializable.
   *
   * <p>Since {@code null} is considered to be a value of any reference type, the returned list
   * permits insertion of null elements whenever the backing list does.
   *
   * @param <E> the class of the objects in the list
   * @param list the list for which a dynamically typesafe view is to be returned
   * @param type the type of element that {@code list} is permitted to hold
   * @return a dynamically typesafe view of the specified list
   * @since 1.5
   */
  public static <E> List<E> checkedList(List<E> list, Class<E> type) {
    return (list instanceof RandomAccess
        ? new CheckedRandomAccessList<>(list, type)
        : new CheckedList<>(list, type));
  }

  /**
   * @serial include
   */
  static class CheckedList<E> extends CheckedCollection<E> implements List<E> {
    private static final long serialVersionUID = 65247728283967356L;
    final List<E> list;

    CheckedList(List<E> list, Class<E> type) {
      super(list, type);
      this.list = list;
    }

    public boolean equals(Object o) {
      return o == this || list.equals(o);
    }

    public int hashCode() {
      return list.hashCode();
    }

    public E get(int index) {
      return list.get(index);
    }

    public E remove(int index) {
      return list.remove(index);
    }

    public int indexOf(Object o) {
      return list.indexOf(o);
    }

    public int lastIndexOf(Object o) {
      return list.lastIndexOf(o);
    }

    public E set(int index, E element) {
      return list.set(index, typeCheck(element));
    }

    public void add(int index, E element) {
      list.add(index, typeCheck(element));
    }

    public boolean addAll(int index, Collection<? extends E> c) {
      return list.addAll(index, checkedCopyOf(c));
    }

    public ListIterator<E> listIterator() {
      return listIterator(0);
    }

    public ListIterator<E> listIterator(final int index) {
      final ListIterator<E> i = list.listIterator(index);
      return new ListIterator<E>() {
        public boolean hasNext() {
          return i.hasNext();
        }

        public E next() {
          return i.next();
        }

        public boolean hasPrevious() {
          return i.hasPrevious();
        }

        public E previous() {
          return i.previous();
        }

        public int nextIndex() {
          return i.nextIndex();
        }

        public int previousIndex() {
          return i.previousIndex();
        }

        public void remove() {
          i.remove();
        }

        public void set(E e) {
          i.set(typeCheck(e));
        }

        public void add(E e) {
          i.add(typeCheck(e));
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
          i.forEachRemaining(action);
        }
      };
    }

    public List<E> subList(int fromIndex, int toIndex) {
      return new CheckedList<>(list.subList(fromIndex, toIndex), type);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ClassCastException if the class of an element returned by the operator prevents it
     *     from being added to this collection. The exception may be thrown after some elements of
     *     the list have already been replaced.
     */
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
      Objects.requireNonNull(operator);
      list.replaceAll(e -> typeCheck(operator.apply(e)));
    }

    @Override
    public void sort(Comparator<? super E> c) {
      list.sort(c);
    }
  }

  /**
   * @serial include
   */
  static class CheckedRandomAccessList<E> extends CheckedList<E> implements RandomAccess {
    private static final long serialVersionUID = 1638200125423088369L;

    CheckedRandomAccessList(List<E> list, Class<E> type) {
      super(list, type);
    }

    public List<E> subList(int fromIndex, int toIndex) {
      return new CheckedRandomAccessList<>(list.subList(fromIndex, toIndex), type);
    }
  }

  /**
   * Returns a dynamically typesafe view of the specified map. Any attempt to insert a mapping whose
   * key or value have the wrong type will result in an immediate {@link ClassCastException}.
   * Similarly, any attempt to modify the value currently associated with a key will result in an
   * immediate {@link ClassCastException}, whether the modification is attempted directly through
   * the map itself, or through a {@link Map.Entry} instance obtained from the map's {@link
   * Map#entrySet() entry set} view.
   *
   * <p>Assuming a map contains no incorrectly typed keys or values prior to the time a dynamically
   * typesafe view is generated, and that all subsequent access to the map takes place through the
   * view (or one of its collection views), it is <i>guaranteed</i> that the map cannot contain an
   * incorrectly typed key or value.
   *
   * <p>A discussion of the use of dynamically typesafe views may be found in the documentation for
   * the {@link #checkedCollection checkedCollection} method.
   *
   * <p>The returned map will be serializable if the specified map is serializable.
   *
   * <p>Since {@code null} is considered to be a value of any reference type, the returned map
   * permits insertion of null keys or values whenever the backing map does.
   *
   * @param <K> the class of the map keys
   * @param <V> the class of the map values
   * @param m the map for which a dynamically typesafe view is to be returned
   * @param keyType the type of key that {@code m} is permitted to hold
   * @param valueType the type of value that {@code m} is permitted to hold
   * @return a dynamically typesafe view of the specified map
   * @since 1.5
   */
  public static <K, V> Map<K, V> checkedMap(Map<K, V> m, Class<K> keyType, Class<V> valueType) {
    return new CheckedMap<>(m, keyType, valueType);
  }

  /**
   * @serial include
   */
  private static class CheckedMap<K, V> implements Map<K, V>, Serializable {
    private static final long serialVersionUID = 5742860141034234728L;
    private final Map<K, V> m;
    final Class<K> keyType;
    final Class<V> valueType;

    private void typeCheck(Object key, Object value) {
      if (key != null && !keyType.isInstance(key)) {
        throw new ClassCastException(badKeyMsg(key));
      }
      if (value != null && !valueType.isInstance(value)) {
        throw new ClassCastException(badValueMsg(value));
      }
    }

    private BiFunction<? super K, ? super V, ? extends V> typeCheck(
        BiFunction<? super K, ? super V, ? extends V> func) {
      Objects.requireNonNull(func);
      return (k, v) -> {
        V newValue = func.apply(k, v);
        typeCheck(k, newValue);
        return newValue;
      };
    }

    private String badKeyMsg(Object key) {
      return "Attempt to insert " + key.getClass() + " key into map with key type " + keyType;
    }

    private String badValueMsg(Object value) {
      return "Attempt to insert "
          + value.getClass()
          + " value into map with value type "
          + valueType;
    }

    CheckedMap(Map<K, V> m, Class<K> keyType, Class<V> valueType) {
      this.m = Objects.requireNonNull(m);
      this.keyType = Objects.requireNonNull(keyType);
      this.valueType = Objects.requireNonNull(valueType);
    }

    public int size() {
      return m.size();
    }

    public boolean isEmpty() {
      return m.isEmpty();
    }

    public boolean containsKey(Object key) {
      return m.containsKey(key);
    }

    public boolean containsValue(Object v) {
      return m.containsValue(v);
    }

    public V get(Object key) {
      return m.get(key);
    }

    public V remove(Object key) {
      return m.remove(key);
    }

    public void clear() {
      m.clear();
    }

    public Set<K> keySet() {
      return m.keySet();
    }

    public Collection<V> values() {
      return m.values();
    }

    public boolean equals(Object o) {
      return o == this || m.equals(o);
    }

    public int hashCode() {
      return m.hashCode();
    }

    public String toString() {
      return m.toString();
    }

    public V put(K key, V value) {
      typeCheck(key, value);
      return m.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public void putAll(Map<? extends K, ? extends V> t) {
      // Satisfy the following goals:
      // - good diagnostics in case of type mismatch
      // - all-or-nothing semantics
      // - protection from malicious t
      // - correct behavior if t is a concurrent map
      Object[] entries = t.entrySet().toArray();
      List<Map.Entry<K, V>> checked = new ArrayList<>(entries.length);
      for (Object o : entries) {
        Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
        Object k = e.getKey();
        Object v = e.getValue();
        typeCheck(k, v);
        checked.add(new AbstractMap.SimpleImmutableEntry<>((K) k, (V) v));
      }
      for (Map.Entry<K, V> e : checked) {
        m.put(e.getKey(), e.getValue());
      }
    }

    private transient Set<Map.Entry<K, V>> entrySet;

    public Set<Map.Entry<K, V>> entrySet() {
      if (entrySet == null) {
        entrySet = new CheckedEntrySet<>(m.entrySet(), valueType);
      }
      return entrySet;
    }

    // Override default methods in Map
    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
      m.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
      m.replaceAll(typeCheck(function));
    }

    @Override
    public V putIfAbsent(K key, V value) {
      typeCheck(key, value);
      return m.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
      return m.remove(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
      typeCheck(key, newValue);
      return m.replace(key, oldValue, newValue);
    }

    @Override
    public V replace(K key, V value) {
      typeCheck(key, value);
      return m.replace(key, value);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      return m.computeIfAbsent(
          key,
          k -> {
            V value = mappingFunction.apply(k);
            typeCheck(k, value);
            return value;
          });
    }

    @Override
    public V computeIfPresent(
        K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
      return m.computeIfPresent(key, typeCheck(remappingFunction));
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
      return m.compute(key, typeCheck(remappingFunction));
    }

    @Override
    public V merge(
        K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      return m.merge(
          key,
          value,
          (v1, v2) -> {
            V newValue = remappingFunction.apply(v1, v2);
            typeCheck(null, newValue);
            return newValue;
          });
    }

    /**
     * We need this class in addition to CheckedSet as Map.Entry permits modification of the backing
     * Map via the setValue operation. This class is subtle: there are many possible attacks that
     * must be thwarted.
     *
     * @serial exclude
     */
    static class CheckedEntrySet<K, V> implements Set<Map.Entry<K, V>> {
      private final Set<Map.Entry<K, V>> s;
      private final Class<V> valueType;

      CheckedEntrySet(Set<Map.Entry<K, V>> s, Class<V> valueType) {
        this.s = s;
        this.valueType = valueType;
      }

      public int size() {
        return s.size();
      }

      public boolean isEmpty() {
        return s.isEmpty();
      }

      public String toString() {
        return s.toString();
      }

      public int hashCode() {
        return s.hashCode();
      }

      public void clear() {
        s.clear();
      }

      public boolean add(Map.Entry<K, V> e) {
        throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection<? extends Map.Entry<K, V>> coll) {
        throw new UnsupportedOperationException();
      }

      public Iterator<Map.Entry<K, V>> iterator() {
        final Iterator<Map.Entry<K, V>> i = s.iterator();
        return new Iterator<Map.Entry<K, V>>() {
          public boolean hasNext() {
            return i.hasNext();
          }

          public void remove() {
            i.remove();
          }

          public Map.Entry<K, V> next() {
            return checkedEntry(i.next(), valueType);
          }

          public void forEachRemaining(Consumer<? super Entry<K, V>> action) {
            i.forEachRemaining(e -> action.accept(checkedEntry(e, valueType)));
          }
        };
      }

      @SuppressWarnings("unchecked")
      public Object[] toArray() {
        Object[] source = s.toArray();
        /*
         * Ensure that we don't get an ArrayStoreException even if
         * s.toArray returns an array of something other than Object
         */
        Object[] dest = (source.getClass() == Object[].class) ? source : new Object[source.length];
        for (int i = 0; i < source.length; i++) {
          dest[i] = checkedEntry((Map.Entry<K, V>) source[i], valueType);
        }
        return dest;
      }

      @SuppressWarnings("unchecked")
      public <T> T[] toArray(T[] a) {
        // We don't pass a to s.toArray, to avoid window of
        // vulnerability wherein an unscrupulous multithreaded client
        // could get his hands on raw (unwrapped) Entries from s.
        T[] arr = s.toArray(a.length == 0 ? a : Arrays.copyOf(a, 0));
        for (int i = 0; i < arr.length; i++) {
          arr[i] = (T) checkedEntry((Map.Entry<K, V>) arr[i], valueType);
        }
        if (arr.length > a.length) {
          return arr;
        }
        System.arraycopy(arr, 0, a, 0, arr.length);
        if (a.length > arr.length) {
          a[arr.length] = null;
        }
        return a;
      }

      /**
       * This method is overridden to protect the backing set against an object with a nefarious
       * equals function that senses that the equality-candidate is Map.Entry and calls its setValue
       * method.
       */
      public boolean contains(Object o) {
        if (!(o instanceof Map.Entry)) {
          return false;
        }
        Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
        return s.contains((e instanceof CheckedEntry) ? e : checkedEntry(e, valueType));
      }

      /**
       * The bulk collection methods are overridden to protect against an unscrupulous collection
       * whose contains(Object o) method senses when o is a Map.Entry, and calls o.setValue.
       */
      public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
          if (!contains(o)) // Invokes safe contains() above
          {
            return false;
          }
        }
        return true;
      }

      public boolean remove(Object o) {
        if (!(o instanceof Map.Entry)) {
          return false;
        }
        return s.remove(new AbstractMap.SimpleImmutableEntry<>((Map.Entry<?, ?>) o));
      }

      public boolean removeAll(Collection<?> c) {
        return batchRemove(c, false);
      }

      public boolean retainAll(Collection<?> c) {
        return batchRemove(c, true);
      }

      private boolean batchRemove(Collection<?> c, boolean complement) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<Map.Entry<K, V>> it = iterator();
        while (it.hasNext()) {
          if (c.contains(it.next()) != complement) {
            it.remove();
            modified = true;
          }
        }
        return modified;
      }

      public boolean equals(Object o) {
        if (o == this) {
          return true;
        }
        if (!(o instanceof Set)) {
          return false;
        }
        Set<?> that = (Set<?>) o;
        return that.size() == s.size() && containsAll(that); // Invokes safe containsAll() above
      }

      static <K, V, T> CheckedEntry<K, V, T> checkedEntry(Map.Entry<K, V> e, Class<T> valueType) {
        return new CheckedEntry<>(e, valueType);
      }

      /**
       * This "wrapper class" serves two purposes: it prevents the client from modifying the backing
       * Map, by short-circuiting the setValue method, and it protects the backing Map against an
       * ill-behaved Map.Entry that attempts to modify another Map.Entry when asked to perform an
       * equality check.
       */
      private static class CheckedEntry<K, V, T> implements Map.Entry<K, V> {
        private final Map.Entry<K, V> e;
        private final Class<T> valueType;

        CheckedEntry(Map.Entry<K, V> e, Class<T> valueType) {
          this.e = Objects.requireNonNull(e);
          this.valueType = Objects.requireNonNull(valueType);
        }

        public K getKey() {
          return e.getKey();
        }

        public V getValue() {
          return e.getValue();
        }

        public int hashCode() {
          return e.hashCode();
        }

        public String toString() {
          return e.toString();
        }

        public V setValue(V value) {
          if (value != null && !valueType.isInstance(value)) {
            throw new ClassCastException(badValueMsg(value));
          }
          return e.setValue(value);
        }

        private String badValueMsg(Object value) {
          return "Attempt to insert "
              + value.getClass()
              + " value into map with value type "
              + valueType;
        }

        public boolean equals(Object o) {
          if (o == this) {
            return true;
          }
          if (!(o instanceof Map.Entry)) {
            return false;
          }
          return e.equals(new AbstractMap.SimpleImmutableEntry<>((Map.Entry<?, ?>) o));
        }
      }
    }
  }

  /**
   * Returns a dynamically typesafe view of the specified sorted map. Any attempt to insert a
   * mapping whose key or value have the wrong type will result in an immediate {@link
   * ClassCastException}. Similarly, any attempt to modify the value currently associated with a key
   * will result in an immediate {@link ClassCastException}, whether the modification is attempted
   * directly through the map itself, or through a {@link Map.Entry} instance obtained from the
   * map's {@link Map#entrySet() entry set} view.
   *
   * <p>Assuming a map contains no incorrectly typed keys or values prior to the time a dynamically
   * typesafe view is generated, and that all subsequent access to the map takes place through the
   * view (or one of its collection views), it is <i>guaranteed</i> that the map cannot contain an
   * incorrectly typed key or value.
   *
   * <p>A discussion of the use of dynamically typesafe views may be found in the documentation for
   * the {@link #checkedCollection checkedCollection} method.
   *
   * <p>The returned map will be serializable if the specified map is serializable.
   *
   * <p>Since {@code null} is considered to be a value of any reference type, the returned map
   * permits insertion of null keys or values whenever the backing map does.
   *
   * @param <K> the class of the map keys
   * @param <V> the class of the map values
   * @param m the map for which a dynamically typesafe view is to be returned
   * @param keyType the type of key that {@code m} is permitted to hold
   * @param valueType the type of value that {@code m} is permitted to hold
   * @return a dynamically typesafe view of the specified map
   * @since 1.5
   */
  public static <K, V> SortedMap<K, V> checkedSortedMap(
      SortedMap<K, V> m, Class<K> keyType, Class<V> valueType) {
    return new CheckedSortedMap<>(m, keyType, valueType);
  }

  /**
   * @serial include
   */
  static class CheckedSortedMap<K, V> extends CheckedMap<K, V>
      implements SortedMap<K, V>, Serializable {
    private static final long serialVersionUID = 1599671320688067438L;
    private final SortedMap<K, V> sm;

    CheckedSortedMap(SortedMap<K, V> m, Class<K> keyType, Class<V> valueType) {
      super(m, keyType, valueType);
      sm = m;
    }

    public Comparator<? super K> comparator() {
      return sm.comparator();
    }

    public K firstKey() {
      return sm.firstKey();
    }

    public K lastKey() {
      return sm.lastKey();
    }

    public SortedMap<K, V> subMap(K fromKey, K toKey) {
      return checkedSortedMap(sm.subMap(fromKey, toKey), keyType, valueType);
    }

    public SortedMap<K, V> headMap(K toKey) {
      return checkedSortedMap(sm.headMap(toKey), keyType, valueType);
    }

    public SortedMap<K, V> tailMap(K fromKey) {
      return checkedSortedMap(sm.tailMap(fromKey), keyType, valueType);
    }
  }

  /**
   * Returns true if the specified arguments are equal, or both null.
   *
   * <p>NB: Do not replace with Object.equals until JDK-8015417 is resolved.
   */
  static boolean eq(Object o1, Object o2) {
    return o1 == null ? o2 == null : o1.equals(o2);
    }

}
